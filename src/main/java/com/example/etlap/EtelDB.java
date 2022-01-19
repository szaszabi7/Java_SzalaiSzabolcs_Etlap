package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtelDB {
    Connection conn;

    public EtelDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eteldb","root", "");
    }

    public List<Etel> getEtelek() throws SQLException {
        List<Etel> etelek = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etelek;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String kategoria = result.getString("kategoria");
            int ar = result.getInt("ar");
            Etel etel = new Etel(id, nev, kategoria, ar);
            etelek.add(etel);
        }
        return etelek;
    }
}
