package com.pamela.hh.db;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

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
    public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual() throws Exception {

        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("CUSTOMERS");

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("CUSTOMERS");

//        org.dbunit.Assertion.assertEquals(expectedTable, actualTable);

        String sqlInsert = "INSERT INTO CUSTOMERS (name, bornAT) VALUES (?, ?)";
        String sqlGet = "SELECT * FROM CUSTOMERS WHERE name = ? LIMIT 1";

        try (
                PreparedStatement insert = getConnection().getConnection().prepareStatement(sqlInsert);
                PreparedStatement get = getConnection().getConnection().prepareStatement(sqlGet);
        ) {
            insert.setString(1, "Anna");
            insert.setInt(2,2000);
            assertTrue(insert.executeUpdate() > 0);

            get.setString(1, "Anna");
            ResultSet result = get.executeQuery();
            String name = null;

            while(result.next()){
                name = result.getString("name");
            }

            assertEquals("Anna", name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws URISyntaxException {

        String clazzPackage = getClass().getPackage().getName();
        System.out.println(clazzPackage);

        String fileName = clazzPackage + "/dataset.xml";
        System.out.println(fileName);

        URL url = getClass().getClassLoader().getResource(fileName);
        System.out.println(url);
        assertNotNull(url);

        File file = new File(url.toURI());
        assertTrue(file.exists());
    }

    @Test
    public void whenGetClazzPackage_thenReturnOnlyPackageName() {

        String clazzPackage = getClass().getPackage().getName().replaceAll("\\.", "/");
        System.out.println(clazzPackage);

        String fileName = clazzPackage + "/dataset.xml";
        try(
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)
        ) {
            System.out.println("File found");
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
