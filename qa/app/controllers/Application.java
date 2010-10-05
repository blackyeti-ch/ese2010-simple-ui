package controllers;
 
import java.util.*;
 
import play.*;
import play.mvc.*;
import play.data.validation.*;
 
import models.*;
 
public class Application extends Controller {
	
	@Before
	static void addDefaults() {
	    renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
	    renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}
 
    public static void index() {
        Question frontQuestion = Question.find("order by postedAt desc").first();
        List<Question> olderQuestions = Question.find(
            "order by postedAt desc"
        ).from(1).fetch(10);
        render(frontQuestion, olderQuestions);
    }
    
    public static void show(Long id) {
        Question question = Question.findById(id);
        render(question);
    }
    
    public static void questionAnswer(Long questionId, @Required String author, @Required String content) {
        Question question = Question.findById(questionId);
        if(validation.hasErrors()) {
            render("Application/show.html", question);
        }
        question.addAnswer(author, content);
        flash.success("Thanks for asking %s", author);
        show(questionId);
    }
 
}
