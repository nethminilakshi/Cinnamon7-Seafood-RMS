package lk.ijse.restaurantManagement.controller;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.tm.CustomerTm;
import lk.ijse.restaurantManagement.model.tm.ItemTm;
import lk.ijse.restaurantManagement.repository.CustomerRepo;
import lk.ijse.restaurantManagement.repository.ItemRepo;
import lk.ijse.restaurantManagement.repository.PaymentRepo;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ItemFormController {

    @FXML
    private ComboBox<String> cmbStatus;


    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;
    private List<Item> itemList=new ArrayList<>();
    private Alert alert;
    public void initialize() {

        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.itemList=getAllItems();
        getItemStatus();
        setCellValueFactory();
        loadItemTable();

    }

    private List<Item> getAllItems() {
        List<Item> itemList = null;
        try {
            itemList = ItemRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }


    private String[] statusList={"Available","Unavailable"};

    public void getItemStatus(){
        List<String> statuslist = new ArrayList<>();
        for(String data: statusList){
            statuslist.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(statuslist);
        cmbStatus.setItems(obList);
    }
    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        for (Item item : itemList) {
            ItemTm itemTm = new ItemTm(
                    item.getId(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice(),
                    item.getStatus()
            );


            tmList.add(itemTm);
        }
        tblItem.setItems(tmList);
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }
    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (isValidate()) {
            String description = txtName.getText();

            try {
                boolean isDeleted = ItemRepo.delete(description);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (isValidate()){
        String id = txtId.getText();
        String name = txtName.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String unitPrice = txtUnitPrice.getText();
        String status=String.valueOf(cmbStatus.getValue());

        Item item = new Item(id, name, qtyOnHand,unitPrice,status);

        try {

            boolean isSaved = ItemRepo.save(item);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
    }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
        cmbStatus.setValue("");
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        if (isValidate()){
        clearFields();
    }}

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isValidate()) {
            String id = txtId.getText();
            String name = txtName.getText();
            String qtyOnHand = txtQtyOnHand.getText();
            String unitPrice = txtUnitPrice.getText();
            String status = String.valueOf(cmbStatus.getValue());

            Item item = new Item(id, name, qtyOnHand, unitPrice, status);
            try {
                boolean isUpdated = ItemRepo.update(item);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Item updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            clearFields();
            initialize();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent)throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void searchOnAction(ActionEvent actionEvent) {

            String description = txtName.getText();

            try {
                Item item = ItemRepo.searchByDescription(description);

                if (item != null) {
                    txtId.setText(item.getId());
                    txtName.setText(item.getDescription());
                    txtQtyOnHand.setText(item.getQtyOnHand());
                    txtUnitPrice.setText(item.getUnitPrice());
                    cmbStatus.setValue(item.getStatus());

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }

    public void tblClickOnAction(MouseEvent mouseEvent) {
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        txtId.setText(selectedItem.getId());
        txtName.setText(selectedItem.getDescription());
        txtQtyOnHand.setText(selectedItem.getQtyOnHand());
        txtUnitPrice.setText(selectedItem.getUnitPrice());
        cmbStatus.setValue(selectedItem.getStatus());
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(new ItemRepo().autoGenarateItemCode());
    }

    public void txtItemUnitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.UNITPRICE,txtUnitPrice);
    }

    public void txtitemQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtQtyOnHand);
    }

    public void txtItemNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtName);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.UNITPRICE,txtUnitPrice))return false;
        if(!Regex.setTextColor(TextField.QTY,txtQtyOnHand))return false;
        if(!Regex.setTextColor(TextField.NAME,txtName))return false;
        return true;
    }
}



