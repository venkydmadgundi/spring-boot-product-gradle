package com.example.simple.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.example.simple.web.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ProductRepositoryImpl implements ProductRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@Override
	public List<Product> findAll() {
		return jdbcTemplate.query("SELECT * FROM products", new ProductRowMapper());
	}

	@Override
	public Product findById(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Product.class));
	}

	@Override
	public void save(Product product) {
		String sqlQuery = "INSERT products(name, description, price, stock) " + "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sqlQuery, product.getName(), product.getDescription(), product.getPrice(),
				product.getStock());
	}

	@Override
	public void update(Product product,int id) {
		String sqlQuery = "UPDATE products SET name = ?, description = ?, price = ?, stock = ? " + "WHERE id = ?";
		jdbcTemplate.update(sqlQuery, product.getName(), product.getDescription(), product.getPrice(),
				product.getStock(), id);		
	}

	@Override
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM products WHERE id = ?", new Object[] { id }) > 0;
	}

}
