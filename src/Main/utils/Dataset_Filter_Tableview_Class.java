package Main.utils;

import javafx.beans.property.SimpleStringProperty;

public class Dataset_Filter_Tableview_Class {


    private final SimpleStringProperty spalte1;
    private final SimpleStringProperty spalte2;


    public Dataset_Filter_Tableview_Class(String spalte1, String spalte2) {
        this.spalte1 = new SimpleStringProperty(spalte1);
        this.spalte2 = new SimpleStringProperty(spalte2);


    }


    public String getSpalte1() {
        return spalte1.get();
    }

    public void setSpalte1(String fName) {
        spalte1.set(fName);
    }

    public String getSpalte2() {
        return spalte2.get();
    }

    public void setSpalte2(String fName) {
        spalte2.set(fName);
    }

}
