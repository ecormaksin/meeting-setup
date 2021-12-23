package meeting.domain.model.attendance.convenience;

/**
 * 都合種別
 */
public enum ConvenienceCategory {
    マル("○", "convenience-category-maru"),
    サンカク("△", "convenience-category-sankaku"),
    バツ("×", "convenience-category-shikaku");

    String symbol;
    String displayStyle;

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

    ConvenienceCategory(String symbol, String displayStyle) {
        this.symbol = symbol;
        this.displayStyle = displayStyle;
    }

    public String symbol() {
        return symbol;
    }

    public String displayStyle() {
        return displayStyle;
    }
}
