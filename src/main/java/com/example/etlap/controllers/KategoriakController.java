package com.example.etlap.controllers;

import com.example.etlap.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void onClickKategHozzaadAblak(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlApp.class.getResource("kateghozzaad-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Kategória hozzáadása");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> kategotiaFeltolt());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void onClickKategTorles(ActionEvent actionEvent) {
        int selectedIndex = listViewKategoriak.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Kategoria torlendoKategoria = listViewKategoriak.getSelectionModel().getSelectedItem();
        if (!confirm("Az összes étel ilyen kategóriával törlődni fog\nBiztos hogy törölni szeretné az alábbi kategóriát: " + torlendoKategoria.getNev())) {
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
