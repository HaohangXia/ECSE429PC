package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.CategoriesWriteResult;
import UnitTest.model.Category;
import UnitTest.model.CategoriesReadResult;
import org.apache.http.HttpStatus;
import java.io.IOException;
import java.util.Random;


public class TodoApiCategories {
    Random random=new Random();

    ApiTodosTest apiTodosTest=new ApiTodosTest();

    static final String CONTENT_TYPE_XML = "application/xml";

    public void createCategory(int count) throws IOException {
        int currentIdx=0;
        while(currentIdx<count){
            String payload= "<category><title>"+ "title_"+this.random.nextInt()+"</title><description>"+"description_"+this.random.nextInt()+"</description></category>";
            final ApiResponse<CategoriesWriteResult> response = this.apiTodosTest.httpPost("/categories", CONTENT_TYPE_XML, payload, CategoriesWriteResult.class)
                    .assertStatusAndFormed(HttpStatus.SC_CREATED);
            currentIdx++;
        }
    }

    public int changeAllCategories() throws IOException {
        ApiResponse<CategoriesReadResult> response = this.apiTodosTest.httpGet("/categories", CategoriesReadResult.class)
                .assertOkAndFormed();
        CategoriesReadResult categories = response.getBodyParsed();
        for(Category cate : categories.getCategories()){
            String title=this.random.nextInt()+"";
            String payload= "<category><title>"+ "title_"+this.random.nextInt()+"</title><description>"+"description_"+this.random.nextInt()+"</description></category>";
            this.apiTodosTest.httpPost("/categories/"+cate.getId(),CONTENT_TYPE_XML, payload, CategoriesWriteResult.class);
        }
        return categories.getCategories().size();
    }

    public int deleteAllCategories() throws IOException {
        ApiResponse<CategoriesReadResult> response = this.apiTodosTest.httpGet("/categories", CategoriesReadResult.class)
                .assertOkAndFormed();
        CategoriesReadResult categories = response.getBodyParsed();
        for(Category cate : categories.getCategories()){
            this.apiTodosTest.httpDelete( "/categories/" + cate.getId(), void.class).assertStatusAndFormed(HttpStatus.SC_OK);
        }
        return categories.getCategories().size();
    }

}
