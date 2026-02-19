package com.election.model;

import java.util.Objects;

public class VoteTypeCount {
    private VoteType voteType;
    private Integer count;

    public VoteTypeCount(VoteType voteType, Integer count) {
        this.voteType = voteType;
        this.count = count;
    }

    public VoteTypeCount() {

    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteTypeCount that = (VoteTypeCount) o;
        return voteType == that.voteType && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteType, count);
    }

    @Override
    public String toString() {
        return "VoteTypeCount{" +
                "voteType=" + voteType +
                ", count=" + count +
                '}';
    }
}
