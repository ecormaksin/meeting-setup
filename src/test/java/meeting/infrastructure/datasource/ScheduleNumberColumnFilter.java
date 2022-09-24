package meeting.infrastructure.datasource;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.filter.IColumnFilter;

public class ScheduleNumberColumnFilter implements IColumnFilter {

    @Override
    public boolean accept(String tableName, Column column) {
        String columnName = column.getColumnName();
        return !columnName.equals("日程番号");
    }
}
