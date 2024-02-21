package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RunSQL {

    Connection conn;

    public RunSQL(Connection conn){
        this.conn = conn;
    }

    public List<String> getConstraint(){
        List<String> constraintNames = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT CONSTRAINT_NAME " +
                    "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS " +
                    "WHERE TABLE_NAME = 'PRODUCTS' " +
                    "and CONSTRAINT_TYPE='FOREIGN KEY';");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String constraintName = rs.getString("CONSTRAINT_NAME");
                constraintNames.add(constraintName);
            }
            rs.close();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return constraintNames;
    }

}
