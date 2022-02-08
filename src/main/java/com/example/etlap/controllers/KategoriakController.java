package com.example.etlap.controllers;

import com.example.etlap.Controller;
import com.example.etlap.Etel;
import com.example.etlap.EtelDB;
import com.example.etlap.Kategoria;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

public class KategoriakController extends Controller {
    @javafx.fxml.FXML
    private ListView<Kategoria> listViewKategoriak;
    private List<Kategoria> kategoriak;
    private EtelDB db;

    public void initialize() {
        try {
            db = new EtelDB();
            kategotiaFeltolt();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @javafx.fxml.FXML
    public void onClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onClickKategTorles(ActionEvent actionEvent) {
        int selectedIndex = listViewKategoriak.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Kategoria torlendoKategoria = listViewKategoriak.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy törölni szeretné az alábbi ételt: " + torlendoKategoria.getNev())) {
            return;
        }
        try {
            db.kategoriaTorlese(torlendoKategoria.getId());
            alert("Sikeres törlés");
            kategotiaFeltolt();
        } catch (SQLException e) {
            errorAlert(e);
        }
    }

    public void kategotiaFeltolt() {
        try {
            kategoriak = db.getKategoria();
            listViewKategoriak.getItems().clear();
            for (Kategoria kategoria : kategoriak) {
                listViewKategoriak.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
