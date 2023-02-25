package meeting.infrastructure.datasource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Types;

@Configuration
public class DBUnitConfig {

    @Bean
    public IDatabaseConnection dbUnitDatabaseConnection(DataSource dataSource) throws SQLException {
        IDatabaseConnection connection = new DatabaseDataSourceConnection(new TransactionAwareDataSourceProxy(dataSource));
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory() {
            @Override
            public boolean isEnumType(String sqlTypeName) {
                return sqlTypeName.equals("\"出欠\".\"都合種別\"");
            }

            @Override
            public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
                if (isEnumType(sqlTypeName)) {
                    sqlType = Types.OTHER;
                }
                return super.createDataType(sqlType, sqlTypeName);
            }
        });
        return connection;
    }
}
