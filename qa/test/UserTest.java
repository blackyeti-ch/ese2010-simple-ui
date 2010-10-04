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
		// Create user
	    new User("markus19@gmail.com", "password", "Markus").save();
	    
	    // Retrieve user
	    User markus = User.find("byEmail", "markus19@gmail.com").first();
	    assertNotNull(markus);
	    assertEquals("Markus", markus.fullname);
	}

	@Test
	public void tryConnectAsUser() {
		// Create user
	    new User("markus19@gmail.com", "password", "Markus").save();
	    
	    // Test Login-informations
	    assertNotNull(User.connect("markus19@gmail.com", "password"));
	    assertNull(User.connect("markus19@gmail.com", "badpassword"));
	    assertNull(User.connect("mario18@gmail.com", "password"));
	}

}
