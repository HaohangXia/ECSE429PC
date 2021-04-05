package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.CategoriesReadResult;
import UnitTest.model.Category;
import UnitTest.model.Project;
import UnitTest.model.ProjectsReadResult;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodoApiProjectsTest {
    private int count;

    TodoApiProjects todoApiProjects=new TodoApiProjects();


    @Parameterized.Parameters
    public static Collection<Integer> data() {
        return Arrays.asList(1,10,100,1000,10000);
    }

    public TodoApiProjectsTest(int count) {
        this.count= count;
    }

    @Parameterized.BeforeParam
    public static void setup() throws IOException {
        //remove all todos
        ApiTodosTest apiTodosTest= new ApiTodosTest();
        ApiResponse<ProjectsReadResult> response = apiTodosTest.httpGet("/projects", ProjectsReadResult.class)
                .assertOkAndFormed();
        ProjectsReadResult projects = response.getBodyParsed();
        for(Project proj : projects.getProjects()){
            apiTodosTest.httpDelete( "/projects/" + proj.getId(), void.class).assertStatusAndFormed(HttpStatus.SC_OK);
        }
    }

    @Parameterized.AfterParam
    public static void tearDown() {

    }


    @Test
    public void _1createProjs() throws IOException {
        this.todoApiProjects.createProject(this.count);
        System.out.println("create Projects.count:"+this.count);
    }

    @Test
    public void _2changeAllProjs() throws IOException {
        int size=this.todoApiProjects.changeAllProjects();
        Assert.assertEquals(this.count,size);
        System.out.println("change All Projects.count:"+size);
    }



    @Test
    public void _3deleteAllProjs() throws IOException {
        int size= this.todoApiProjects.deleteAllProjects();
        Assert.assertEquals(this.count,size);
        System.out.println("delete All Projects.count:"+size);
    }
}
