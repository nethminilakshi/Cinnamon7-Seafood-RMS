package lk.ijse.restaurantManagement.util;

import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex  {
    public static boolean isTextFiledsValid(TextField textFields, String text){
        String field = "";

        switch (textFields){
            case ID :
                field = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                field = "^(([A-z|\\\\\\s]{3,})| ([\\\\\\s][A-z|\\\\\\s]{3,}))$";
                break;
            case PW:
                field = "^(([A-z]{5})([\\W])([0-9]{4}))|(([A-z]{4})([\\W])([0-9]{4}))|(([A-z]{6})([\\W])([0-9]{4}))|(([A-z]{7})([\\W])([0-9]{4}))|(([A-z]{8})([\\W])([0-9]{4}))$";
                break;
            case QTY:
                field = "^([\\d]{3}|[\\d]{2}|[\\d]{1})$";
                break;
            case DESC:
                field = "^([A-z|\\s]{4,})$";
                break;
            case TYPE:
                field = "^([A-z|\\s]{4,})$";
                break;
            case USERID:
                field = "^(([U])([0-9]{3}))$";
                break;
            case ADDRESS:
                field = "^([A-z0-9]|[-/,.@+]|\\\\s){4,}$";
                break;
            case CONTACT:
                field = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case EMAIL:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case AMOUNT:
                field = "^([\\d]{3}|[\\d]{2}|[\\d]{1})$";
                break;
            case POSITION:
                field =  "^(([A-z|\\\\\\s]{3,})| ([\\\\\\s][A-z|\\\\\\s]{3,}))$";
                break;
            case PAYMETHOD:
                field =  "^([A-z|\\s]{4,})$";
                break;
            case STATUS:
                field =  "^([A-z|\\s]{4,})$";
                break;
            case UNITPRICE:
                field =   "^([\\d]{3}|[\\d]{2}|[\\d]{1})$";
                break;


        }

        Pattern pattern = Pattern.compile(field);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }
    public static boolean setTextColor(TextField location, javafx.scene.control.TextField field){
        if (Regex.isTextFiledsValid(location,field.getText())){
           // field.setFocusColor(Paint.valueOf("Green"));
           // field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
           // field.setFocusColor(Paint.valueOf("Red"));
           // field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
    public static boolean setTextColor(TextField location, JFXPasswordField field){
        if (Regex.isTextFiledsValid(location,field.getText())){
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
}
