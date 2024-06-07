package com.hamzaKhan.fullStackbackEnd.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper rowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }


    @Override
    public List<Customer> selectAllCustomers() {

        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        List<Customer> query = jdbcTemplate.query(sql, rowMapper);

        return query;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {

        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;

        Optional<Customer> potentialCustomer =
                jdbcTemplate.query(sql, rowMapper, id)
                        .stream()
                        .findFirst();

        return potentialCustomer;
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;

       int update = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );

        System.out.println("jdbcTemplate.update = " + update);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        if (count > 0)
            return true;

        return false;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (count > 0)
            return true;

        return false;
    }

    @Override
    public void deleteCustomer(Integer custID) {
        var sql = """
                DELETE 
                from customer
                WHERE id = ?
                """;
       int delete = jdbcTemplate.update(
               sql,
               custID
       );
        System.out.println("jdbcTemplate.update = " + delete);
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName() != null){
            var sql = "UPDATE customer SET name = ? WHERE id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    update.getName(),
                    update.getId()
            );

            System.out.println("update name res =" + result);
        }

        if (update.getAge() != null){
            var sql = "UPDATE customer SET age = ? WHERE id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    update.getAge(),
                    update.getId()
            );

            System.out.println("update age res =" + result);
        }

        if (update.getEmail() != null){
            var sql = "UPDATE customer SET email = ? WHERE id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId()
            );

            System.out.println("update email res =" + result);
        }
    }
}
