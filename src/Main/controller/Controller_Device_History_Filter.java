package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Device_History_Filter {
    @FXML
    private Button Abort;

    public void Abort(){

        try {
            Stage stage = (Stage) Abort.getScene().getWindow();
            stage.close();
            EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Hauptmenue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
