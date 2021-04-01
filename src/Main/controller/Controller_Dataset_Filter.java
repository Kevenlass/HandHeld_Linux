package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.Dataset_Filter_Tableview_Class;
import Main.utils.DatenbankHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Dataset_Filter implements Initializable {


    @FXML
    private TableView<Dataset_Filter_Tableview_Class> chart;
    @FXML
    ObservableList<Dataset_Filter_Tableview_Class> DbListe = FXCollections.observableArrayList();

    public void fillChart() throws SQLException {

        new DatenbankHandler().Connect();
        String testquery = "";

        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(testquery);
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
            String spalte1, spalte2;

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

            Dataset_Filter_Tableview_Class pers = new Dataset_Filter_Tableview_Class(spalte1, spalte2);

            DbListe.add(pers);
        }
        chart.setItems(DbListe);

    }


    public void getobjectbutton() throws IOException {
        Dataset_Filter_Tableview_Class getitem = chart.getSelectionModel().getSelectedItem();
        int spaltenindex = 12;

        EinstiegsPunkt.tbo.setKundennamen(getitem.getSpalte1());
        EinstiegsPunkt.tbo.setStanort(getitem.getSpalte2());

    }


    @FXML
    private ListView ListView_Customer;

    public void FillListView() throws SQLException {

        new DatenbankHandler().Connect();
        String query = "SELECT Kunden_name,Standort FROM Customer Group by Kunden_name,Standort order by Kunden_name,standort";
        //ListView<String> ListView_Customer;
        ObservableList<String> item = FXCollections.observableArrayList();

        PreparedStatement pstm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            String Concat, Concat2, Concat3;
            Concat = rs.getString("Kunden_Name");
            Concat2 = rs.getString("Standort");
            Concat3 = Concat + " " + Concat2;
            item.add(Concat3);
        }
        ListView_Customer.setStyle("-fx-font-size: 1.5em ;");
        ListView_Customer.setItems(item);

    }

    @FXML
    public void getObjectListview() throws SQLException {
        String Check = ListView_Customer.getSelectionModel().getSelectedItem().toString();
        String [] splittetStrings = Check.split(" ");

        for (int i = 0; i <= splittetStrings.length ; i++) {
            if (i==0){
                String kunde = splittetStrings[i];
                EinstiegsPunkt.g_u_s.setKundenname(kunde);
            }

            if (i ==1){
                String standort = splittetStrings[i];
                EinstiegsPunkt.g_u_s.setStandort(standort);
            }
        }
        try {
            EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Filtered_Customer_Tableview);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FillListView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Controller_Dataset_Filter() {

    }
//    public void CustomerTabelle() throws SQLException {
//        new DatenbankHandler().Connect();
//        String query = "SELECT * FROM Customer";
//        PreparedStatement psmt = DatenbankHandler.connection.prepareStatement(query);
//
//        ResultSet rs = psmt.executeQuery();
//        comboFieldKunde.getItems().add("Kunde Wählen");
//        while (rs.next()) {
//            String name = rs.getString("Kunden_Name");
//            boolean schonVorhanden = false;
//            for (int i = 0; i < comboFieldKunde.getItems().size(); i++) {
//                if (name.equals(comboFieldKunde.getItems().get(i))) {
//                    schonVorhanden = true;
//                }
//            }
//            if (!schonVorhanden) {
//                comboFieldKunde.getItems().add(rs.getString("Kunden_Name"));
//            }
//        }
//        Platform.runLater(() -> comboFieldKunde.getSelectionModel().select(EinstiegsPunkt.g_u_s.getIndexOne()));
//    }

//    public void Zuweisung_Standort() throws SQLException {
//        new DatenbankHandler().Connect();
//        String query = "select Standort from Customer where group_id = " + "'" + EinstiegsPunkt.g_u_s.getGroup_id() + "'";
//        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
//        ResultSet rs = stm.executeQuery();
//        comboFieldStandort.requestLayout();
//        comboFieldStandort.getItems().clear();
//        comboFieldStandort.getItems().add("Optional Standort Auswählen");
//        while (rs.next()) {
//            comboFieldStandort.getItems().add(rs.getString("Standort"));
//        }
//        Platform.runLater(() -> comboFieldStandort.getSelectionModel().select(EinstiegsPunkt.g_u_s.getIndexTwo()));
//    }
//
//    public void comboAction_Kunde(ActionEvent event) {
//        Datensatz_Anlegen_Group_ID("Optional Standort Auswählen", "0");
//        Datensatz_Anlegen_Group_ID("Lekkerland", "2");
//        Datensatz_Anlegen_Group_ID("Bodan", "3");
//        Datensatz_Anlegen_Group_ID("TWI", "1");
//        Datensatz_Anlegen_Group_ID("GGT", "4");
//        Datensatz_Anlegen_Group_ID("Fixmer", "5");
//        Datensatz_Anlegen_Group_ID("HvL", "6");
//
//
//        try {
//
//            Zuweisung_Standort();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    public void Datensatz_Anlegen_Group_ID(String kunden_name, String group_id) {
//
//        if (kunden_name.equals(comboFieldKunde.getSelectionModel().getSelectedItem())) {
//            EinstiegsPunkt.g_u_s.setGroup_id(group_id);
//        }
//
//
//    }
//
//    public void DeviceTypeTabelle() throws SQLException {
//        new DatenbankHandler().Connect();
//        String query = "SELECT * FROM DeviceType";
//        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
//        ResultSet rs = stm.executeQuery();
//        comboFieldGeraet.getItems().add("Optional Gerät Auswählen");
//        while (rs.next()) {
//            comboFieldGeraet.getItems().add(rs.getString("Devicename"));
//        }
//
//        Platform.runLater(() -> comboFieldGeraet.getSelectionModel().select(EinstiegsPunkt.g_u_s.getIndexThree()));
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            comboFieldStandort.getItems().add("Optional Standort Auswählen");
//
//            CustomerTabelle();
//            DeviceTypeTabelle();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @FXML
//    public void zurueck() throws IOException {
//        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Hauptmenue);
//    }
//
//        @FXML
//    public void filteredWindow() throws IOException {
//        if (comboFieldKunde.getSelectionModel().getSelectedItem() != null) {
//            if (comboFieldStandort.getSelectionModel().getSelectedItem() != null) {
//                if (comboFieldGeraet.getSelectionModel().getSelectedItem() != null) {
//                    EinstiegsPunkt.g_u_s.setKundenname((comboFieldKunde.getSelectionModel().getSelectedItem().toString()));
//                    EinstiegsPunkt.g_u_s.setDevicename(comboFieldGeraet.getSelectionModel().getSelectedItem().toString());
//                    EinstiegsPunkt.g_u_s.setStandort(comboFieldStandort.getSelectionModel().getSelectedItem().toString());
//                    System.out.println(EinstiegsPunkt.g_u_s.getStandort()+EinstiegsPunkt.g_u_s.getDevicename());
//                    getIndex();
//                    EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Filtered_Customer_Tableview);
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Gerät muss ausgewählt sein").showAndWait();
//                }
//            } if (comboFieldStandort.getSelectionModel().getSelectedItem() == null) {
//                if (comboFieldGeraet.getSelectionModel().getSelectedItem() != null) {
//                    EinstiegsPunkt.g_u_s.setKundenname((comboFieldKunde.getSelectionModel().getSelectedItem().toString()));
//                    EinstiegsPunkt.g_u_s.setDevicename(comboFieldGeraet.getSelectionModel().getSelectedItem().toString());
//                    //EinstiegsPunkt.g_u_s.setStandort(comboFieldStandort.getSelectionModel().getSelectedItem().toString());
//                    System.out.println(EinstiegsPunkt.g_u_s.getStandort()+EinstiegsPunkt.g_u_s.getDevicename());
//                    getIndex();
//                    EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Filtered_Customer_Tableview);
//                }
//            }
//
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Kundenname darf nicht leer sein").showAndWait();
//        }
//    }
//
//
//    @FXML
//    public void Device_Type_ID(ActionEvent event) throws SQLException {
//        new DatenbankHandler().Connect();
//        String query = "select Device_Type_ID from DeviceType where Devicename = " + "'" + comboFieldGeraet.getValue() + "'";
//        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
//        ResultSet rs = stm.executeQuery();
//        while (rs.next()) {
//            String Devicename = rs.getString("Device_Type_id");
//            EinstiegsPunkt.g_u_s.setDeviceTypeID(Devicename);
//            EinstiegsPunkt.g_u_s.setDevicename(comboFieldGeraet.getValue().toString());
//            System.out.println(EinstiegsPunkt.g_u_s.getDeviceTypeID());
//            System.out.println(EinstiegsPunkt.g_u_s.getDevicename());
//        }
//
//    }
//
//    @FXML
//    public void New_Dataset_Device() throws IOException {
//        getIndex();
//        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.New_Dataset_Device);
//    }
//
//    public void New_Dataset_Customer() throws IOException {
//        getIndex();
//        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.New_Dataset_Customer);
//    }
//
//    public void getIndex() {
//        EinstiegsPunkt.g_u_s.setIndexOne(comboFieldKunde.getSelectionModel().getSelectedIndex());
//        EinstiegsPunkt.g_u_s.setIndexTwo(comboFieldStandort.getSelectionModel().getSelectedIndex());
//        EinstiegsPunkt.g_u_s.setIndexThree(comboFieldGeraet.getSelectionModel().getSelectedIndex());
//    }
}
