package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.Todo;
import UnitTest.model.TodoReadResult;
import UnitTest.model.TodoWriteResult;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.Random;

public class TodoApiConsumer {
    Random random=new Random();

    ApiTodosTest apiTodosTest=new ApiTodosTest();

    static final String CONTENT_TYPE_XML = "application/xml";

    public void createTodo(int count) throws IOException {

        String title=this.random.nextInt()+"";
        int currentIdx=0;
        while(currentIdx<count){
            String payload= "<todo><title>"+ title+"</title><doneStatus>"+random.nextBoolean()+"</doneStatus></todo>";
            final ApiResponse<TodoWriteResult> response = this.apiTodosTest.httpPost("/todos", CONTENT_TYPE_XML, payload, TodoWriteResult.class)
                    .assertStatusAndFormed(HttpStatus.SC_CREATED);
            currentIdx++;
        }
    }

    public int changeAllTodoes() throws IOException {
        ApiResponse<TodoReadResult> response = this.apiTodosTest.httpGet("/todos", TodoReadResult.class)
                .assertOkAndFormed();
        TodoReadResult todos = response.getBodyParsed();
        for(Todo todo : todos.getTodos()){
            String title=this.random.nextInt()+"";
            String payload= "<todo><title>"+ title+"</title><doneStatus>"+random.nextBoolean()+"</doneStatus></todo>";
            this.apiTodosTest.httpPost("/todo/"+todo.getId(),CONTENT_TYPE_XML, payload, TodoWriteResult.class);
        }
        return todos.getTodos().size();
    }

    public int deleteAllTodoes() throws IOException {

        ApiResponse<TodoReadResult> response = this.apiTodosTest.httpGet("/todos", TodoReadResult.class)
                .assertOkAndFormed();
        TodoReadResult todos = response.getBodyParsed();
        for(Todo todo : todos.getTodos()){
            this.apiTodosTest.deleteTodo(todo.getId());
        }
        return todos.getTodos().size();
    }


}
