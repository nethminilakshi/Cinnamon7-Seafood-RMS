package lk.ijse.restaurantManagement.repository;

import javafx.scene.control.Button;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.Order;
import lk.ijse.restaurantManagement.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2,payment.getCusId());
        pstm.setObject(3, payment.getOrderId());
        pstm.setObject(4, payment.getPayMethod());
        pstm.setObject(5, payment.getAmount());


        return pstm.executeUpdate()>0;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM Payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String paymentId = resultSet.getString(1);
            String cusId = resultSet.getString(2);
            String orderId = resultSet.getString(3);
            String payMethod = resultSet.getString(4);
            double amount = Double.parseDouble(resultSet.getString(5));
           // String payButton =resultSet.getString(5);


            Payment payment = new Payment(paymentId,cusId, orderId, payMethod, amount);
            paymentList.add(payment);
        }
        return paymentList;
    }



    public String autoGenaratePaymentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT paymentId from Payment order by paymentId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);


        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String paymentId = resultSet.getString("paymentId");
            String numericPart = paymentId.replaceAll("\\D+","");
            int newPaymentId = Integer.parseInt(numericPart) + 1;
            return String.format("Pay%03d",newPaymentId);

        }else {
            return "pay001";

        }
    }
}

