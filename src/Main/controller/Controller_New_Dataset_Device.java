package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class Controller_New_Dataset_Device implements Initializable {

    @FXML
    private Label Datum_Label;
    @FXML
    private ComboBox ComboBox_Tag_Anfang, ComboBox_Monat_Anfang, ComboBox_Jahr_Anfang, ComboBox_Tag_Ende, ComboBox_Monat_Ende,
            ComboBox_Jahr_Ende,comboboxStatus, comboboxServiceLVL, comboboxLieferant, comboFieldGeraet, comboFieldKunde, comboFieldStandort;

    @FXML
    private TextField textfield_twi_nr, textfield_mac_adresse, textfield_seriennummer;
    @FXML
    private Button datensatz_anlegen;
    @FXML
    private DatePicker LieferterminDT;


    @FXML
    private void systemDateTime() {
        LocalDate CurrentDate = LocalDate.now();
        Datum_Label.setText(CurrentDate.toString());
    }

    public Controller_New_Dataset_Device() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemDateTime();
        try {
            loadFillComboboxes();
            DeviceTypeTabelle();
            getCustomer();
            comboboxChangeFontStyle();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Controller: Controller_New_Dataset wurde geladen..");
    }

    private void loadFillComboboxes() throws SQLException {
        fillComboboxes(comboboxStatus, "Status");
        fillComboboxes(comboboxLieferant, "lieferant");
        fillComboboxes(comboboxServiceLVL, "Servicelvl");
    }


    private void fillComboboxes(ComboBox comboBox, String ColumLabel) throws SQLException {
        new DatenbankHandler().Connect();
        String query = "SELECT * FROM Device2";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String PlaceHolder = rs.getString(ColumLabel);
            boolean bool = false;
            for (int i = 0; i < comboBox.getItems().size(); i++) {

                if (PlaceHolder != null) {
                    if (PlaceHolder.equals(comboBox.getItems().get(i))) {
                        bool = true;
                    }
                }
            }
            if (bool == false && PlaceHolder != null) {
                comboBox.getItems().add(rs.getString(ColumLabel));
            }
        }
        DatenbankHandler.connection.close();
    }





    public void setGroupID(String kunden_name, String group_id) {

        if (kunden_name.equals(comboFieldKunde.getSelectionModel().getSelectedItem())) {
            // g_u_s.setGroup_id(group_id);
            EinstiegsPunkt.g_u_s.setGroup_id(group_id);
        }
    }

    public void comboAction_Kunde(ActionEvent event) {
        setGroupID("", "0");
        setGroupID("Lekkerland", "2");
        setGroupID("Bodan", "3");
        setGroupID("TWI", "1");
        setGroupID("GGT", "4");
        setGroupID("Fixmer", "5");
        setGroupID("HvL", "6");
        try {
            setLocation();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setLocation() throws SQLException {
        new DatenbankHandler().Connect();

        String query = "SELECT Standort FROM Customer WHERE group_id = " + "'" + EinstiegsPunkt.g_u_s.getGroup_id().toString() + "'";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        comboFieldStandort.requestLayout();
        comboFieldStandort.getItems().clear();
        while (rs.next()) {
            comboFieldStandort.getItems().add(rs.getString("Standort"));
        }
        DatenbankHandler.connection.close();
    }



    public void getCustomer() throws SQLException {
        new DatenbankHandler().Connect();
        String query = "SELECT * FROM Customer";
        PreparedStatement psmt = DatenbankHandler.connection.prepareStatement(query);

        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            String name = rs.getString("Kunden_Name");
            boolean drinn = false;
            for (int i = 0; i < comboFieldKunde.getItems().size(); i++) {
                if (name.equals(comboFieldKunde.getItems().get(i))) {
                    drinn = true;
                }
            }
            if (drinn == false) {
                comboFieldKunde.getItems().add(rs.getString("Kunden_Name"));
            }
        }
        comboFieldKunde.setValue(comboFieldKunde.getItems().get(0));
        DatenbankHandler.connection.close();
    }

    public void DeviceTypeTabelle() throws SQLException {
        new DatenbankHandler().Connect();
        String query = "SELECT * FROM DeviceType";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            comboFieldGeraet.getItems().add(rs.getString("Devicename"));
        }
        //comboFieldGeraet.setValue(comboFieldGeraet.getItems().get(0));
        DatenbankHandler.connection.close();
    }

    @FXML
    public void Device_Type_ID(ActionEvent event) throws SQLException {
        new DatenbankHandler().Connect();
        String query = "select Device_Type_ID from DeviceType where Devicename = " + "'" + comboFieldGeraet.getValue() + "'";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String Devicename = rs.getString("Device_Type_id");
            EinstiegsPunkt.g_u_s.setDeviceTypeID(Devicename);
            EinstiegsPunkt.g_u_s.setDevicename(comboFieldGeraet.getValue().toString());
        }     
        DatenbankHandler.connection.close();
    }



    public void Combo_fontsize_new_dataset(ComboBox comb) {
        comb.setStyle("-fx-font: 16px \"Arial black\";");
    }

    public void Combo_fontsize_new_dataset2(ComboBox comb) {
        comb.setStyle("-fx-font: 14px \"Arial black\";");
    }

    public void comboboxChangeFontStyle() {
        Combo_fontsize_new_dataset(comboFieldKunde);
        Combo_fontsize_new_dataset(comboboxLieferant);
        Combo_fontsize_new_dataset(comboboxServiceLVL);
        Combo_fontsize_new_dataset(comboboxStatus);
        Combo_fontsize_new_dataset(comboFieldGeraet);
        Combo_fontsize_new_dataset(comboFieldStandort);
        Combo_fontsize_new_dataset2(ComboBox_Tag_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Monat_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Jahr_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Tag_Ende);
        Combo_fontsize_new_dataset2(ComboBox_Monat_Ende);
        Combo_fontsize_new_dataset2(ComboBox_Jahr_Ende);
    }

    public void get_insert_textfield_seriennummer() {
        EinstiegsPunkt.g_u_s.setSeriennummer(textfield_seriennummer.getText());
    }

    public void get_insert_textfield_twi_nr() {
        EinstiegsPunkt.g_u_s.setTwiNr(textfield_twi_nr.getText());
    }

    public void get_insert_textfield_mac_adresse() {
        EinstiegsPunkt.g_u_s.setMac(textfield_mac_adresse.getText());
    }

    @FXML
    public void zurück() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
    }

    public void Datensatz_in_SQL_Übergeben() throws SQLException {
        String Kundenname = comboFieldKunde.getSelectionModel().getSelectedItem().toString();
        String ServiceBegin = ComboBox_Tag_Anfang.getSelectionModel().getSelectedItem().toString() + "/" + ComboBox_Monat_Anfang.getSelectionModel().getSelectedItem().toString() + "/" + ComboBox_Jahr_Anfang.getSelectionModel().getSelectedItem().toString();
        String ServiceEnde = ComboBox_Tag_Ende.getSelectionModel().getSelectedItem().toString() + "/" + ComboBox_Monat_Ende.getSelectionModel().getSelectedItem().toString() + "/" + ComboBox_Jahr_Ende.getSelectionModel().getSelectedItem().toString();
        String StandOrt = comboFieldStandort.getSelectionModel().getSelectedItem().toString();
        String Status = comboboxStatus.getSelectionModel().getSelectedItem().toString();
        String Seriennummer = textfield_seriennummer.getText();
        String Mac_Adresse = textfield_mac_adresse.getText();
        String Twi_Nr = textfield_twi_nr.getText();
        String Liefertermin = LieferterminDT.getValue().toString();
        String ServiceLVL = comboboxServiceLVL.getSelectionModel().getSelectedItem().toString();
        String Lieferant = comboboxLieferant.getSelectionModel().getSelectedItem().toString();
        String Device_Name = comboFieldGeraet.getSelectionModel().getSelectedItem().toString();
        new DatenbankHandler().Connect();

        String query_lesbar = String.format(
                "INSERT INTO Device2 (Kunde,Standort,Device_Name,Status,Servicelvl,Lieferant,ServiceBegin,ServiceEnde,Liefertermin,Seriennummer,TWI_NR,MAC_Adresse) Values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                Kundenname, StandOrt, Device_Name, Status, ServiceLVL, Lieferant, ServiceBegin, ServiceEnde, Liefertermin, Seriennummer, Twi_Nr, Mac_Adresse);

        //String query = "INSERT INTO CUSTOMERTEST (Customer_ID,Group_ID,Kunden_name,Standort) Values (" + "'" + g_u_s.getKunden_ID() + "'" +","+ "'" + g_u_s.getGroup_id() + "'" +","+ "'" + g_u_s.getName() + "'" +","+ "'" + g_u_s.getFB_TWI() + "'" + ")";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query_lesbar);
        ResultSet rs = stm.executeQuery();

        System.out.println("geklickt " + Liefertermin);
    }
}
