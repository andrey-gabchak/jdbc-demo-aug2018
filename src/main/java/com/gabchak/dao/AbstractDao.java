package com.gabchak.dao;

import com.gabchak.model.QueryBulder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T, ID> implements GenericDao<T, ID> {

    private final Connection connection;
    private QueryBulder queryBulder = new QueryBulder();

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T findById(ID id) {
        String query = queryBulder.getSelectByIdQuery(connection.getClass());
        PreparedStatement preparedStatement;
        T result = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next() ? getObjectFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void update(T t) {
        String query = queryBulder.getUpdateQuery(connection.getClass());
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement = prepareStatementForUpdate(preparedStatement, t);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(ID id) {
        String query = queryBulder.getDeleteByIdQuery(connection.getClass());
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(T t) {
        String query = queryBulder.getInsertQuery(connection.getClass());
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement = prepareStatementForInsert(preparedStatement, t);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<T> findAll() {
        String query = queryBulder.getSelectAllQuery(connection.getClass());
        Statement statement;
        ResultSet resultSet;
        List<T> resultList = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                resultList.add(getObjectFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    T getObjectByParam(String query, Object param) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        T resultObject = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, param);
            resultSet = preparedStatement.executeQuery();
            resultObject = resultSet.next() ? getObjectFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultObject;
    }

    protected abstract T getObjectFromResultSet(ResultSet resultSet);

    protected abstract PreparedStatement prepareStatementForUpdate(PreparedStatement preparedStatement, T t);

    protected abstract PreparedStatement prepareStatementForInsert(PreparedStatement preparedStatement, T t);
}

