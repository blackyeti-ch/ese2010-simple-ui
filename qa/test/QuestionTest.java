import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class QuestionTest extends UnitTest {
	
    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
	
    @Test
    public void createQuestion() {
        User markus = new User("markus19@gmail.com", "password", "Markus").save();
        new Question(markus, "First Question", "Actually i dont have a question...").save();
        assertEquals(1, Question.count());
        List<Question> markusPosts = Question.find("byAuthor", markus).fetch();
        
        assertEquals(1, markusPosts.size());
        Question firstPost = markusPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(markus, firstPost.author);
        assertEquals("First Question", firstPost.title);
        assertEquals("Actually i dont have a question...", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }

}
