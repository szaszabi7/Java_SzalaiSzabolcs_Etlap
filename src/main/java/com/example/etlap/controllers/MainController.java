package com.example.etlap.controllers;

import com.example.etlap.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private TableView<Etel> tableViewEtlap;
    @FXML
    private TableColumn<Etel, String> colNev;
    @FXML
    private TableColumn<Etel, Integer> colAr;
    @FXML
    private TableColumn<Etel, String> colKategoria;
    @FXML
    private Spinner<Integer> spinnerSzazalek;
    @FXML
    private Spinner<Integer> spinnerForint;
    private EtelDB db;
    @FXML
    private TextArea textAreaLeiras;
    private List<Etel> etelek;
    private List<Kategoria> kateg;
    @FXML
    private ChoiceBox<Kategoria> choiceBoxKateg;

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

        kategFeltolt();

        choiceBoxKateg.setOnAction(actionEvent -> {
            if (choiceBoxKateg.getItems().size() != 0) {
                if (choiceBoxKateg.getSelectionModel().getSelectedItem().getId() == -1) {
                    etelListaFeltolt();
                } else {
                    tableViewEtlap.getItems().clear();
                    for (Etel e : etelek) {
                        if (e.getKategoria().equals(choiceBoxKateg.getSelectionModel().getSelectedItem().getNev())) {
                            tableViewEtlap.getItems().add(e);
                        }
                    }
                }
            }
        });
    }

    public void kategFeltolt() {
        try {
            kateg = db.getKategoria();
            choiceBoxKateg.getItems().clear();
            choiceBoxKateg.getItems().add(new Kategoria(-1, ""));
            for (Kategoria kategoria: kateg) {
                choiceBoxKateg.getItems().add(kategoria);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void etelListaFeltolt() {
        try {
            etelek = db.getEtelek();
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
            stage.setTitle("??j ??tel felv??tele");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> etelListaFeltolt());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClickTorol(ActionEvent actionEvent) {
        int selectedIndex = tableViewEtlap.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A t??rl??shez el??bb v??lasszon ki egy elemet a t??bl??zatb??l");
            return;
        }
        Etel torlendoEtel = tableViewEtlap.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy t??r??lni szeretn?? az al??bbi ??telt: " + torlendoEtel.getNev())) {
            return;
        }
        try {
            db.etelTorlese(torlendoEtel.getId());
            alert("Sikeres t??rl??s");
            etelListaFeltolt();
            textAreaLeiras.setText("");
        } catch (SQLException e) {
            errorAlert(e);
        }
    }

    @FXML
    public void onClickSzazalekEmel(ActionEvent actionEvent) {
        int selectedIndex = tableViewEtlap.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            int emeles = spinnerSzazalek.getValue();
            if (!confirm("Biztos hogy szeretn??d emelni az ??r??t ennek: ??sszes ??tel" )) {
                return;
            }
            try {
                if (db.etelArSzazalek(emeles)) {
                    alert("Sikeres m??dos??t??s");
                    etelListaFeltolt();
                } else {
                    alert("Sikertelen m??dos??t??s");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Etel modositandoEtel = tableViewEtlap.getSelectionModel().getSelectedItem();
            int emeles = spinnerSzazalek.getValue();
            if (!confirm("Biztos hogy szeretn??d emelni az ??r??t ennek: " + modositandoEtel.getNev())) {
                return;
            }
            try {
                if (db.etelArSzazalek(emeles, modositandoEtel.getId())) {
                    alert("Sikeres m??dos??t??s");
                    etelListaFeltolt();
                } else {
                    alert("Sikertelen m??dos??t??s");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onClickFtEmel(ActionEvent actionEvent) {
        int selectedIndex = tableViewEtlap.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            int emeles = spinnerForint.getValue();
            if (!confirm("Biztos hogy szeretn??d emelni az ??r??t ennek: ??sszes ??tel" )) {
                return;
            }
            try {
                if (db.etelArForint(emeles)) {
                    alert("Sikeres ??remel??s");
                    etelListaFeltolt();
                } else {
                    alert("Sikertelen ??remel??s");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Etel modositandoEtel = tableViewEtlap.getSelectionModel().getSelectedItem();
            int emeles = spinnerForint.getValue();
            if (!confirm("Biztos hogy szeretn??d emelni az ??r??t ennek: " + modositandoEtel.getNev())) {
                return;
            }
            try {
                if (db.etelArForint(emeles, modositandoEtel.getId())) {
                    alert("Sikeres ??remel??s");
                    etelListaFeltolt();
                } else {
                    alert("Sikertelen ??remel??s");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onClickLeiras(Event event) {
        Etel etel = tableViewEtlap.getSelectionModel().getSelectedItem();
        textAreaLeiras.setText(etel.getLeiras());
    }

    @FXML
    public void onClickKategAblak(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(EtlApp.class.getResource("kategoriak-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Kateg??ri??k");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> loadData());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        etelListaFeltolt();
        kategFeltolt();
    }
}