package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller_Filtered_Customer implements Initializable {
    @FXML
    private TextField Searchfield;
    @FXML
    private Button anfordern;
    @FXML
    private TableView<Tableview_Controller> chart;
    @FXML
    ObservableList<Tableview_Controller> DbListe = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            fillChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void fillChart() throws SQLException {

        new DatenbankHandler().Connect();
        String query = "Select * From Device2 where device_name = '" + EinstiegsPunkt.g_u_s.getDevicename() + "'" + "and Kunde = '" + EinstiegsPunkt.g_u_s.getKundenname() + "'" + "and Standort = '" + EinstiegsPunkt.g_u_s.getStandort() + "'";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        boolean run = false;

        List<TableColumn> columns = new ArrayList<>();


        while (rs.next()) {
            if (!run) {
                //Hier werden die Spalten generiert...
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i));
                    column.setCellValueFactory(
                            new PropertyValueFactory<Tableview_Controller, String>("spalte" + i));
                    //Hier werden diese in eine Liste hinzugefügt
                    columns.add(column);
                }
                run = true;
                for (TableColumn t : columns) {
                    //Hier wird die liste durch Iteriert und die SPalten werde dem chart(Tableview) hinzugefügt
                    this.chart.getColumns().add(t);
                }
            }
            //HIer wird ein neues Objekt erstellt
            String spalte1, spalte2, spalte3, spalte4, spalte5, spalte6, spalte7, spalte8, spalte9, spalte10, spalte11, spalte12;

            if (rs.getString(1) != null) {
                spalte1 = rs.getString(1);
            } else {
                spalte1 = "NULL";
            }
            if (rs.getString(2) != null) {
                spalte2 = rs.getString(2);
            } else {
                spalte2 = "NULL";
            }
            if (rs.getString(3) != null) {
                spalte3 = rs.getString(3);
            } else {
                spalte3 = "NULL";
            }
            if (rs.getString(4) != null) {
                spalte4 = rs.getString(4);
            } else {
                spalte4 = "NULL";
            }
            if (rs.getString(5) != null) {
                spalte5 = rs.getString(5);
            } else {
                spalte5 = "NULL";
            }
            if (rs.getString(6) != null) {
                spalte6 = rs.getString(6);
            } else {
                spalte6 = "NULL";
            }
            if (rs.getString(7) != null) {
                spalte7 = rs.getString(7);
            } else {
                spalte7 = "NULL";
            }
            if (rs.getString(8) != null) {
                spalte8 = rs.getString(8);
            } else {
                spalte8 = "NULL";
            }
            if (rs.getString(9) != null) {
                spalte9 = rs.getString(9);
            } else {
                spalte9 = "NULL";
            }
            if (rs.getString(10) != null) {
                spalte10 = rs.getString(10);
            } else {
                spalte10 = "NULL";
            }
            if (rs.getString(11) != null) {
                spalte11 = rs.getString(11);
            } else {
                spalte11 = "NULL";
            }
            if (rs.getString(12) != null) {
                spalte12 = rs.getString(12);
            } else {
                spalte12 = "NULL";
            }
            Tableview_Controller pers = new Tableview_Controller(spalte1, spalte2, spalte3, spalte4, spalte5, spalte6, spalte7, spalte8, spalte9, spalte10, spalte11, spalte12);

            DbListe.add(pers);
        }

        FilteredList<Tableview_Controller> filteredList = new FilteredList<>(DbListe);


        // predikat setzen bei änderungen.
        Searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Tableview_Controller -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // vergleich.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Tableview_Controller.getSpalte1().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Tableview_Controller.getSpalte11().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        //  Wrap liste.
        SortedList<Tableview_Controller> sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(chart.comparatorProperty());

        chart.setItems(sortedData);

    }


    public void getobjectbutton() throws IOException {
        Tableview_Controller getitem = chart.getSelectionModel().getSelectedItem();
        int spaltenindex = 12;

        EinstiegsPunkt.tbo.setKundennamen(getitem.getSpalte1());
        EinstiegsPunkt.tbo.setStanort(getitem.getSpalte2());
        EinstiegsPunkt.tbo.setSeriennummer(getitem.getSpalte10());
        EinstiegsPunkt.tbo.setgeraetenamen(getitem.getSpalte3());
        EinstiegsPunkt.tbo.setMac(getitem.getSpalte12());
        EinstiegsPunkt.tbo.setdatum(getitem.getSpalte9());
        EinstiegsPunkt.tbo.setTwiNr(getitem.getSpalte11());
        EinstiegsPunkt.tbo.setServicelvl(getitem.getSpalte5());
        EinstiegsPunkt.tbo.setServiceende(getitem.getSpalte8());
        EinstiegsPunkt.tbo.setlieferant(getitem.getSpalte6());
        EinstiegsPunkt.tbo.setServiceAnfang(getitem.getSpalte7());
        EinstiegsPunkt.tbo.setstatus(getitem.getSpalte4());
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Edit_Filtered_Customer);
    }

    @FXML
    public void returnToFilter() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
    }
}

