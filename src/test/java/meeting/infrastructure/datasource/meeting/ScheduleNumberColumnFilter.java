package meeting.infrastructure.datasource.meeting;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.filter.IColumnFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ScheduleNumberColumnFilter implements IColumnFilter {

    private static final Set<String> EXCLUDE_COLUMNS = new HashSet<>(Arrays.asList("日程番号", "都合番号"));

    @Override
    public boolean accept(String tableName, Column column) {
        final String columnName = column.getColumnName();
        return !EXCLUDE_COLUMNS.contains(columnName);
    }
}
