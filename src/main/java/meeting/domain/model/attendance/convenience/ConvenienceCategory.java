package meeting.domain.model.attendance.convenience;

/**
 * 都合種別
 */
public enum ConvenienceCategory {
    マル("○"),
    サンカク("△"),
    バツ("×");

    String symbol;

    public static ConvenienceCategory of(String symbol) {
        for (ConvenienceCategory convenienceCategory : ConvenienceCategory.values()) {
            if (convenienceCategory.symbol().equals(symbol)) {
                return convenienceCategory;
            }
        }
        throw new IllegalArgumentException(
                String.format("指定された記号に該当する都合種別はありません。記号を確認してください。（%s）", symbol)
        );
    }

    ConvenienceCategory(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
