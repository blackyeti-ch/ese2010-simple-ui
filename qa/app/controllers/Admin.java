package controllers;
 
import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }
 
    public static void index() {
        List<Question> questions = Question.find("author.email", Security.connected()).fetch();
        render(questions);
    }
    
    public static void form() {
        render();
    }
     
    public static void save(String title, String content, String tags) {
        // Create question
        User author = User.find("byEmail", Security.connected()).first();
        Question question = new Question(author, title, content);
        // Validate
        validation.valid(question);
        if(validation.hasErrors()) {
            render("@form", question);
        }
        // Save
        question.save();
        index();
    }

    public static void form(Long id) {
        if(id != null) {
            Question question = Question.findById(id);
            render(question);
        }
        render();
    }

}
