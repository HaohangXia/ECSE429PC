package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.Todo;
import UnitTest.model.TodoReadResult;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoApiConsumerTest {
    private int count;

    TodoApiConsumer todoApiConsumer=new TodoApiConsumer();


    @Parameterized.Parameters
    public static Collection<Integer> data() {
        return Arrays.asList(1,10,100,1000,10000);
    }

    public TodoApiConsumerTest(int count) {
        this.count= count;
    }

    @Parameterized.BeforeParam
    public static void setup() throws IOException {
        //remove all todos
        ApiTodosTest apiTodosTest= new ApiTodosTest();
        ApiResponse<TodoReadResult> response = apiTodosTest.httpGet("/todos", TodoReadResult.class)
                .assertOkAndFormed();
        TodoReadResult todos = response.getBodyParsed();
        for(Todo todo : todos.getTodos()){
            apiTodosTest.deleteTodo(todo.getId());
        }
    }

    @Parameterized.AfterParam
    public static void tearDown() {

    }


    @Test
    public void _1createTodo() throws IOException {
        this.todoApiConsumer.createTodo(this.count);
        System.out.println("create Todoes.count:"+this.count);
    }

    @Test
    public void _2changeAllTodoes() throws IOException {
        int size=this.todoApiConsumer.changeAllTodoes();
        Assert.assertEquals(this.count,size);
        System.out.println("change All Todoes.count:"+size);
    }



    @Test
    public void _3deleteAllTodoes() throws IOException {
       int size= this.todoApiConsumer.deleteAllTodoes();
        Assert.assertEquals(this.count,size);
        System.out.println("delete All Todoes.count:"+size);
    }
}