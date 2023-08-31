package org.example.Entity;

import java.sql.*;

public class Handler {

    public void createTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE Customers (" +
                "id Serial PRIMARY KEY ," +
                "name VARCHAR(10)" +
                ");" +
                "Create TABLE Orders(" +
                "id Serial PRIMARY KEY ," +
                "name VARCHAR(15)," +
                "qnty INT," +
                "customer_id INT," +
                "FOREIGN KEY (customer_id) REFERENCES customers(id)" +
                ");" +
                "CREATE TABLE Order_Details(" +
                "price DECIMAL(4,2)," +
                "id Serial PRIMARY KEY, " +
                "order_id INT," +
                "FOREIGN KEY (order_id) REFERENCES orders(id)" +
                ");" +
                "CREATE TABLE Customer_details(" +
                "last_name VARCHAR(10), " +
                "id SERIAL PRIMARY KEY," +
                "customer_id INT," +
                "FOREIGN KEY (customer_id) REFERENCES customers(id)" +
                ");" +
                "CREATE TABLE Cart(" +
                "id SERIAL PRIMARY KEY ," +
                "tovar_name VARCHAR(10)," +
                "position INT," +
                "FOREIGN KEY (position) REFERENCES Orders(id)" +
                ");"
        );
        statement.close();
        System.out.println("Таблиці створено");

    }

    public void insertInTableCustomer(Connection connection, Customer customer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT  INTO customers (name)" +
                "VALUES (?)");
        statement.setString(1, customer.getName());
        statement.execute();
        statement.close();

    }

    public void insertInTableOrder(Connection connection, Order order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT  INTO orders (name,qnty,customer_id)" +
                "VALUES (?,?,?)");
        statement.setString(1, order.getName());
        statement.setInt(2, order.getQnty());
        statement.setInt(3, order.getCustomerId());
        statement.execute();
        statement.close();
    }

    public void insertInTableOrderDetails(Connection connection, OrderDetails order) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT  INTO order_details (price,order_id)" +
                "VALUES (?,?)");
        statement.setDouble(1, order.getPrice());
        statement.setInt(2, order.getOrderId());
        statement.execute();
        statement.close();
    }

    public void insertInTableCustomerDetails(Connection connection, CustomerDetails customer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT  INTO customer_details " +
                "(last_name,customer_id)" +
                "VALUES (?,?)");
        statement.setString(1, customer.getLastName());
        statement.setInt(2, customer.getCustomerId());
        statement.execute();
        statement.close();
    }

    public void insertInTableCart(Connection connection, Cart cart) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT  INTO cart " +
                "(tovar_name,position)" +
                "VALUES (?,?)");
        statement.setString(1, cart.getTovarName());
        statement.setInt(2, cart.getPosition());
        statement.execute();
        statement.close();
    }

    public void takeOrderFromOneCastomer(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT c.name,o.name AS order_name,o.qnty FROM customers c " +
                "INNER JOIN orders o ON c.id = o.customer_id where o.id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Імя клієнта " + resultSet.getString("name") + " Імя ордеру "
                    + resultSet.getString("order_name") + " кількість "
                    + resultSet.getInt("qnty"));

        }
        resultSet.close();
        statement.close();

    }

    public void orderWithDetails(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT o.name,o.qnty, or_d.price FROM orders o" +
                " JOIN order_details or_d ON o.id = or_d.order_id where o.id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Імя клієнта " + resultSet.getString("name") + " ціна "
                    + resultSet.getInt("price") + " кількість "
                    + resultSet.getInt("qnty"));


        }
        resultSet.close();
        statement.close();
    }

    public void showAllOrders(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT c.name,o.name AS order_name,o.qnty FROM orders o " +
                "INNER JOIN customers c ON o.customer_id = c.id");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Імя клієнта " + resultSet.getString("name") + " назва ордеру "
                    + resultSet.getString("order_name") + " кількість "
                    + resultSet.getInt("qnty")
            );

        }
        resultSet.close();
        statement.close();

    }

    public void orderByCast(Connection connection) throws SQLException {
        ;
        PreparedStatement statement = connection.prepareStatement("SELECT c.name, COUNT(o.id) AS order_count " +
                "FROM customers c " +
                "JOIN orders o ON c.id = o.customer_id " +
                "GROUP BY c.name ");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Імя клієнта " + resultSet.getString("name") + " кількість замовлень "
                    + resultSet.getInt("order_count")
            );

        }
        resultSet.close();
        statement.close();

    }

    public void updateInOrder(Connection connection, String name, int qnty, int id) throws SQLException {
        ;
        PreparedStatement statement = connection.prepareStatement("UPDATE orders " +
                "SET name = ?, qnty = ? " +
                "WHERE id = ?");
        statement.setString(1, name);
        statement.setInt(2, qnty);
        statement.setInt(3, id);

        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected + " row(s) updated.");
        statement.close();
    }

    public void deleteFromCart(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cart");
        statement.executeUpdate();
        System.out.println("Cart has been cleaned");
        statement.close();

    }

}
