package meeting.infrastructure.datasource.schedule;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartMinuteTypeHandler extends BaseTypeHandler<StartMinuteTypeHandler> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, StartMinuteTypeHandler parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public StartMinuteTypeHandler getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public StartMinuteTypeHandler getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public StartMinuteTypeHandler getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
