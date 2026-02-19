package com.election.repository;

import com.election.db.DBConnection;

public class DataRetriever {
    private final DBConnection dbConnection;
    public DataRetriever() {
        this.dbConnection = new DBConnection();
    }


}
