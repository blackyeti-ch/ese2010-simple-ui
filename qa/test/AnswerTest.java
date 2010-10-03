import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class AnswerTest extends UnitTest {
	
    @Before
    public void setup() {
        Fixtures.deleteAll();
    }

	@Test
	public void postAnswers() {
		User markus = new User("markus19@gmail.com", "password", "Markus").save();
		Question markusQuestion = new Question(markus, "First Question", "Actually i dont have a question...").save();
 
		new Answer(markusQuestion, "Daniel", "You are stupid").save();
		new Answer(markusQuestion, "Thomas", "wtf, lost 5 seconds of my life reading this <.<").save();
		List<Answer> markusQuestionAnswers = Answer.find("byQuestion", markusQuestion).fetch();
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

}