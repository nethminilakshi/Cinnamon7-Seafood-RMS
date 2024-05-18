package lk.ijse.restaurantManagement.repository;

import com.mysql.cj.xdevapi.Session;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT orderId FROM orders ORDER BY orderId desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getOrderType());
        pstm.setString(3, order.getCusId());
        pstm.setString(4, order.getDate());

        return pstm.executeUpdate() > 0;
    }


    public static Order searchById(String orderId) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE orderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, orderId);
        ResultSet resultSet = pstm.executeQuery();

        Order orders = null;

        if (resultSet.next()) {
            String orderid = resultSet.getString(1);
            String orderType = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);

            orders = new Order(orderid, orderType, cusId, date);
        }
        return orders;

    }

    public String autoGenarateOrderId() throws SQLException {
        String sql = "SELECT orderId from Orders order by orderId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        // pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        // Salary salary = null;

        if (resultSet.next()) {
            String orderId = resultSet.getString("orderId");
            String numericPart = orderId.replaceAll("\\D+", "");
            int newOrderId = Integer.parseInt(numericPart) + 1;
            return String.format("Od%03d", newOrderId);

        } else {
            return "Od001";
        }
    }

    public static void OrdersCount(BarChart<String,Number> barChartOrders) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT date AS order_date, COUNT(*) AS order_count FROM Orders GROUP BY date ORDER BY order_date";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        while (resultSet.next()) {
            String date = resultSet.getString("order_date");
            int ordersCount = resultSet.getInt("order_count");
            series.getData().add(new XYChart.Data<>(date, ordersCount));
        }

        barChartOrders.getData().add(series);


        }
    }

