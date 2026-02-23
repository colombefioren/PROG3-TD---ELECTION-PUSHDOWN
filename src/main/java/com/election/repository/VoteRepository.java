package com.election.repository;

import com.election.model.CandidateVoteCount;
import com.election.model.ElectionResult;
import com.election.model.VoteSummary;
import com.election.model.VoteTypeCount;

import java.util.List;

public interface VoteRepository {
    long countAllVotes();

    List<VoteTypeCount> countVotesByType();

    List<CandidateVoteCount> countValidVotesByCandidate();

    VoteSummary computeVoteSummary();

    double computeTurnoutRate();

    ElectionResult FindWinner();
}
