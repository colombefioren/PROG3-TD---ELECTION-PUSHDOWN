package com.election.model;

import java.util.Objects;

public class ElectionResult {
    private String candidateName;
    private double validVoteCount;

    public ElectionResult() {
    }

    public ElectionResult(String candidateName, double validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public double getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(double validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ElectionResult that = (ElectionResult) o;
        return Double.compare(validVoteCount, that.validVoteCount) == 0 && Objects.equals(candidateName, that.candidateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateName, validVoteCount);
    }

    @Override
    public String toString() {
        return "ElectionResult{" +
                "candidateName='" + candidateName + '\'' +
                ", validVoteCount=" + validVoteCount +
                '}';
    }
}
