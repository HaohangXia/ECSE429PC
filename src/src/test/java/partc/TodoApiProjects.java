package partc;

import UnitTest.ApiResponse;
import UnitTest.ApiTodosTest;
import UnitTest.model.*;
import org.apache.http.HttpStatus;
import java.io.IOException;
import java.util.Random;


public class TodoApiProjects {
    Random random=new Random();

    ApiTodosTest apiTodosTest=new ApiTodosTest();

    static final String CONTENT_TYPE_XML = "application/xml";

    public void createProject(int count) throws IOException {
        int currentIdx=0;
        while(currentIdx<count){
            String payload= "<project><title>"+ "title_"+this.random.nextInt()+"</title><description>"+"description_"+this.random.nextInt()+"</description></project>";
            final ApiResponse<ProjectsWriteResult> response = this.apiTodosTest.httpPost("/projects", CONTENT_TYPE_XML, payload, ProjectsWriteResult.class)
                    .assertStatusAndFormed(HttpStatus.SC_CREATED);
            currentIdx++;
        }
    }

    public int changeAllProjects() throws IOException {
        ApiResponse<ProjectsReadResult> response = this.apiTodosTest.httpGet("/projects", ProjectsReadResult.class)
                .assertOkAndFormed();
        ProjectsReadResult projects = response.getBodyParsed();
        for(Project proj : projects.getProjects()){
            String title=this.random.nextInt()+"";
            String payload= "<project><title>"+ "title_"+this.random.nextInt()+"</title><description>"+"description_"+this.random.nextInt()+"</description></project>";
            this.apiTodosTest.httpPost("/projects/"+proj.getId(),CONTENT_TYPE_XML, payload, ProjectsWriteResult.class);
        }
        return projects.getProjects().size();
    }

    public int deleteAllProjects() throws IOException {
        ApiResponse<ProjectsReadResult> response = this.apiTodosTest.httpGet("/projects", ProjectsReadResult.class)
                .assertOkAndFormed();
        ProjectsReadResult projects = response.getBodyParsed();
        for(Project proj : projects.getProjects()){
            this.apiTodosTest.httpDelete( "/projects/" + proj.getId(), void.class).assertStatusAndFormed(HttpStatus.SC_OK);
        }
        return projects.getProjects().size();
    }

}
