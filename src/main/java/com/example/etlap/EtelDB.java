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
        String sql = "SELECT * FROM etlap INNER JOIN kategoria ON etlap.kategoria_id = kategoria.id ORDER BY etlap.id";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            String kategoria = result.getString("kategoria.nev");
            int ar = result.getInt("ar");
            Etel etel = new Etel(id, nev, leiras, kategoria, ar);
            etelek.add(etel);
        }
        return etelek;
    }

    public List<Kategoria> getKategoria() throws SQLException {
        List<Kategoria> kategoriak = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM kategoria");
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            kategoriak.add(new Kategoria(id, nev));
        }
        return kategoriak;
    }

    public int etelHozzadasa(String nev, String leiras, int ar, int kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, ar);
        stmt.setInt(4, kategoria);
        return stmt.executeUpdate();
    }

    public int kategoriaHozzaadasa(String nev) throws SQLException {
        String sql = "INSERT INTO kategoria(nev) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nev);
        return stmt.executeUpdate();
    }

    public boolean etelTorlese(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean kategoriaTorlese(int id) throws SQLException {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelArSzazalek(int emeles, int id) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100) WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok != 0;
    }

    public boolean etelArSzazalek(int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar * ((100 + ?) / 100)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok != 0;
    }

    public boolean etelArForint(int emeles, int id) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        stmt.setInt(2, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok != 0;
    }

    public boolean etelArForint(int emeles) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, emeles);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok != 0;
    }
}
