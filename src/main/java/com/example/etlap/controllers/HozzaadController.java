package com.example.etlap.controllers;

import com.example.etlap.Controller;
import com.example.etlap.Etel;
import com.example.etlap.EtelDB;
import com.example.etlap.Kategoria;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HozzaadController extends Controller {
    @javafx.fxml.FXML
    private TextField labelHozzadNev;
    @javafx.fxml.FXML
    private TextArea textAreaHozzadLeiras;
    @javafx.fxml.FXML
    private ComboBox<Kategoria> comboBoxHozzaadKategoria;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerHozzadAr;
    private EtelDB db;

    public void initialize() {
        try {
            db = new EtelDB();
            List<Kategoria> kateg = db.getKategoria();
            for (Kategoria kategoria: kateg) {
                comboBoxHozzaadKategoria.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        comboBoxHozzaadKategoria.getSelectionModel().select(0);
    }

    @javafx.fxml.FXML
    public void btnHozzaadClick(ActionEvent actionEvent) {
        String nev = labelHozzadNev.getText();
        String leiras = textAreaHozzadLeiras.getText();
        int ar = spinnerHozzadAr.getValue();
        int kategIndex = comboBoxHozzaadKategoria.getSelectionModel().getSelectedIndex();

        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        if (kategIndex == -1){
            alert("Kategória kiválasztása köztelező");
            return;
        }
        int kategoria = comboBoxHozzaadKategoria.getSelectionModel().getSelectedItem().getId();

        try {
            db = new EtelDB();
            db.etelHozzadasa(nev, leiras, ar, kategoria);
            alert("Étel sikeresen felvéve");
            labelHozzadNev.setText("");
            textAreaHozzadLeiras.setText("");
            comboBoxHozzaadKategoria.getSelectionModel().select(0);
            spinnerHozzadAr.getValueFactory().setValue(1);
        } catch (Exception e) {
            errorAlert(e);
        }
    }
}
