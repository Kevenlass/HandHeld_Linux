package Main.controller;

import Main.EinstiegsPunkt;
import Main.utils.DatenbankHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

//import javafx.*;


public class Controller_Login implements Initializable {


    @FXML
    private TextField Username;
    @FXML
    private PasswordField Pwbox;
    @FXML
    Button Login;
    @FXML
    private Label settings;


    /* ### Dieses Event ruft eine Methode auf in der klasse  ###
    ### DatenbankHandler um in der datenbank Abzugleichen  ###
    ### ob der user existiert für den login.             ###  */
    @FXML
    private void OnAction(ActionEvent event) throws IOException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {
        new DatenbankHandler().loginCheck(Pwbox.getText(), Username.getText());
    }


    /* Dieses KeyEvent greift den ENTER key ab in der #
    # Passwortbox (Für den login mit der ENTER taste) */
    @FXML
    private void pressedEnterKey(KeyEvent ke) throws InvalidKeySpecException, SQLException, NoSuchAlgorithmException, IOException {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            new DatenbankHandler().loginCheck(Pwbox.getText(), Username.getText());
            //new Alert(Alert.AlertType.ERROR, "Falsches passwort").showAndWait();
            System.out.println("Test");
        }
    }

    /* ### Controller Klasse Konstruktor ### */
    public Controller_Login() {

        System.out.println("Controller: Controller_Login wurde geladen..");
    }

    /* Das Initializable Interface */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EinstiegsPunkt.g_u_s.setUserSystemName(System.getProperty("user.name"));
        Preferences rootUserPrefs = Preferences.userRoot();
    }

    @FXML
    public void getSettings() throws IOException, NoSuchAlgorithmException, SQLException, InvalidKeySpecException {

        HashMap<String, String> Adminlogin = new HashMap<>();
        Adminlogin.put("Username", "Admin");
        Adminlogin.put("Hash", "$2a$10$Nw6h7ZD.GUC7CZROuTvB9eeF90N0pRigqHK77TSVQKZBz0Nauhje.");

        new DatenbankHandler().Admin_Settings_Login(Pwbox.getText(),Username.getText(),Adminlogin.get("Hash"),Adminlogin.get("Username"));
    }
}


