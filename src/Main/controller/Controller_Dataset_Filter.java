package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_Dataset_Filter implements Initializable {

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
        EinstiegsPunkt.g_u_s.setWelcherKunde(ListView_Customer.getSelectionModel().getSelectedItem().toString());
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
}
