package com.pamela.hh.user;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.InputStream;

@SpringBootTest
public class UserTest extends DataSourceBasedDBTestCase {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DataSource getDataSource() {

        String clazzPackageName = getClass().getPackage().getName();
        String schemaName = clazzPackageName + "/schema.sql";

        String strUrl = String.format(
                "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:%s'",
                schemaName);

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(strUrl);
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {

        String clazzPackageName = getClass().getPackage().getName();
        String dataset = clazzPackageName + "/dataset.xml";

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(dataset);

        return new FlatXmlDataSetBuilder()
                .build(inputStream);
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

    @Test
    public void testUserService() {
        User user = new User.UserBuilder()
                .id(1L)
                .firstName("Pamela")
                .lastName("Quintanilha")
                .email("pamela@email.com")
                .password("1234678")
                .userRole(UserRole.PATIENT)
                .build();

        boolean userExists = userRepository
                .findUserByEmail(user.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        user.setPassword(user.getPassword());
        userRepository.save(user);
        userRepository.findAll().forEach(System.out::println);
    }
}
