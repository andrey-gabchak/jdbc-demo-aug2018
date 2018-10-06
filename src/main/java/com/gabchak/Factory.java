package com.gabchak;

import com.gabchak.controller.*;
import com.gabchak.dao.*;
import com.gabchak.service.UserService;
import com.gabchak.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    static Connection connection = null;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static GetAllCategoriesController getAllCategoriesController() {
        return  new GetAllCategoriesController(getCategoryDaoImpl(getConnection()));
    }

    public static CategoryDaoImpl getCategoryDaoImpl(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static PageNotFoundController getPageNotFoundController() {
        return new  PageNotFoundController();
    }

    public static UserService getUserService() {
        return new UserServiceImpl(getUserDao());
    }

    public static UserDao getUserDao() {
        return new UserDaoImpl(getConnection());
    }

    public static RegisterController getRegisterController() {
        return new RegisterController(getUserService());
    }

    public static UserServiceImpl getUserServiceImpl() {
        return new UserServiceImpl(getUserDao());
    }

    public static Controller getLoginPageController() {
        return new LoginController(getUserService());
    }

    public static Controller getGetCategoryByIdController() {
        return new GetCategoryByIdController(getCategoryDaoImpl(getConnection()));
    }

    public static Controller getProductByIdController() {
        return new GetProductControllerById(getProductDao());
    }

    private static ProductDaoImpl getProductDao() {
        return new ProductDaoImpl(connection);
    }
}
