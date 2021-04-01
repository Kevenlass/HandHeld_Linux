package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller_Filtered_Customer_Tableview implements Initializable {
    @FXML
    private TextField Searchfield;
    @FXML
    private Button New_Device_Button, History_Button;
    @FXML
    private TableView<Tableview_Controller> chart;
    @FXML
    ObservableList<Tableview_Controller> DbListe = FXCollections.observableArrayList();
    @FXML
    private Label customerList_Label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            customerList_Label.setText(EinstiegsPunkt.g_u_s.getWelcherKunde());
            fillChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void fillChart() throws SQLException {

        new DatenbankHandler().Connect();
        String testquery = "Select * From Device2 where Kunde = '" + EinstiegsPunkt.g_u_s.getKundenname() + "'" + "and Standort = '" + EinstiegsPunkt.g_u_s.getStandort() + "'";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(testquery);
        ResultSet rs = stm.executeQuery();
        boolean run = false;

        List<TableColumn> columns = new ArrayList<>();

        while (rs.next()) {
            if (!run) {
                //Hier werden die Spalten generiert...
                for (int i = 3; i <= rs.getMetaData().getColumnCount(); i++) {
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

                if (Tableview_Controller.getSpalte5().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Tableview_Controller.getSpalte6().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Tableview_Controller.getSpalte7().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        //  Wrap liste.
        SortedList<Tableview_Controller> sortedData = new SortedList<>(filteredList);

        sortedData.comparatorProperty().bind(chart.comparatorProperty());

        chart.setItems(sortedData);

        chart.setOnMousePressed((EventHandler<MouseEvent>) event -> {
            if (event.getClickCount() == 2) {
                System.out.println(chart.getSelectionModel().getSelectedItem());
                try {
                    getobjectbutton();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getobjectbutton() throws IOException {
        Tableview_Controller getitem = chart.getSelectionModel().getSelectedItem();
        int spaltenindex = 12;

        EinstiegsPunkt.tbo.setKundennamen(getitem.getSpalte1());
        EinstiegsPunkt.tbo.setStanort(getitem.getSpalte2());
        EinstiegsPunkt.tbo.setSeriennummer(getitem.getSpalte7());
        EinstiegsPunkt.tbo.setgeraetenamen(getitem.getSpalte3());
        EinstiegsPunkt.tbo.setMac(getitem.getSpalte6());
        EinstiegsPunkt.tbo.setLiefertermin(getitem.getSpalte12());
        EinstiegsPunkt.tbo.setTwiNr(getitem.getSpalte5());
        EinstiegsPunkt.tbo.setServicelvl(getitem.getSpalte8());
        EinstiegsPunkt.tbo.setServiceende(getitem.getSpalte11());
        EinstiegsPunkt.tbo.setlieferant(getitem.getSpalte9());
        EinstiegsPunkt.tbo.setServiceAnfang(getitem.getSpalte10());
        EinstiegsPunkt.tbo.setstatus(getitem.getSpalte4());
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Edit_Filtered_Customer);
    }

    @FXML
    public void returnToFilter() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
    }

    @FXML
    public void History() {
        try {
            Stage stage = (Stage) History_Button.getScene().getWindow();
            stage.close();
            EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Device_History_Filter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void NewDevice() {
        try {
            Stage stage = (Stage) New_Device_Button.getScene().getWindow();
            stage.close();
            EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.New_Dataset_Device);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


