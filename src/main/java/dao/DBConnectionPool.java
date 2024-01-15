package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import configuration.Configuration;
import dao.impl.DaoConstantes;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Singleton
public class DBConnectionPool {

    private final Configuration config;
    private final DataSource hikariDataSource;


    @Inject
    public DBConnectionPool(Configuration config) {
        this.config = config;
        hikariDataSource = getHikariPool();
    }

    private DataSource getHikariPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty(DaoConstantes.URL_DB));
        hikariConfig.setUsername(config.getProperty(DaoConstantes.USER_NAME));
        hikariConfig.setPassword(config.getProperty(DaoConstantes.PASSWORD));
        hikariConfig.setDriverClassName(config.getProperty(DaoConstantes.DRIVER));
        hikariConfig.setMaximumPoolSize(4);
        hikariConfig.addDataSourceProperty(DaoConstantes.CACHE_PREP_STMTS, true);
        hikariConfig.addDataSourceProperty(DaoConstantes.PREP_STMT_CACHE_SIZE, 250);
        hikariConfig.addDataSourceProperty(DaoConstantes.PREP_STMT_CACHE_SQL_LIMIT, 2048);

        return new HikariDataSource(hikariConfig);
    }


    public Connection getConnection() {
        Connection con = null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return con;
    }



    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDataSource).close();
    }

}
