package meeting.infrastructure.datasource.schedule.construct;

import meeting.domain.model.schedule.construct.Duration;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
public class DurationTypeHandler extends BaseTypeHandler<Duration> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Duration parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public Duration getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Duration.fromValue(rs.getInt(columnName));
    }

    @Override
    public Duration getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Duration.fromValue(rs.getInt(columnIndex));
    }

    @Override
    public Duration getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Duration.fromValue(cs.getInt(columnIndex));
    }
}
