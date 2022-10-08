package meeting.presentation.meeting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditFormPageTest {

    @Test
    void _URLのパターンが一致する() throws Exception {

        assertTrue("/meeting/mt000000000000000000000001/edit".matches(EditFormPage.URL_RELATIVE_PATH_TEMPLATE));
    }
}
