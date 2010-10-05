import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class PostTest extends UnitTest {
	
    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
    
    @Test
    public void createQuestion() {
    	// Create user and question
        User markus = new User("markus19@gmail.com", "password", "Markus").save();
        new Question(markus, "First Question", "Actually i dont have a question...").save();
        assertEquals(1, Question.count());
        List<Question> markusQuestions = Question.find("byAuthor", markus).fetch();
        
        // Question tests
        assertEquals(1, markusQuestions.size());
        Question firstQuestion = markusQuestions.get(0);
        assertNotNull(firstQuestion);
        assertEquals(markus, firstQuestion.author);
        assertEquals("First Question", firstQuestion.title);
        assertEquals("Actually i dont have a question...", firstQuestion.content);
        assertNotNull(firstQuestion.postedAt);
    }
    
	@Test
	public void postAnswers() {
		// Create user and question
		User markus = new User("markus19@gmail.com", "password", "Markus").save();
		Question markusQuestion = new Question(markus, "First Question", "Actually i dont have a question...").save();
 
		// Create and retrieve answers
		new Answer(markusQuestion, "Daniel", "You are stupid").save();
		new Answer(markusQuestion, "Thomas", "wtf, lost 5 seconds of my life reading this <.<").save();
		List<Answer> markusQuestionAnswers = Answer.find("byQuestion", markusQuestion).fetch();
		
		// Anser tests
		assertEquals(2, markusQuestionAnswers.size());
		Answer firstAnswer = markusQuestionAnswers.get(0);
		assertNotNull(firstAnswer);
		assertEquals("Daniel", firstAnswer.author);
		assertEquals("You are stupid", firstAnswer.content);
		assertNotNull(firstAnswer.postedAt);
 
		Answer secondAnswer = markusQuestionAnswers.get(1);
		assertNotNull(secondAnswer);
		assertEquals("Thomas", secondAnswer.author);
		assertEquals("wtf, lost 5 seconds of my life reading this <.<", secondAnswer.content);
		assertNotNull(secondAnswer.postedAt);
	}
	
	@Test
	public void useTheAnswersRelation() {
		// Create user and question 
		User ali = new User("alig88@gmail.com", "alligator1", "Ali").save();
	    Question aliQuestion = new Question(ali, "Crocodiles/Alligators", "What's the difference between crocodiles and aligators?").save();

	    // Create answers
	    aliQuestion.addAnswer("Joe", "Dude, learn to google...");
	    aliQuestion.addAnswer("Pascal", "Check this: http://lmgtfy.com/?q=difference+crocodiles+alligators");
	 
	    // Count questions and answers
	    assertEquals(1, User.count());
	    assertEquals(1, Question.count());
	    assertEquals(2, Answer.count());
	 
	    // Retrieve question
	    aliQuestion = Question.find("byAuthor", ali).first();
	    assertNotNull(aliQuestion);
	 
	    // Navigate to answers
	    assertEquals(2, aliQuestion.answers.size());
	    assertEquals("Joe", aliQuestion.answers.get(0).author);
	    
	    // Delete question
	    aliQuestion.delete();
	    
	    // Check if answers to the question has been deleted
	    assertEquals(1, User.count());
	    assertEquals(0, Question.count());
	    assertEquals(0, Answer.count());
	}

	@Test
	public void fullTest() {    // Mainly overtaken from the Play! Framework tutorial
	    Fixtures.load("data.yml");
	 
	    // Count things
	    assertEquals(2, User.count());
	    assertEquals(3, Question.count());
	    assertEquals(3, Answer.count());
	 
	    // Try to connect as users
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNotNull(User.connect("jeff@gmail.com", "secret"));
	    assertNull(User.connect("jeff@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
	 
	    // Find all of Bob's questions
	    List<Question> bobQuestions = Question.find("author.email", "bob@gmail.com").fetch();
	    assertEquals(2, bobQuestions.size());
	 
	    // Find all answers related to Bob's questions
	    List<Answer> bobAnswers = Answer.find("question.author.email", "bob@gmail.com").fetch();
	    assertEquals(3, bobAnswers.size());
	 
	    // Find the most recent question
	    Question frontQuestion = Question.find("order by postedAt desc").first();
	    assertNotNull(frontQuestion);
	    assertEquals("About the model layer", frontQuestion.title);
	 
	    // Check that this question has two answers
	    assertEquals(1, frontQuestion.answers.size());
	 
	    // Question a new answer
	    frontQuestion.addAnswer("Jim", "I don't think so");
	    assertEquals(2, frontQuestion.answers.size());
	    assertEquals(4, Answer.count());
	}
    
}