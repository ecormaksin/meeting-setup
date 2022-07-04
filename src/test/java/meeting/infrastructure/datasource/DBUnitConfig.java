package meeting.infrastructure.datasource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DBUnitConfig {

    @Bean
    public IDatabaseConnection dbUnitDatabaseConnection(DataSource dataSource) throws SQLException {
        IDatabaseConnection connection = new DatabaseDataSourceConnection(new TransactionAwareDataSourceProxy(dataSource));
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        return connection;
    }
}
