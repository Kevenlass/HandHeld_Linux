package Main.utils;


import Main.BCrypt.BCrypt;
import Main.EinstiegsPunkt;
import Main.enums.FXML_Scenes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.prefs.Preferences;


public class DatenbankHandler {
    public static Connection connection;


    //public static Connection conn2;
    Preferences rootUserPrefs = Preferences.userRoot();
    private final String user = "Keven";
    private final String password = "Asusg50v";

    private final String Db_LoginName = rootUserPrefs.get("dbuserid", "theloginnameforthedb");
    private final String Db_password = rootUserPrefs.get("dbuserpassword", "thedbloginpassword");

    final int port = rootUserPrefs.getInt("port", 8080);
    final String sid = rootUserPrefs.get("sid", "mysid");
    final String IpAdress = rootUserPrefs.get("Hostip", "MyDBServerIp");



    /*#############################/
   # Datenbank Verbindung Handheld##
    #############################*/
    public Object Connect(String query) throws SQLException {
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", Db_LoginName, Db_password);
        PreparedStatement pstm = connection.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();
        ObservableList<String> db = FXCollections.observableArrayList();
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columns; i++) {
            System.out.print(String.format("%-15s", rs.getMetaData().getColumnName(i)));
            db.add(String.format("%-15s",rs.getMetaData().getCatalogName(i)));
        }
        System.out.println();
        while (rs.next()) {

            for (int i = 1; i <= columns; i++) {
                System.out.print(String.format("%-15s", rs.getString(i)));
                db.add(String.format("%-15s", rs.getString(i)));
            }
            System.out.println();
        }
        //System.out.println("Mit Oracle Verbunden!!");
        return db;
    }

    public void Connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", Db_LoginName, Db_password);
        System.out.println("Mit Oracle Verbunden!!");
    }

    /*#############################/
    # Datenbank Verbindung Trennen##
     #############################*/
    public void Disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*#############################/
    # UserLogin Check  ##########
     #############################*/
    public void loginCheck(String password, String username) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {


        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", this.Db_LoginName, this.Db_password);
        //connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:" + port + ":xe", this.user, this.password);
        // String Query = "Select * From Login where username = ?,Salt =? ";
        String Query = "Select Username,Hash From Login";
        PreparedStatement stm = this.connection.prepareStatement(Query);
        ResultSet rs = stm.executeQuery();
        boolean flag = false;
        while (rs.next()) {
            String Username = rs.getString("USERNAME");
            String Hash = rs.getString("Hash");

            if (username.equals(Username)) {
                flag = true;
                if (BCrypt.checkpw(password, Hash)) {
                    EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Dataset_Filter);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Passwort nicht korrekt").showAndWait();
                }
                //rs.close();
            }
        }
        if (!flag) {
            new Alert(Alert.AlertType.ERROR, "Username Nicht Vorhanden").showAndWait();
        }
    }

    /*#############################/
    # Neuen User anlegen in der DB #
     #############################*/
    public void createNewUser(String Password, String username) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", Db_LoginName, Db_password);

        if (!Password.equals("") || !Password.isEmpty()) {
            if (!username.isEmpty() || !username.equals("")) {
                String PasswordHash = BCrypt.hashpw(Password, BCrypt.gensalt());

                //Inserting values
                String query = "INSERT INTO Login(USERNAME,SALT,HASH) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, null);
                pstmt.setString(3, PasswordHash);

                System.out.println(username + "        " + PasswordHash);
                pstmt.execute();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Passwort oder User dürfen nicht leer sein").showAndWait();
        }
    }

    public void deleteExistUser(String username) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", Db_LoginName, Db_password);

        String query = "Delete from Login where username = ?";

        PreparedStatement pstm = conn.prepareStatement(query);

        pstm.setString(1, username);
        pstm.executeQuery();
    }

    public void passwordReset(String username, String password) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:oracle:thin:@" + IpAdress + ":" + port + ":" + sid + "", Db_LoginName, Db_password);
        String PasswordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String query = "Update Login set (Username,Hash,Salt) Values (?,?,?) where username = ?";
        if (!username.isEmpty() || !username.equals("")) {
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, username);
            pstm.setString(2, null);
            pstm.setString(3, PasswordHash);
            pstm.setString(4, username);

            pstm.executeQuery();
        }
    }

    public void Admin_Settings_Login(String Password, String Username, String Hash, String usernameAdmin) throws IOException {

        if (Username.equals(usernameAdmin)) {
            if (BCrypt.checkpw(Password, Hash)) {
                EinstiegsPunkt.sceneSwitcher.changeScene(FXML_Scenes.Settings);
            }
        }
    }
}
