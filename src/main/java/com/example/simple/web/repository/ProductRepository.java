package com.example.simple.web.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.example.simple.web.model.Product;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(int id);

    void save(Product product);

    void update(Product product,int id);

    boolean delete(int id);

    

}
