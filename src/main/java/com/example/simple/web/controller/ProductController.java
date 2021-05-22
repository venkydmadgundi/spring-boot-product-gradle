package com.example.simple.web.controller;

import com.example.simple.web.model.Product;
// import com.example.simple.web.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/products")

public class ProductController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class ProductRowMapper implements RowMapper < Product > {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setStock(rs.getInt("stock"));
            return product;
        }
    }

    @GetMapping("")
    public List < Product > selectAll() {
        return jdbcTemplate.query("SELECT * FROM products", new ProductRowMapper());
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Product.class));
    }

    @PostMapping("/")
    public void save(@RequestBody Product product) {
        String sqlQuery = "INSERT products(name, description, price, stock) " +
                          "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery, product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }
    
    @PutMapping("/{id}")
    public void update(@RequestBody Product product, @PathVariable Integer id) {
        String sqlQuery = "UPDATE products SET " +
                          "name = ?, description = ?, price = ?, stock = ? " +
                          "WHERE id = ?";

        jdbcTemplate.update(sqlQuery
                      , product.getName()
                      , product.getDescription()
                      , product.getPrice()
                      , product.getStock()
                      , id );
    }
    
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return jdbcTemplate.update("DELETE FROM products WHERE id = ?", new Object[]{id} ) > 0;
    }

}
