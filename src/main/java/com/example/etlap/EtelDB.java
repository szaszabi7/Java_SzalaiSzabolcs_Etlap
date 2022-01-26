package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtelDB {
    Connection conn;

    public EtelDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb","root", "");
    }

    public List<Etel> getEtelek() throws SQLException {
        List<Etel> etelek = new ArrayList<>();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM etlap;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            String kategoria = result.getString("kategoria");
            int ar = result.getInt("ar");
            Etel etel = new Etel(id, nev, leiras, kategoria, ar);
            etelek.add(etel);
        }
        return etelek;
    }

    public int etelHozzadasa(String nev, String leiras, int ar, String kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, ar);
        stmt.setString(4, kategoria);
        return stmt.executeUpdate();
    }
}
