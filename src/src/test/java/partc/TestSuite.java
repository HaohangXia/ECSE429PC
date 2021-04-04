package partc;

import UnitTest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TodoApiConsumerTest.class,
        TodoApiCategoriesTest.class,
        //TODO 其他人的test？
})
public class TestSuite {
}  