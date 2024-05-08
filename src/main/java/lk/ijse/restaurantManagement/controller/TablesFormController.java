package lk.ijse.restaurantManagement.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.Tables;
import lk.ijse.restaurantManagement.model.tm.TablesTm;
import lk.ijse.restaurantManagement.repository.ItemRepo;
import lk.ijse.restaurantManagement.repository.TablesRepo;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesFormController {
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colTableId;

    @FXML
    private Label lblFamilyTbl1;

    @FXML
    private Label lblFamilyTbl2;

    @FXML
    private ImageView lblSingletbl;

    @FXML
    private Label lblSingleTbl;

    @FXML
    private Label lbloutdoorTbl;

    @FXML
    private TableView<TablesTm> tblTables;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtTableId;
    @FXML
    private AnchorPane root;
    private List<Tables> tablesList=new ArrayList<>();
    private String[] labelList={"lblFamilyTbl1","lblFamilyTbl2","lblSingleTbl","lbloutdoorTbl"};

    private void initialize() {
        this.tablesList=getAllTables();
        setCellValueFactory();
        loadItemTable();
    }

    private List<Tables> getAllTables() {
        List<Tables> tablesList = null;
        try {
            tablesList = TablesRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tablesList;
    }

    private void loadItemTable() {
        ObservableList<TablesTm> tmList = FXCollections.observableArrayList();

        for (Tables tables : tablesList) {
            TablesTm tablesTm = new TablesTm(
                    tables.getTableId(),
                    tables.getDescription()
            );


            tmList.add(tablesTm);
        }
        tblTables.setItems(tmList);
        TablesTm selectedItem = tblTables.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }



    public void addOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String description = txtDescription.getText();

        try {
            boolean isDeleted = TablesRepo.delete(description);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Table deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage)root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String tablesId = txtTableId.getText();
        String description = txtDescription.getText();


        Tables tables = new Tables(tablesId,description);
        try {
            boolean isUpdated = TablesRepo.update(tables);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Table updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtTableId.setText("");
        txtDescription.setText("");

    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String tableId = txtTableId.getText();
        String description = txtDescription.getText();

        Tables tables = new Tables(tableId,description);

        try {

            boolean isSaved = TablesRepo.save(tables);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Table saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
    }

    public void ClickOnAction(MouseEvent mouseEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
        String description  = txtDescription.getText();

        try {
            Tables tables = TablesRepo.searchByDescription(description);

            if (tables != null) {
                txtTableId.setText(tables.getTableId());
                txtDescription.setText(tables.getDescription());

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }


   /* public void ClickOnAction(MouseEvent mouseEvent) {
        labelList selectedItem = lblFamilyTbl1.getLabelFor().;
        colDescription.setText(selectedItem.());

    }*/
}
