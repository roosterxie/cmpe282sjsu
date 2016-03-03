package cmpe282.hw1;

import cmpe282.hw1.model.Employee;
import cmpe282.hw1.model.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.WriteResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class hw1Controller {
    @Autowired
    @Qualifier("mongoTemplateID")
    private MongoTemplate mongoTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    //GET project
    @RequestMapping(value = "/project/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getProject(@PathVariable("id") int cur_i, HttpServletResponse response) {
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Project project = mongoOps.findOne(
                new Query(Criteria.where("id").is(cur_i)),
                Project.class, "project");
        if(project==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }else {
            String projectJson = null;
            try {
                projectJson = mapper.writeValueAsString(project);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return projectJson;
        }
    }

    //GET employee
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getEmployee(@PathVariable("id") int cur_i,HttpServletResponse response) {
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Employee employee = mongoOps.findOne(
                new Query(Criteria.where("id").is(cur_i)),
                Employee.class, "employee");
        if(employee==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }else{
            String EmployeeJson = null;
            try{
                EmployeeJson = mapper.writeValueAsString(employee);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return EmployeeJson;
        }
    }


    //POST Employee
    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    @ResponseBody
    public String postEmployee(@RequestBody String newData,HttpServletResponse response) {
        JSONObject receive = new JSONObject(newData);
        Employee newEmployee = new Employee();
        newEmployee.setId(Integer.parseInt(receive.getString("id")));
        newEmployee.setlastName(receive.getString("lastName"));
        newEmployee.setfirstName(receive.getString("firstName"));
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Employee oldEmployee = mongoOps.findOne(
                new Query(Criteria.where("id").is(Integer.parseInt(receive.getString("id")))),
                Employee.class, "employee");
        if(oldEmployee != null){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }else {
            mongoOps.insert(newEmployee, "employee");
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        return null;
    }

    //POST Project
    @RequestMapping(value = "/project",method = RequestMethod.POST)
    @ResponseBody
    public String postProject(@RequestBody String newData,HttpServletResponse response) {
        JSONObject receive = new JSONObject(newData);
        Project newProject = new Project();
        newProject.setId(Integer.parseInt(receive.getString("id")));
        newProject.setname(receive.getString("name"));
        newProject.setbudget(Float.parseFloat(receive.getString("budget")));
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Project oldProject = mongoOps.findOne(
                new Query(Criteria.where("id").is(Integer.parseInt(receive.getString("id")))),
                Project.class, "project");
        if(oldProject != null){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }else {
            mongoOps.insert(newProject, "project");
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        return null;
    }


    //PUT project
    @RequestMapping(value = "/project/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public String postProject(@RequestBody String newdata,@PathVariable("id") int cur_i,HttpServletResponse response) {
        JSONObject receive = new JSONObject(newdata);
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Project newProject = mongoOps.findOne(
                new Query(Criteria.where("id").is(cur_i)),
                Project.class, "project");
        if(newProject==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else {
            if(receive.has("name"))
                newProject.setname(receive.getString("name"));
            if(receive.has("budget"))
                newProject.setbudget(Float.parseFloat(receive.getString("budget")));
            mongoOps.save(newProject, "project");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return null;
    }

    //PUT employee
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public String putEmployee(@RequestBody String newdata,@PathVariable("id") int cur_i,HttpServletResponse response) {
        JSONObject receive = new JSONObject(newdata);
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        Employee newEmployee = mongoOps.findOne(
                new Query(Criteria.where("id").is(cur_i)),
                Employee.class, "employee");
        if(newEmployee==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else {
            if(receive.has("firstName"))
                newEmployee.setfirstName(receive.getString("firstName"));
            if(receive.has("lastName"))
                newEmployee.setlastName(receive.getString("lastName"));
            mongoOps.save(newEmployee, "employee");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return null;
    }


    //DELETE employee
    @RequestMapping(value = "/employee/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteEmployee(@PathVariable("id") int cur_i,HttpServletResponse response) {
        Query query = new Query(Criteria.where("id").is(cur_i));
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        WriteResult result = mongoOps.remove(query, Employee.class, "employee");
        if(result.getN()==0)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else
            response.setStatus(HttpServletResponse.SC_OK);
        return null;
    }

    //DELETE project
    @RequestMapping(value = "/project/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProject(@PathVariable("id") int cur_i,HttpServletResponse response) {
        Query query = new Query(Criteria.where("id").is(cur_i));
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        WriteResult result = mongoOps.remove(query, Project.class, "project");
        if(result.getN()==0)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else
            response.setStatus(HttpServletResponse.SC_OK);
        return null;
    }


    //GET all projects
    @RequestMapping(value = "/project",method = RequestMethod.GET)
    @ResponseBody
    public String getProjectList(HttpServletResponse response) {
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        List<Project> projectList = mongoOps.find(
                new Query(),Project.class, "project");
        if(projectList.size()==0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }else {
            String projectJson = null;
            try {
                projectJson = mapper.writeValueAsString(projectList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return "{\"projectList\":"+projectJson+"}";
        }
    }

    //GET all employees

    @RequestMapping(value = "/employee",method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeList(HttpServletResponse response) {
        //MongoOperations mongoOps = new MongoTemplate(mongoClient, "cmpe282lingqianxie572");
        MongoOperations mongoOps = mongoTemplate;
        List<Employee> employeeList = mongoOps.find(
                new Query(),Employee.class, "employee");
        if(employeeList.size()==0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }else {
            String projectJson = null;
            try {
                projectJson = mapper.writeValueAsString(employeeList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return "{\"projectList\":"+projectJson+"}";
        }
    }
}