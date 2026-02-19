package com.election.repository;

import com.election.db.DBConnection;

import java.sql.*;

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
}
