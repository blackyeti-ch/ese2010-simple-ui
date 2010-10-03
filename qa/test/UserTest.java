import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {
	
    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
	
	@Test
	public void createAndRetrieveUser() {
	    new User("markus19@gmail.com", "password", "Markus").save();
	    User markus = User.find("byEmail", "markus19@gmail.com").first();
	    assertNotNull(markus);
	    assertEquals("Markus", markus.fullname);
	}

	@Test
	public void tryConnectAsUser() {
	    new User("markus19@gmail.com", "password", "Markus").save();
	    assertNotNull(User.connect("markus19@gmail.com", "password"));
	    assertNull(User.connect("markus19@gmail.com", "badpassword"));
	    assertNull(User.connect("mario18@gmail.com", "password"));
	}

}
