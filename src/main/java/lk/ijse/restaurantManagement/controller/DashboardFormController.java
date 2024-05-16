package lk.ijse.restaurantManagement.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.repository.OrderDetailRepo;
import lk.ijse.restaurantManagement.repository.OrderRepo;


import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {
    @FXML
    private AnchorPane bodyPane;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblItemCount;
    private int customerCount;
    private int itemCount;
    private int employeeCount;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    private volatile boolean stop = false;


    @FXML
    private BarChart<String, Number> barChartOrders;

    public void initialize() throws SQLException {

        timeNow();
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
        String formattedDate = date.format(dateformatter);
        lblDate.setText(formattedDate);

        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCustomerCount(customerCount);

        try {
            itemCount = getItemCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setItemCount(itemCount);

        try {
            employeeCount = getEmployeeCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setEmployeeCount(employeeCount);

       // OrderRepo.OrdersCount(barChartOrders);
    }


    public void timeNow(){
        Thread thread = new Thread(()->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            while (!stop){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(()->{
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    private void setItemCount(int itemCount) {
        lblItemCount.setText(String.valueOf(itemCount));
    }

    private int getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS item_count FROM Item";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }

    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int customerCount = 0;
        if(resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM Employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int employeeCount = 0;
        if(resultSet.next()) {
            employeeCount = resultSet.getInt("employee_count");
        }
        return employeeCount;
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }


}
