package meeting.infrastructure.datasource.schedule.construct;

import meeting.domain.model.schedule.construct.StartMinute;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
public class StartMinuteTypeHandler extends BaseTypeHandler<StartMinute> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, StartMinute parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public StartMinute getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return StartMinute.of(rs.getInt(columnName));
    }

    @Override
    public StartMinute getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return StartMinute.of(rs.getInt(columnIndex));
    }

    @Override
    public StartMinute getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return StartMinute.of(cs.getInt(columnIndex));
    }
}
