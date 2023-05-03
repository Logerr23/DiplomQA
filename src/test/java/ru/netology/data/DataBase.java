package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.models.CreditRequestEntity;
import ru.netology.data.models.OrderEntity;
import ru.netology.data.models.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataBase {
    public DataBase(){}

    @SneakyThrows
    public static Connection getConnection(){
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void clearDataBase(){
        var conn = getConnection();
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
        conn.createStatement().executeUpdate("TRUNCATE payment_entity;");
        conn.createStatement().executeUpdate("TRUNCATE credit_request_entity;");
        conn.createStatement().executeUpdate("TRUNCATE order_entity");
        conn.createStatement().executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");
    }

    @SneakyThrows
    public static OrderEntity getOrderEntity(){

        var orderSQL = "SELECT * FROM order_entity;";
        var runner = new QueryRunner();

        try (
                var conn = getConnection();
                ) {
            return runner.query(conn, orderSQL, new BeanHandler<>(OrderEntity.class));
        }
    }

    @SneakyThrows
    public static PaymentEntity getPaymentEntity(){

        var paymentSQL = "SELECT * FROM payment_entity;";
        var runner = new QueryRunner();

        try (var conn = getConnection()) {
            return runner.query(conn, paymentSQL, new BeanHandler<>(PaymentEntity.class));
        }
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequestEntity(){

        var creditSQL = "SELECT * FROM credit_request_entity;";
        var runner = new QueryRunner();

        try (
                var conn = getConnection();
        ) {
            return runner.query(conn, creditSQL, new BeanHandler<>(CreditRequestEntity.class));
        }
    }

    public static Boolean validOrderEntity (OrderEntity order){
        return order.getId() != null
                && order.getCreated() != null
                && (order.getCreditId() != null || order.getPaymentId() != null);
    }

    public static Boolean validPaymentEntity (PaymentEntity payment){
        return  payment.getId() != null
                && payment.getAmount() != 0
                && payment.getCreated() != null
                && payment.getStatus() != null
                && payment.getTransactionId() != null;
    }

    public static Boolean validCreditRequestEntity (CreditRequestEntity creditRequest){
        return  creditRequest.getId() != null
                && creditRequest.getBankId() != null
                && creditRequest.getCreated() != null
                && creditRequest.getStatus() != null;
    }

    public static Boolean validCreated(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm"))
                .equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm")));

    }


}
