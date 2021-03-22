package Main.utils;

import javafx.scene.control.ComboBox;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ZeitComboBoxen {
    private List<String> monate_liste;
    public ZeitComboBoxen() {
        monate_liste = Arrays.asList(new String[]{
                "Januar",
                "Februar",
                "März",
                "April",
                "Mai",
                "Juni",
                "Juli",
                "August",
                "September",
                "Oktober",
                "November",
                "Dezember"
        }.clone());
    }
    public void LoadComboBoxes(ComboBox ComboBox_Tag_Anfang,ComboBox ComboBox_Monat_Anfang,ComboBox ComboBox_Jahr_Anfang,ComboBox ComboBox_Tag_Ende,ComboBox ComboBox_Monat_Ende,ComboBox ComboBox_Jahr_Ende ) throws SQLException {
        anfang_ServiceZeit(31, ComboBox_Tag_Anfang, false);
        anfang_ServiceZeit(12, ComboBox_Monat_Anfang, true);
        ende_ServiceZeit(1950, 2020, ComboBox_Jahr_Anfang);
        anfang_ServiceZeit(31, ComboBox_Tag_Ende, false);
        anfang_ServiceZeit(12, ComboBox_Monat_Ende, true);
        ende_ServiceZeit(1950, 2020, ComboBox_Jahr_Ende);
    }

    private void anfang_ServiceZeit(int zahl, ComboBox combobox, boolean monate) {
        for (int i = 1; i <= zahl; i++) {
            if (monate) {

                int finalI = i;
                combobox.getItems().add(monate_liste.get(i - 1));
            } else {
                combobox.getItems().add(i);
            }
        }
        combobox.setValue(combobox.getItems().get(0));
    }

    private void ende_ServiceZeit(int jahresnang, int jahresende, ComboBox combobox) {
        for (int i = jahresnang; i <= jahresende; i++) {
            combobox.getItems().add(i);
        }
        combobox.setValue(combobox.getItems().get(0));
    }
}
