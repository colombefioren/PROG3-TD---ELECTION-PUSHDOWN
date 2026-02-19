package com.election.repository;

import com.election.db.DBConnection;
import com.election.model.CandidateVoteCount;
import com.election.model.VoteType;
import com.election.model.VoteTypeCount;

import java.sql.*;
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
        ResultSet rs = null;

        try{
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            if(!rs.next()){
                throw new IllegalArgumentException("No votes found");
            }
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

      try{
          conn = dbConnection.getDBConnection();
          ps = conn.createStatement();
          rs = ps.executeQuery(sql);
          List<VoteTypeCount> votes = new ArrayList<VoteTypeCount>();
          while(rs.next()){
              votes.add(mapResultSetToVoteTypeCount(rs));
          }
          return votes;
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }finally{
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

        try{
            conn = dbConnection.getDBConnection();
            ps = conn.createStatement();
            rs = ps.executeQuery(sql);
            List<CandidateVoteCount> candidateVoteCounts = new ArrayList<CandidateVoteCount>();
            while(rs.next()){
                candidateVoteCounts.add(mapResultSetToCandidateVoteCount(rs));
            }
            return candidateVoteCounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


}
