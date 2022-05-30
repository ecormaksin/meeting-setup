package meeting.domain.model.attendance.convenience;

import java.util.ArrayList;
import java.util.List;

/**
 * 都合種別
 */
public enum ConvenienceCategory {
    マル("○"),
    サンカク("△"),
    バツ("×"),
    未回答("－");

    String symbol;

    public static ConvenienceCategory fromSymbol(String symbol) {
        for (ConvenienceCategory convenienceCategory : ConvenienceCategory.values()) {
            if (convenienceCategory.symbol().equals(symbol)) {
                return convenienceCategory;
            }
        }
        throw new IllegalArgumentException(
                String.format("指定された記号に該当する都合種別はありません。記号を確認してください。（%s）", symbol)
        );
    }

    public static List<ConvenienceCategory> valuesExcludeUnreplied() {
        List<ConvenienceCategory> values = new ArrayList<>();
        for (ConvenienceCategory convenienceCategory : ConvenienceCategory.values()) {
            if (convenienceCategory == ConvenienceCategory.未回答) {
                continue;
            }
            values.add(convenienceCategory);
        }
        return values;
    }

    ConvenienceCategory(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
