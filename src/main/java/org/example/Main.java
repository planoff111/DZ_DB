package org.example;

import org.example.Entity.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Handler handler = new Handler();


        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/Dz_DB",
                "postgres", "123456")) {

            handler.createTables(connection);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/Dz_DB",
                "postgres", "123456")) {

            List<Customer> cast = new ArrayList<>();
            cast.add(new Customer("Ivan"));
            cast.add(new Customer("Petro"));
            cast.add(new Customer("Sasha"));
            cast.add(new Customer("Anna"));
            cast.add(new Customer("Eugene"));
            for (Customer customer : cast) {
                handler.insertInTableCustomer(connection, customer);
            }

            List<Order> orders = new ArrayList<>();
            orders.add(new Order("Order_1", 3, 1));
            orders.add(new Order("Order_2", 1, 1));
            orders.add(new Order("Order_3", 2, 2));
            orders.add(new Order("Order_4", 4, 2));
            orders.add(new Order("Order_5", 1, 3));
            orders.add(new Order("Order_6", 5, 3));
            orders.add(new Order("Order_7", 1, 4));
            orders.add(new Order("Order_8", 2, 5));
            for (Order order : orders) {
                handler.insertInTableOrder(connection, order);
            }

            List<OrderDetails> details = new ArrayList<>();
            details.add(new OrderDetails(25, 1));
            details.add(new OrderDetails(15.1, 2));
            details.add(new OrderDetails(13.5, 2));
            details.add(new OrderDetails(12, 3));
            details.add(new OrderDetails(11, 4));
            details.add(new OrderDetails(11, 5));
            details.add(new OrderDetails(22, 5));
            details.add(new OrderDetails(24.6, 6));
            details.add(new OrderDetails(17, 7));
            details.add(new OrderDetails(13, 8));

            for (OrderDetails detail : details) {
                handler.insertInTableOrderDetails(connection, detail);
            }

            List<CustomerDetails> det = new ArrayList<>();
            det.add(new CustomerDetails("Ivanovich", 1));
            det.add(new CustomerDetails("Petrovich", 2));
            det.add(new CustomerDetails("Kovalenko", 3));
            det.add(new CustomerDetails("Ibragim", 4));
            det.add(new CustomerDetails("Romaschenk", 5));
            for (CustomerDetails detail : det) {
                handler.insertInTableCustomerDetails(connection, detail);
            }
            List<Cart> carts = new ArrayList<>();
            carts.add(new Cart("Pivo", 1));
            carts.add(new Cart("Chips", 2));
            carts.add(new Cart("Soda", 2));
            carts.add(new Cart("Chocolate", 3));
            carts.add(new Cart("Juice", 4));
            carts.add(new Cart("Cookies", 5));
            carts.add(new Cart("Candy", 5));
            carts.add(new Cart("Bread", 6));
            carts.add(new Cart("Milk", 7));
            carts.add(new Cart("Water", 8));

            for (Cart cart : carts) {
                handler.insertInTableCart(connection, cart);
            }
        }catch (SQLException e) {
            System.out.println(e);
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/Dz_DB",
                "postgres", "123456")) {
            System.out.println("Отримати всі замовлення для 1 кастомера.");
            handler.takeOrderFromOneCastomer(connection, 2);
            System.out.println("Отримати всі замовлення для всіх кастомерів.");
            handler.showAllOrders(connection);
            System.out.println("Отримати всі замовлення із деталями для одного кастомера.");
            handler.orderWithDetails(connection, 2);
            System.out.println("Отримати кількість замовлень у кожного кастомера. ");
            handler.orderByCast(connection);
            System.out.println("Змінити щось у замовленні.");
            handler.updateInOrder(connection, "pipa", 3, 1);
            System.out.println("Очистити таблицю Cart.");
            handler.deleteFromCart(connection);


        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}



