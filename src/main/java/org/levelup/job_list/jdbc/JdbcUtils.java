package org.levelup.job_list.jdbc;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

    public static Connection getConnection() throws SQLException {
       Connection connection = DriverManager.getConnection(
               "jdbc:postgresql://localhost:5432/jobs",
               "postgres",
               "root");
return connection;
    }
}
