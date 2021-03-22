package Main;

import Main.enums.FXML_Scenes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneSwitcher {

    public void changeScene(FXML_Scenes fxml) throws IOException {
        String pfad = "fxml/" + fxml.toString() + ".fxml";
        System.out.println("Mein Pfad: " + pfad);
        Parent pane = FXMLLoader.load(
                getClass().getResource(pfad));
        EinstiegsPunkt.stage.setScene(new Scene(pane));
        EinstiegsPunkt.stage.show();
    }
}

