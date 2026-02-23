package com.election;

import com.election.model.CandidateVoteCount;
import com.election.model.ElectionResult;
import com.election.model.VoteSummary;
import com.election.model.VoteTypeCount;
import com.election.repository.DataRetriever;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("\n===> Q1 - countAllVotes <===");
        long totalVotes = dataRetriever.countAllVotes();
        System.out.println("totalVote=" + totalVotes);

        System.out.println("\n===> Q2 - countVotesByType <===");
        List<VoteTypeCount> voteTypeCounts = dataRetriever.countVotesByType();
        System.out.println(voteTypeCounts);

        System.out.println("\n===> Q3 - countValidVotesByCandidate <===");
        List<CandidateVoteCount> candidateVoteCounts = dataRetriever.countValidVotesByCandidate();
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < candidateVoteCounts.size(); i++) {
            CandidateVoteCount c = candidateVoteCounts.get(i);
            result.append(c.getCandidateName())
                    .append("=")
                    .append(c.getValidVoteCount());

            if (i < candidateVoteCounts.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        System.out.println(result.toString());

        System.out.println("\n===> Q4 - computeVoteSummary <===");
        VoteSummary summary = dataRetriever.computeVoteSummary();
        System.out.println(summary);

        System.out.println("\n===> Q5 - computeTurnoutRate <===");
        double turnoutRate = dataRetriever.computeTurnoutRate();
        System.out.println("turnoutRate=" + turnoutRate + "%");

        System.out.println("\n===> Q6 - findWinner <===");
        ElectionResult winner = dataRetriever.FindWinner();
        System.out.println(winner.getCandidateName() + " | " + winner.getValidVoteCount());
    }
}