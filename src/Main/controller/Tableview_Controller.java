package Main.controller;

import javafx.beans.property.SimpleStringProperty;


/* Klasse um eine reihe aus der datenbank in das tableview #
 # zu bringen damit man die objekte einzeln sich wieder aus #
 # der tableview holen kann */

public class Tableview_Controller {


    private final SimpleStringProperty spalte1;
    private final SimpleStringProperty spalte2;
    private final SimpleStringProperty spalte3;
    private final SimpleStringProperty spalte4;
    private final SimpleStringProperty spalte5;
    private final SimpleStringProperty spalte6;
    private final SimpleStringProperty spalte7;
    private final SimpleStringProperty spalte8;
    private final SimpleStringProperty spalte9;
    private final SimpleStringProperty spalte10;
    private final SimpleStringProperty spalte11;
    private final SimpleStringProperty spalte12;

    public Tableview_Controller(String spalte1, String spalte2, String spalte3, String spalte4, String spalte5, String spalte6, String spalte7, String spalte8, String spalte9, String spalte10, String spalte11, String spalte12) {
        this.spalte1 = new SimpleStringProperty(spalte1);
        this.spalte2 = new SimpleStringProperty(spalte2);
        this.spalte3 = new SimpleStringProperty(spalte3);
        this.spalte4 = new SimpleStringProperty(spalte4);
        this.spalte5 = new SimpleStringProperty(spalte5);
        this.spalte6 = new SimpleStringProperty(spalte6);
        this.spalte7 = new SimpleStringProperty(spalte7);
        this.spalte8 = new SimpleStringProperty(spalte8);
        this.spalte9 = new SimpleStringProperty(spalte9);
        this.spalte10 = new SimpleStringProperty(spalte10);
        this.spalte11 = new SimpleStringProperty(spalte11);
        this.spalte12 = new SimpleStringProperty(spalte12);

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

    public String getSpalte3() {
        return spalte3.get();
    }

    public void setSpalte3(String fName) {
        spalte3.set(fName);
    }

    public String getSpalte4() {
        return spalte4.get();
    }

    public void setSpalte4(String fName) {
        spalte4.set(fName);
    }

    public String getSpalte5() {
        return spalte5.get();
    }

    public void setSpalte5(String fName) {
        spalte5.set(fName);
    }

    public String getSpalte6() {
        return spalte6.get();
    }

    public void setSpalte6(String fName) {
        spalte6.set(fName);
    }

    public String getSpalte7() {
        return spalte7.get();
    }

    public void setSpalte7(String fName) {
        spalte7.set(fName);
    }

    public String getSpalte8() {
        return spalte8.get();
    }

    public void setSpalte8(String fName) {
        spalte8.set(fName);
    }

    public String getSpalte9() {
        return spalte9.get();
    }

    public void setSpalte9(String fName) {
        spalte9.set(fName);
    }

    public String getSpalte10() {
        return spalte10.get();
    }

    public void setSpalte10(String fName) {
        spalte10.set(fName);
    }

    public String getSpalte11() {
        return spalte11.get();
    }

    public void setSpalte11(String fName) {
        spalte11.set(fName);
    }

    public String getSpalte12() {
        return spalte12.get();
    }

    public void setSpalte12(String fName) {
        spalte12.set(fName);
    }




}
