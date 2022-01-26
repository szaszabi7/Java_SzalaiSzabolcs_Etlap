package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HozzaadController {
    @javafx.fxml.FXML
    private TextField labelHozzadNev;
    @javafx.fxml.FXML
    private TextArea labelHozzadLeiras;
    @javafx.fxml.FXML
    private MenuButton labelHozzadKategoria;
    @javafx.fxml.FXML
    private Spinner<Integer> labelHozzadAr;

    @javafx.fxml.FXML
    public void btnHozzaadClick(ActionEvent actionEvent) {
        String nev = labelHozzadNev.getText();
        String leiras = labelHozzadLeiras.getText();
        String kategoria = labelHozzadKategoria.getText();
        int ar = labelHozzadAr.getValue();

        try {
            EtelDB db = new EtelDB();
            db.etelHozzadasa(nev, leiras, ar, kategoria);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
