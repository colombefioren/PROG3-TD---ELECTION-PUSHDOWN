package com.election.model;

import java.util.Objects;

public class VoteSummary {
    private Integer valideCount;
    private Integer blankCount;
    private Integer nullCount;

    public VoteSummary() {
    }

    public VoteSummary(Integer valideCount, Integer blankCount, Integer nullCount) {
        this.valideCount = valideCount;
        this.blankCount = blankCount;
        this.nullCount = nullCount;
    }

    public Integer getValideCount() {
        return valideCount;
    }

    public void setValideCount(Integer valideCount) {
        this.valideCount = valideCount;
    }

    public Integer getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(Integer blankCount) {
        this.blankCount = blankCount;
    }

    public Integer getNullCount() {
        return nullCount;
    }

    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteSummary that = (VoteSummary) o;
        return Objects.equals(valideCount, that.valideCount) && Objects.equals(blankCount, that.blankCount) && Objects.equals(nullCount, that.nullCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valideCount, blankCount, nullCount);
    }

    @Override
    public String toString() {
        return "VoteSummary{" +
                "valideCount=" + valideCount +
                ", blankCount=" + blankCount +
                ", nullCount=" + nullCount +
                '}';
    }
}
