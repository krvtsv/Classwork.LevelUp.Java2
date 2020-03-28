package org.levelup.job_list.jdbc;

import org.levelup.job_list.domain.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcJobListService {

    public Position createPosition(String name) throws SQLException {

        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into positions (name) values (?)");
        statement.setString(1, name);
        int rowChanged = statement.executeUpdate();//insert/update/delete - влияет на строки

        System.out.println("Количество добавленных строк " + rowChanged);
        Statement selectStatement = connection.createStatement();
        ResultSet resultset = selectStatement.executeQuery("select * from positions where name ='" + name + "'");
        resultset.next();
        int id = resultset.getInt(1);
        String positionName = resultset.getString("name");
        System.out.println("Должность: ID = " + id + " name = " + positionName);
        return new Position(id, positionName);
    }

    public void deletePosition(String name) throws SQLException {

        try (Connection connection = JdbcUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from positions where name = ?");
            statement.setString(1, name);
            int rowDeleted = statement.executeUpdate();
            System.out.println("Удалено позиций " + rowDeleted);
        }
    }

    public Collection<Position> findAll() throws SQLException {
        try (Connection connection = JdbcUtils.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from positions");
            return extractPositions(resultSet);
        }

    }

    private Collection<Position> extractPositions(ResultSet resultSet) throws SQLException {
        Collection<Position> positions = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            positions.add(new Position(id, name));

        }
        return positions;
    }

    public Collection<Position> findPositionWithNameLike(String name) throws SQLException {
        try (Connection connection = JdbcUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from positions where name like ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return extractPositions(resultSet);
        }

    }
}
