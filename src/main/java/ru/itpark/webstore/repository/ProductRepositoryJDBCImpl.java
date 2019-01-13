package ru.itpark.webstore.repository;

import org.springframework.stereotype.Repository;
import ru.itpark.webstore.domain.Product;
import ru.itpark.webstore.exception.DbException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryJDBCImpl implements ProductRepository {
    private final DataSource dataSource;

    public ProductRepositoryJDBCImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            List<Product> products = new ArrayList<>();

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id, name, price FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                products.add(new Product(id, name, price));
            }
            resultSet.close();
            statement.close();
            connection.close();

            return products;
        } catch (SQLException e) {
            // rethrow exception:
            // выкидываем non-checked exception + заворачивает старое
            throw new DbException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<Product> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Product item) {

    }

    @Override
    public void removeById(int id) {

    }
}
