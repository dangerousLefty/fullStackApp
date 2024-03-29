package com.hamzakhan.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age 
                FROM customer;
                """;

        List<Customer> customerList = jdbcTemplate.query(sql, customerRowMapper);
        return customerList;
    }

    @Override
    public Optional<Customer> getCustomerByID(Integer cID) {
        
        var sql = """
                SELECT id, name, email, age 
                FROM customer 
                WHERE id = ?
                """;
        
          return jdbcTemplate.query(sql, customerRowMapper, cID)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        // TODO Auto-generated method stub
        var query = """
                INSERT INTO customer (name, email, age) 
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(
            query, 
            new Object[]{
                customer.getName(),
                customer.getEmail(), 
                customer.getAge()
            }
        );

        System.out.println("jdbcTemplate.update =" + result);
    }

    @Override
    public void deleteCustomerById(Integer cID) {
        var sql = """
                DELETE 
                FROM customer 
                WHERE id = ?
                """;
        
        int result = jdbcTemplate.update(sql, cID);
        System.out.println("deleteByCustomerID res = " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        if (count != 0)
            return true;
        
        else
            return false;
    }

    @Override
    public boolean existsCustomerById(Integer cID) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cID);
        
        if (count != 0)
            return true;
        
        else
            return false;
        
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId()
            );
            System.out.println("update customer name result = " + result);
        }
        if (update.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getAge(),
                    update.getId()
            );
            System.out.println("update customer age result = " + result);
        }
        if (update.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId());
            System.out.println("update customer email result = " + result);
        }
    }
    
}
