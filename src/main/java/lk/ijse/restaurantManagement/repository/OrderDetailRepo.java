package lk.ijse.restaurantManagement.repository;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderDetail od) throws SQLException {
        String sql = "INSERT INTO Order_details VALUES(?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItemId());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }


    }

