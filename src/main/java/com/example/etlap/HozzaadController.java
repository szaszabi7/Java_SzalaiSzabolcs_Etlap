package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HozzaadController {
    @javafx.fxml.FXML
    private TextField labelHozzadNev;
    @javafx.fxml.FXML
    private TextArea textAreaHozzadLeiras;
    @javafx.fxml.FXML
    private ComboBox<String> comboBoxHozzaadKategoria;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerHozzadAr;
    private EtelDB db;

    public void initialize() {
        List<String> kateg = new ArrayList();
        try {
            db = new EtelDB();
            List<Etel> etelek = db.getEtelek();
            comboBoxHozzaadKategoria.getItems().clear();
            for (Etel etel : etelek) {
                if (!kateg.contains(etel.getKategoria())) kateg.add(etel.getKategoria());
            }
            for (String kategoria: kateg) {
                comboBoxHozzaadKategoria.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @javafx.fxml.FXML
    public void btnHozzaadClick(ActionEvent actionEvent) {
        String nev = labelHozzadNev.getText();
        String leiras = textAreaHozzadLeiras.getText();
        String kategoria = comboBoxHozzaadKategoria.getSelectionModel().getSelectedItem();
        int ar = spinnerHozzadAr.getValue();
        // TODO: 2022. 01. 27. alertek 
        try {
            db = new EtelDB();
            db.etelHozzadasa(nev, leiras, ar, kategoria);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
