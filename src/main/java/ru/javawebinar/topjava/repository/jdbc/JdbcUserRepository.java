package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    //    private static final ResultSetExtractor<List<User>> MAPPER_WITH_ROLES = JdbcTemplateMapperFactory
//            .newInstance()
//            .addKeys("id")
//            .newResultSetExtractor(User.class);
    private static final ResultSetExtractor<List<User>> MAPPER_WITH_ROLES = rs -> {
            Map<Integer, User> result = new HashMap<>();
            while (rs.next()) {
                Integer userId = rs.getInt("id");
                User user = result.getOrDefault(userId, new User());
                if (user.isNew()) {
                    user.setId(userId);
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                    user.setEnabled(rs.getBoolean("enabled"));
                    result.put(userId, user);
                }
                Role role = Role.valueOf(rs.getString("role"));
                if (user.getRoles() == null) {
                    user.setRoles(Set.of(role));
                } else {
                    user.getRoles().add(role);
                }
            }
            return new ArrayList<>(result.values());
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.id());
        batchInsertRoles(new ArrayList<>(user.getRoles()), user.id());
        return user;
    }


    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                SELECT u.id, u.name, u.email, u.password, u.calories_per_day, u.enabled, r.role
                FROM users u JOIN user_roles r ON r.user_id=u.id WHERE u.id=? ORDER BY u.id
                """, MAPPER_WITH_ROLES, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("""
                SELECT u.id, u.name, u.email, u.password, u.calories_per_day, u.enabled, r.role 
                FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE u.email=? ORDER BY u.id
                """, MAPPER_WITH_ROLES, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }

    private int[] batchInsertRoles(List<Role> roles, int user_id) {
        return this.jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user_id);
                        ps.setString(2, roles.get(i).toString());
                    }

                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private int[] batchUpdateRoles(List<Role> roles, int user_id) {
        return this.jdbcTemplate.batchUpdate(
                "UPDATE user_roles SET role = ? where user_id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, roles.get(i).toString());
                        ps.setInt(2, user_id);
                    }

                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }
}
