package com.example.simple.web.repository;

import com.example.simple.web.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class ProductRowMapper implements RowMapper < Product > {
        // @Override
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

    public List<Product> getProducts() {
    	return jdbcTemplate.query("SELECT * FROM products", new ProductRowMapper());
    }

    // public Product findById(@PathVariable Integer id) {
    //     return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Product.class));
    // }


}
