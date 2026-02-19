package com.election.repository;

import com.election.model.VoteTypeCount;

import java.util.List;

public interface VoteRepository {
    long countAllVotes();

    List<VoteTypeCount> countVotesByType();
}
