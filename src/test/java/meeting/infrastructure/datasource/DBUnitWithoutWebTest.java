package meeting.infrastructure.datasource;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;

@MybatisTest
@ComponentScan("meeting.infrastructure.datasource")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DBUnitWithoutWebTest extends DBUnitBaseTest {
}
