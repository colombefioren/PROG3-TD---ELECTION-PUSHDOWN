package com.election.repository;

import java.util.List;

public interface VoteRepository {
    long countAllVotes();

    List<VoteTypeCount> countVotesByType();
}
