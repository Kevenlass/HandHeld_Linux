package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import Main.utils.Getter_Und_Setter;
import Main.utils.ZeitComboBoxen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller_New_Dataset_Customer implements Initializable {

    public Getter_Und_Setter g_u_s = new Getter_Und_Setter();
    @FXML
    private Label Datum_Label;
    @FXML
    private ComboBox ComboBox_Tag_Anfang, ComboBox_Monat_Anfang, ComboBox_Jahr_Anfang, ComboBox_Tag_Ende, ComboBox_Monat_Ende, ComboBox_Jahr_Ende;
    @FXML
    private ComboBox comboboxServiceLVL, comboFieldStandort;
    @FXML
    private Button datensatz_anlegen;

    @FXML
    private void time() {
        LocalDate CurrentDate = LocalDate.now();
        Datum_Label.setText(CurrentDate.toString());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        time();
        try {
           // LoadComboBoxes();
            combobox_change_font_style();
            Zuweisung_Standort();
            new ZeitComboBoxen().LoadComboBoxes(ComboBox_Tag_Anfang,ComboBox_Monat_Anfang,ComboBox_Jahr_Anfang,ComboBox_Tag_Ende,ComboBox_Monat_Ende,ComboBox_Jahr_Ende);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Controller: Controller_New_Dataset wurde geladen..");
    }


//    public void Datensatz_Anlegen_Group_ID(String kunden_name, String group_id) {
//
//        if (kunden_name.equals(comboFieldKunde.getSelectionModel().getSelectedItem())) {
//            g_u_s.setGroup_id(group_id);
//        }
//    }


    private void Zuweisung_Standort() throws SQLException {


        new DatenbankHandler().Connect();

        String query = "select Standort from Customer";
        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = stm.executeQuery();
//        comboFieldStandort.requestLayout();
//        comboFieldStandort.getItems().clear();
        while (rs.next()) {
            String comboadd = rs.getString("Standort");
            boolean exists = false;
            for (int i = 0; i < comboFieldStandort.getItems().size(); i++) {
                if (comboadd.equals(comboFieldStandort.getItems().equals(i))) {
                    exists = true;
                }

            }
            if (exists == false) {
                comboFieldStandort.getItems().add(comboadd);
            }

        }

    }

//    public void comboAction_Kunde(ActionEvent event) {
//        Datensatz_Anlegen_Group_ID("", "0");
//        Datensatz_Anlegen_Group_ID("Lekkerland", "2");
//        Datensatz_Anlegen_Group_ID("Bodan", "3");
//        Datensatz_Anlegen_Group_ID("TWI", "1");
//        Datensatz_Anlegen_Group_ID("GGT", "4");
//        Datensatz_Anlegen_Group_ID("Fixmer", "5");
//        Datensatz_Anlegen_Group_ID("HvL", "6");
//        try {
//
//            Zuweisung_Standort();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        //comboFieldStandort.setValue(comboFieldStandort.getItems().get(0));
//    }


    private void Combo_fontsize_new_dataset(ComboBox comb) {
        comb.setStyle("-fx-font: 16px \"Arial black\";");
    }

    private void Combo_fontsize_new_dataset2(ComboBox comb) {
        comb.setStyle("-fx-font: 14px \"Arial black\";");
    }

    private void combobox_change_font_style() {


        Combo_fontsize_new_dataset(comboboxServiceLVL);


        Combo_fontsize_new_dataset(comboFieldStandort);
        Combo_fontsize_new_dataset2(ComboBox_Tag_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Monat_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Jahr_Anfang);
        Combo_fontsize_new_dataset2(ComboBox_Tag_Ende);
        Combo_fontsize_new_dataset2(ComboBox_Monat_Ende);
        Combo_fontsize_new_dataset2(ComboBox_Jahr_Ende);
    }



    @FXML
    private void zurück() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
    }

    private void Datensatz_in_SQL_Übergeben() throws SQLException {


//        new DatenbankHandler();
//
//        String query_lesbar = String.format(
//                "INSERT INTO CUSTOMERTEST (Customer_ID,Group_ID,Kunden_name,Standort) Values ('%s','%s','%s','%s')",
//                g_u_s.getKunden_ID(), g_u_s.getGroup_id(), g_u_s.getKundenname(), g_u_s.getStandort());
//
//        //String query = "INSERT INTO CUSTOMERTEST (Customer_ID,Group_ID,Kunden_name,Standort) Values (" + "'" + g_u_s.getKunden_ID() + "'" +","+ "'" + g_u_s.getGroup_id() + "'" +","+ "'" + g_u_s.getName() + "'" +","+ "'" + g_u_s.getFB_TWI() + "'" + ")";
//        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(query_lesbar);
//        ResultSet rs = stm.executeQuery();
//
//        System.out.println("geklickt");
    }
}
