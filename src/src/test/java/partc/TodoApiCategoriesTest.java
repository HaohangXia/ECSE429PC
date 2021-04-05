package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.CategoriesReadResult;
import UnitTest.model.Category;
import kong.unirest.Unirest;
import org.apache.http.HttpStatus;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoApiCategoriesTest {
    private static boolean isOnline;
    private int count;
    private static Process serverProcess;
    TodoApiCategories todoApiCategories = new TodoApiCategories();


    @Parameterized.Parameters
    public static Collection<Integer> data() {
        return Arrays.asList(1, 10, 100, 1000, 10000);
    }

    public TodoApiCategoriesTest(int count) {
        this.count = count;
    }

    @Parameterized.BeforeParam
    public static void setup() throws IOException {
        //remove all todos
        ApiTodosTest apiTodosTest = new ApiTodosTest();
        ApiResponse<CategoriesReadResult> response = apiTodosTest.httpGet("/categories", CategoriesReadResult.class)
                .assertOkAndFormed();
        CategoriesReadResult categories = response.getBodyParsed();
        for (Category cate : categories.getCategories()) {
            apiTodosTest.httpDelete("/categories/" + cate.getId(), void.class).assertStatusAndFormed(HttpStatus.SC_OK);
        }
    }

//    @Test
//    public void helpSet() throws IOException {
//        setup();
//    }

    @Test
    public void _1createCategory() throws IOException {
        this.todoApiCategories.createCategory(this.count);
        System.out.println("create Categories.count:" + this.count);
    }

    @Test
    public void _2changeAllCategories() throws IOException {
        int size = this.todoApiCategories.changeAllCategories();
        Assert.assertEquals(this.count, size);
        System.out.println("change All Categories.count:" + size);
    }

    @Test
    public void _3deleteAllCategories() throws IOException {
        int size = this.todoApiCategories.deleteAllCategories();
        Assert.assertEquals(this.count, size);
        System.out.println("delete All Categories.count:" + size);
    }
}
