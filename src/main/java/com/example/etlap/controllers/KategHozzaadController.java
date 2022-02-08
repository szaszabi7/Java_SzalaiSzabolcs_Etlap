package com.example.etlap.controllers;

import com.example.etlap.Controller;
import com.example.etlap.EtelDB;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class KategHozzaadController extends Controller {
    @javafx.fxml.FXML
    private TextField textFieldKateg;
    private EtelDB db;

    @javafx.fxml.FXML
    public void onClickKategHozzaad(ActionEvent actionEvent) {
        String nev = textFieldKateg.getText();

        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }

        try {
            db = new EtelDB();
            db.kategoriaHozzaadasa(nev);
            alert("Kategória sikeresen felvéve");
            textFieldKateg.setText("");
        } catch (Exception e) {
            errorAlert(e);
        }
    }
}
