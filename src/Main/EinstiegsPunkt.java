package Main;

import Main.enums.FXML_Scenes;
import Main.utils.Getter_Und_Setter;
import Main.utils.Tableview_Object;
import javafx.application.Application;
import javafx.stage.Stage;


public class EinstiegsPunkt extends Application {

    public static Stage stage;
    public static SceneSwitcher sceneSwitcher;

    public static Getter_Und_Setter g_u_s = new Getter_Und_Setter();
    public static Tableview_Object tbo = new Tableview_Object();

    @Override
    public void start(Stage primaryStage) throws Exception {

//        int count = 0;
//        String query = "Select * From Device2";
//        DatenbankHandler dbh = new DatenbankHandler();
//        ObservableList<Object> obsl = (ObservableList<Object>) dbh.Connect(query);
//        List<Object> Test = Collections.singletonList(dbh.Connect(query));
//        // TableView<Object> tbl = new TableView<>();
//        List<String> ls = new ArrayList<>();
//        for (Object o : obsl) {
//            // tbl.setItems(obsl);
//            System.out.print(o);
//            count++;
//
//        }


        stage = primaryStage;
        sceneSwitcher = new SceneSwitcher();
        sceneSwitcher.changeScene(FXML_Scenes.Login);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
