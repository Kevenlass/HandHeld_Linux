package Main.controller;

import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import Main.utils.DatenbankHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Controller_Settings implements Initializable {
    @FXML
    private Button Save_Button;
    @FXML
    private TextField IpAdresseTextbox, sid_Textfield, dbuserpasswordTextfield, dbuseridTextfield, NewUser_textbox, NewUserPasswort_TextBox, resetPassword_textfield;
    @FXML
    private ComboBox DeleteUser_ComboBox, PasswortReset_ComboBox;

    Preferences rootUserPrefs = Preferences.userRoot();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            existingUserComboboxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Save_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ipAdress();
                sid();
                user_id_password();
                try {
                    newUser();
                    deleteExistUser();
                    resetPassword();
                    EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Login);
                    Stage stage = (Stage) Save_Button.getScene().getWindow();
                    stage.close();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void portSelected() {
        rootUserPrefs.putInt("port", 1521);
        System.out.println("1521");
    }

    @FXML
    public void whichRbIsClicked2() {
        rootUserPrefs.putInt("port", 1522);
        System.out.println("1522");
    }

    @FXML
    private void ipAdress() {
        if (IpAdresseTextbox.getText().isEmpty()) {

        } else {
            rootUserPrefs.put("Hostip", IpAdresseTextbox.getText());
        }
    }

    @FXML
    private void sid() {
        if (sid_Textfield.getText().isEmpty()) {
            System.out.println("sid nicht drinnen");

        } else {
            rootUserPrefs.put("sid", sid_Textfield.getText());
            System.out.println("sid gespeichert");

        }
    }

    private void user_id_password() {
        if (dbuserpasswordTextfield.getText().isEmpty() || dbuseridTextfield.getText().isEmpty() || dbuseridTextfield.getText().equals("")) {
            System.out.println("ist leer");
        } else {
            rootUserPrefs.put("dbuserid", dbuseridTextfield.getText());
            rootUserPrefs.put("dbuserpassword", dbuserpasswordTextfield.getText());

        }
    }

    private void newUser() throws SQLException {
        DatenbankHandler dt = new DatenbankHandler();
        dt.createNewUser(NewUserPasswort_TextBox.getText(), NewUser_textbox.getText());
    }

    private void existingUserComboboxes() throws SQLException {
        new DatenbankHandler().Connect();

        String query = "SELECT * FROM Login";
        PreparedStatement pstm = DatenbankHandler.connection.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();
        DeleteUser_ComboBox.getItems().add("Default");
        PasswortReset_ComboBox.getItems().add("Default");
        while (rs.next()) {

            String user = rs.getString("Username");

            DeleteUser_ComboBox.getItems().add(rs.getString("USERNAME"));
            PasswortReset_ComboBox.getItems().add(rs.getString("Username"));
        }

    }

    private void deleteExistUser() throws SQLException {
        if (!DeleteUser_ComboBox.getItems().equals("Default") ) {
            if (DeleteUser_ComboBox.getSelectionModel().getSelectedItem() != null) {
                String userNameCombo = DeleteUser_ComboBox.getSelectionModel().getSelectedItem().toString();
                DatenbankHandler dbh = new DatenbankHandler();
                dbh.deleteExistUser(userNameCombo);
            }else if (DeleteUser_ComboBox.getSelectionModel().getSelectedItem() == null || DeleteUser_ComboBox.getSelectionModel().getSelectedItem().equals("")) {
                System.out.println("delete user is null");
            }
        }
    }

    private void resetPassword() throws SQLException {
        if (PasswortReset_ComboBox.getSelectionModel().getSelectedItem() != "Default" && PasswortReset_ComboBox.getSelectionModel().getSelectedItem() != null) {
            DatenbankHandler dbh = new DatenbankHandler();
            String username = PasswortReset_ComboBox.getSelectionModel().getSelectedItem().toString();
            String password = resetPassword_textfield.getText();

            dbh.passwordReset(username, password);

        } else if (PasswortReset_ComboBox.getSelectionModel().getSelectedItem() == null || PasswortReset_ComboBox.getSelectionModel().getSelectedItem().equals("")) {
            System.out.println("passwort reset is null");
        }
    }
}



