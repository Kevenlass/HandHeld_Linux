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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Controller_Dataset_Device implements Initializable {

    @FXML
    private Label Datum_Label;
    @FXML
    private ComboBox ComboBox_Tag_Anfang, ComboBox_Monat_Anfang, ComboBox_Jahr_Anfang, ComboBox_Tag_Ende, ComboBox_Monat_Ende, ComboBox_Jahr_Ende;
    @FXML
    private ComboBox comboboxStatus, comboboxServiceLVL, comboboxLieferant, comboFieldGeraet, comboFieldKunde, comboFieldStandort;
    @FXML
    private TextField textfield_twi_nr, textfield_mac_adresse, textfield_seriennummer;
    @FXML
    private Button datensatz_anlegen;
    @FXML
    private DatePicker LieferterminDT;
    private List<String> monate_liste;

    @FXML
    private void time() {
        LocalDate CurrentDate = LocalDate.now();
        Datum_Label.setText(CurrentDate.toString());
    }

    public Controller_Dataset_Device() {
        monate_liste = Arrays.asList(new String[]{
                "Januar",
                "Februar",
                "März",
                "April",
                "Mai",
                "Juni",
                "Juli",
                "August",
                "September",
                "Oktober",
                "November",
                "Dezember"
        }.clone());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        time();
        try {
            // Ersetzen durch Datepicker controls !!!!!!!!!!!!!!!!!!
            //new ZeitComboBoxen().LoadComboBoxes(ComboBox_Tag_Anfang, ComboBox_Monat_Anfang, ComboBox_Jahr_Anfang, ComboBox_Tag_Ende, ComboBox_Monat_Ende, ComboBox_Jahr_Ende);
            kundencmbxladen();
            DeviceTypeTabelle();
            CustomerTabelle();
            combobox_change_font_style();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Controller: Controller_New_Dataset wurde geladen..");
    }

    private void kundencmbxladen() throws SQLException {
        DeviceTabelle2(comboboxStatus, "Status");
        DeviceTabelle2(comboboxLieferant, "lieferant");
        DeviceTabelle2(comboboxServiceLVL, "Servicelvl");
        //comboboxStatus.setValue(comboboxStatus.getItems().get(0));
        //comboboxLieferant.setValue(comboboxLieferant.getItems().get(0));
        //comboboxServiceLVL.setValue(comboboxServiceLVL.getItems().get(0));
    }

    public void Datensatz_Anlegen_Group_ID(String kunden_name, String group_id) {

        if (kunden_name.equals(comboFieldKunde.getSelectionModel().getSelectedItem())) {
            // g_u_s.setGroup_id(group_id);
            EinstiegsPunkt.g_u_s.setGroup_id(group_id);
        }
    }

    public void Zuweisung_Standort() throws SQLException {
        new DatenbankHandler().Connect();

        String query = "select Standort from Customer where group_id = " + "'" + EinstiegsPunkt.g_u_s.getGroup_id().toString() + "'";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
        comboFieldStandort.requestLayout();
        comboFieldStandort.getItems().clear();
        while (rs.next()) {
            comboFieldStandort.getItems().add(rs.getString("Standort"));
        }
        DatenbankHandler.connection.close();
    }

    public void comboAction_Kunde(ActionEvent event) {
        Datensatz_Anlegen_Group_ID("", "0");
        Datensatz_Anlegen_Group_ID("Lekkerland", "2");
        Datensatz_Anlegen_Group_ID("Bodan", "3");
        Datensatz_Anlegen_Group_ID("TWI", "1");
        Datensatz_Anlegen_Group_ID("GGT", "4");
        Datensatz_Anlegen_Group_ID("Fixmer", "5");
        Datensatz_Anlegen_Group_ID("HvL", "6");
        try {
            Zuweisung_Standort();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //comboFieldStandort.setValue(comboFieldStandort.getItems().get(0));
    }

    public void CustomerTabelle() throws SQLException {
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

    private void DeviceTabelle2(ComboBox comboBox, String ColumLabel) throws SQLException {
        new DatenbankHandler().Connect();
        String query = "SELECT * FROM Device2";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            String Status = rs.getString(ColumLabel);
            boolean bool = false;
            for (int i = 0; i < comboBox.getItems().size(); i++) {

                if (Status != null) {
                    if (Status.equals(comboBox.getItems().get(i))) {
                        bool = true;
                    }
                }
            }
            if (bool == false && Status != null) {
                comboBox.getItems().add(rs.getString(ColumLabel));
                //comboBox.setValue(comboboxStatus.getItems().get(0));
            }
        }
        DatenbankHandler.connection.close();
    }

    public void Combo_fontsize_new_dataset(ComboBox comb) {
        comb.setStyle("-fx-font: 16px \"Arial black\";");
    }

    public void Combo_fontsize_new_dataset2(ComboBox comb) {
        comb.setStyle("-fx-font: 14px \"Arial black\";");
    }

    public void combobox_change_font_style() {
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
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Hauptmenue);
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
