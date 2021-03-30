package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.RealTimeUiClock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Hauptmenue implements Initializable {


    @FXML
    private Label userSystemName, systemTime, systemDate;
    @FXML
    private Button History_Button;


    @FXML
    public void SceneKundenFilter() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
    }

    @FXML
    public void New_Dataset() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.New_Dataset_Device);
    }

    public Controller_Hauptmenue() {


        System.out.println("Controller: Controller_Hauptmenue wurde geladen..");
    }

    @FXML
    public void New_Customer() throws IOException {
        EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Customer);
    }

    @FXML
    public void CloseButtonAction() {
        System.exit(1);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userSystemName.setText(EinstiegsPunkt.g_u_s.getUserSystemName());
        RealTimeUiClock rl = new RealTimeUiClock();
        rl.initclock(systemTime, systemDate);
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
}
