package com.example.etlap.controllers;

import com.example.etlap.Etel;
import com.example.etlap.EtelDB;
import com.example.etlap.EtlApp;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML
    private TableView<Etel> tableViewEtlap;
    @FXML
    private TableColumn<Etel, String> colNev;
    @FXML
    private TableColumn<Etel, Integer> colAr;
    @FXML
    private TableColumn<Etel, String> colKategoria;
    @FXML
    private Spinner<Double> spinnerSzazalek;
    @FXML
    private Spinner<Integer> spinnerForint;
    private EtelDB db;
    @FXML
    private TextArea textAreaLeiras;

    public void initialize() {
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        try {
            db = new EtelDB();
            etelListaFeltolt();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void etelListaFeltolt() {
        try {
            List<Etel> etelek = db.getEtelek();
            tableViewEtlap.getItems().clear();
            for (Etel etel : etelek) {
                tableViewEtlap.getItems().add(etel);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void onClickFelvesz(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlApp.class.getResource("hozzaad-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Étlap");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> etelListaFeltolt());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onClickTorol(ActionEvent actionEvent) {

    }

    @FXML
    public void onClickSzatalekEmel(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickFtEmel(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickLeiras(Event event) {
        Etel etel = tableViewEtlap.getSelectionModel().getSelectedItem();
        textAreaLeiras.setText(etel.getLeiras());
    }
}