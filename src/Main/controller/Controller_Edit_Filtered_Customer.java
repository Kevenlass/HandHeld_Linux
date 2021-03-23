package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_Edit_Filtered_Customer implements Initializable {
    @FXML
    private TextField Kunde_Textbox, Standort_Textbox, Status_Textbox,
            Devicename_Textbox, ServiceAnfang_Textbox, ServiceEnde_Texbox,
            ServiceLvl_Textbox, Lieferant_Textbox, Liefertermin_Textbox,
            Seriennummer_Textbox, TwiNr_textbox, MacAdresse_Textbox;

    @FXML
    private Button Abort_Button;


    public Controller_Edit_Filtered_Customer() {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Kunde_Textbox.setText(EinstiegsPunkt.tbo.getKundennamen());
        Standort_Textbox.setText(EinstiegsPunkt.tbo.getStandort());
        Status_Textbox.setText(EinstiegsPunkt.tbo.getstatus());
        Devicename_Textbox.setText(EinstiegsPunkt.tbo.getGeraetenamen());
        ServiceAnfang_Textbox.setText(EinstiegsPunkt.tbo.getServiceAnfang());
        ServiceEnde_Texbox.setText(EinstiegsPunkt.tbo.getServiceende());
        ServiceLvl_Textbox.setText(EinstiegsPunkt.tbo.getservicelvl());
        Lieferant_Textbox.setText(EinstiegsPunkt.tbo.getlieferant());
        Liefertermin_Textbox.setText(EinstiegsPunkt.tbo.getLiefertermin());
        Seriennummer_Textbox.setText(EinstiegsPunkt.tbo.getSeriennummer());
        TwiNr_textbox.setText(EinstiegsPunkt.tbo.getTwiNr());
        MacAdresse_Textbox.setText(EinstiegsPunkt.tbo.getMac());
    }

    @FXML
    public void Abort() {

        try {
            Stage stage = (Stage) Abort_Button.getScene().getWindow();
            stage.close();
            EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Filtered_Customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void InsertAndUpdate() throws SQLException {


        new DatenbankHandler().Connect();
        String Kundenname = Kunde_Textbox.getText().toString();
        String ServiceBegin = ServiceAnfang_Textbox.getText().toString();
        String ServiceEnde = ServiceEnde_Texbox.getText().toString();
        String StandOrt = Standort_Textbox.getText().toString();
        String Status = Status_Textbox.getText().toString();
        String Seriennummer = Seriennummer_Textbox.getText().toString();
        String Mac_Adresse = MacAdresse_Textbox.getText().toString();
        String Twi_Nr = TwiNr_textbox.getText().toString();
        String Liefertermin = Liefertermin_Textbox.getText().toString();
        String ServiceLVL = ServiceLvl_Textbox.getText().toString();
        String Lieferant = Lieferant_Textbox.getText().toString();
        String Device_Name = Devicename_Textbox.getText().toString();
        String queryInstert = String.format(
                "INSERT INTO History (Kunde,Standort,Device_Name,Status,Servicelvl,Lieferant,ServiceBegin,ServiceEnde,Liefertermin,Seriennummer,TWI_NR,MAC_Adresse) Values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                Kunde_Textbox.getText(), Standort_Textbox.getText(), Devicename_Textbox.getText(), Status_Textbox.getText(), ServiceLvl_Textbox.getText(), Lieferant_Textbox.getText(), ServiceAnfang_Textbox.getText(), ServiceEnde_Texbox.getText(), Liefertermin_Textbox.getText(), Seriennummer_Textbox.getText(), TwiNr_textbox.getText(), MacAdresse_Textbox.getText());
        String queryUpdate = String.format(
                "UPDATE DEVICE2 SET(Kunde,Standort,Device_Name,Status,Servicelvl,Lieferant,ServiceBegin,ServiceEnde,Liefertermin,Seriennummer,TWI_NR,MAC_Adresse) = (Select '%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s' from dual) where TWI_NR="+"'"+ Twi_Nr+"",
                Kundenname,StandOrt, Device_Name, Status, ServiceLVL, Lieferant, ServiceBegin, ServiceEnde, Liefertermin, Seriennummer, Twi_Nr, Mac_Adresse);

        PreparedStatement stm = DatenbankHandler.connection.prepareStatement(queryInstert);
        PreparedStatement stm2 = DatenbankHandler.connection.prepareStatement(queryUpdate);
        ResultSet rs2 = stm2.executeQuery();
        ResultSet rs = stm.executeQuery();


    }
}
