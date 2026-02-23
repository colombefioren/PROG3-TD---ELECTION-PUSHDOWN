package com.election.repository;

import com.election.db.DBConnection;
import com.election.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever implements VoteRepository {
    private final DBConnection dbConnection;

    public DataRetriever() {
        this.dbConnection = new DBConnection();
    }


    @Override
    public long countAllVotes() {
        String sql = "select count(id) as total_vote from vote";

        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;/**/

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            if (!rs.next()) {
                throw new IllegalArgumentException("No votes found");
            }
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }
    }

    @Override
    public List<VoteTypeCount> countVotesByType() {
        String sql = """
                select vo.vote_type as vote_type, count(vo.id) as vote_count from vote vo group by vo.vote_type order by vote_count desc
                """;
        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            List<VoteTypeCount> votes = new ArrayList<>();
            while (rs.next()) {
                votes.add(mapResultSetToVoteTypeCount(rs));
            }
            return votes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }
    }

    @Override
    public List<CandidateVoteCount> countValidVotesByCandidate() {
        String sql = """
                select c.name as candidate_name, count(case when vo.vote_type = 'VALID' then 1 end) as valid_vote
                from candidate c
                join vote vo on c.id = vo.candidate_id
                group by c.name
                """;
        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            List<CandidateVoteCount> candidateVoteCounts = new ArrayList<>();
            while (rs.next()) {
                candidateVoteCounts.add(mapResultSetToCandidateVoteCount(rs));
            }
            return candidateVoteCounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }
    }

    @Override
    public VoteSummary computeVoteSummary() {
        String sql =
                """
                        select count(case when vo.vote_type = 'VALID' then 1 end) as valid_vote,
                                count(case when vo.vote_type = 'BLANK' then 1 end) as blank_count,
                                count(case when vo.vote_type = 'NULL' then 1 end) as null_count
                         from vote vo
                        """;

        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            if (!rs.next()) {
                throw new IllegalArgumentException("No votes found");
            }
            return mapResultSetToVoteSummary(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }
    }

    @Override
    public double computeTurnoutRate() {
        String sql = """
                select (count(voter.id) / count(vote.id)) * 100 as participation_rate from voter join vote on voter.id = vote.voter_id
                """;
        Connection conn = null;
        Statement ps = null;

        ResultSet rs = null;

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);

            if (!rs.next()) {
                throw new IllegalArgumentException("No votes found");
            }

            return rs.getDouble(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }
    }

    @Override
    public ElectionResult FindWinner() {
        String sql = """
                select candidate_name, valid_vote_count from (select candidate.name as candidate_name,
                count(vote.id) filter ( where vote.vote_type = 'VALID') as valid_vote_count from candidate join vote
                on vote.candidate_id = candidate.id group by candidate.name) t
                where valid_vote_count = (select max(valid_vote_count) from
                (select count(vote.id) filter ( where vote.vote_type = 'VALID') as valid_vote_count from candidate
                join vote on vote.candidate_id = candidate.id group by candidate.name)x);
                """;

    /*    String sql = """
                select candidate_name, valid_vote_count from (select candidate.name as candidate_name, count(vote.id) filter (where vote.vote_type = 'VALID') as valid_vote_count,
                       rank() over (order by count(vote.id) filter (where vote.vote_type = 'VALID') desc) as rnk
                from candidate join vote on candidate.id = vote.candidate_id group by candidate.name) t where rnk = 1;
                """;*/

        Connection conn = null;
        Statement ps = null;
        ResultSet rs = null;

        try {
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);

            if (!rs.next()) {
                throw new IllegalArgumentException("No winner found");
            }

            return mapResulSetToElectionResult(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.attemptCloseDBConnection(rs, ps, conn);
        }

    }

    private ElectionResult mapResulSetToElectionResult(ResultSet rs) throws SQLException {
        ElectionResult electionResult = new ElectionResult();
        electionResult.setCandidateName(rs.getString("candidate_name"));
        electionResult.setValidVoteCount(rs.getInt("valid_vote_count"));
        return electionResult;
    }


    private VoteTypeCount mapResultSetToVoteTypeCount(ResultSet rs) throws SQLException {
        VoteTypeCount vote = new VoteTypeCount();
        vote.setVoteType(VoteType.valueOf(rs.getString("vote_type")));
        vote.setCount(rs.getInt("vote_count"));
        return vote;
    }

    private CandidateVoteCount mapResultSetToCandidateVoteCount(ResultSet rs) throws SQLException {
        CandidateVoteCount candidateVoteCount = new CandidateVoteCount();
        candidateVoteCount.setCandidateName(rs.getString("candidate_name"));
        candidateVoteCount.setValidVoteCount(rs.getInt("valid_vote"));
        return candidateVoteCount;
    }

    private VoteSummary mapResultSetToVoteSummary(ResultSet rs) throws SQLException {
        VoteSummary voteSummary = new VoteSummary();
        voteSummary.setValideCount(rs.getInt("valid_vote"));
        voteSummary.setBlankCount(rs.getInt("blank_count"));
        voteSummary.setNullCount(rs.getInt("null_count"));
        return voteSummary;
    }


}
