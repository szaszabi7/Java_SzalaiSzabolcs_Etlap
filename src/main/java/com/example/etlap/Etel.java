package com.example.etlap;

public class Etel {
    private int id;
    private String nev;
    private String leiras;
    private String kategoria;
    private int ar;

    public Etel(int id, String nev, String leiras, String kategoria, int ar) {
        this.id = id;
        this.nev = nev;
        this.leiras = leiras;
        this.kategoria = kategoria;
        this.ar = ar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    @Override
    public String toString() {
        String s = String.format("Étel neve: %s\nKategória: %s\nÁr: %d", this.nev, this.kategoria, this.ar);
        return s;
    }
}
