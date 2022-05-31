package meeting.infrastructure.datasource.attendance;

import meeting.domain.model.attendance.convenience.ConvenienceCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class ConvenienceCategoryTypeHandler extends BaseTypeHandler<ConvenienceCategory> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ConvenienceCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.symbol());
    }

    private ConvenienceCategory convenienceCategory(final String columnValue) {
        return Objects.isNull(columnValue) ?
                ConvenienceCategory.未回答
                :
                ConvenienceCategory.fromSymbol(columnValue);
    }

    @Override
    public ConvenienceCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convenienceCategory(rs.getString(columnName));
    }

    @Override
    public ConvenienceCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convenienceCategory(rs.getString(columnIndex));
    }

    @Override
    public ConvenienceCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convenienceCategory(cs.getString(columnIndex));
    }
}
