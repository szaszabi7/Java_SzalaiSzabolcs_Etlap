package com.example.etlap;

public class Etel {
    private int id;
    private String nev;
    private String kategoria;
    private int ar;

    public Etel(int id, String nev, String kategoria, int ar) {
        this.nev = nev;
        this.kategoria = kategoria;
        this.ar = ar;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
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
}
