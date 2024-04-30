package lk.ijse.restaurantManagement.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.tm.ItemTm;
import lk.ijse.restaurantManagement.repository.CustomerRepo;
import lk.ijse.restaurantManagement.repository.ItemRepo;

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
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;
    private List<Item> itemList=new ArrayList<>();
    private Alert alert;
    public void initialize() {

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
        String id = txtId.getText();

        String sql = "DELETE FROM Item WHERE id = ?";

        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setObject(1, id);

            if(pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String unitPrice = txtUnitPrice.getText();
        String status=String.valueOf(cmbStatus.getValue());

        String sql = "INSERT INTO Item VALUES(?, ?, ?, ?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setObject(1, id);
            pstm.setObject(2, name);
            pstm.setObject(3, qtyOnHand);
            pstm.setObject(4, unitPrice);
            pstm.setObject(5,status);

            boolean isSaved = pstm.executeUpdate() > 0;
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        //cmbType.getTypeSelector();
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
        cmbStatus.setValue("");
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String qtyOnHand = txtQtyOnHand.getText();
        String unitPrice = txtUnitPrice.getText();
        String status=String.valueOf(cmbStatus.getValue());

        Item item = new Item(id,name,qtyOnHand,unitPrice,status);
        try {
            boolean isUpdated = ItemRepo.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    public void btnBackOnAction(ActionEvent actionEvent)throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Item item = ItemRepo.searchById(id);

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
}



