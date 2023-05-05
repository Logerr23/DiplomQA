package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ru.netology.data.models.CreditRequestEntity;
import ru.netology.data.models.OrderEntity;
import ru.netology.data.models.PaymentEntity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseHelper {
    public DataBaseHelper(){}
    private final static String url = System.getProperty("db.url");
    private final static String username = System.getProperty("username");
    private final static String password = System.getProperty("password");

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
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

    @SneakyThrows
    public static long getCountOrder(){

        var countSQL = "SELECT count(*) FROM order_entity;";
        var runner = new QueryRunner();

        try (
                var conn = getConnection();
        ) {
            return runner.query(conn, countSQL, new ScalarHandler<>());
        }
    }

    public static Boolean  DataBaseIsEmpty(){
        return DataBaseHelper.getCountOrder() == 0;
    }
















}
