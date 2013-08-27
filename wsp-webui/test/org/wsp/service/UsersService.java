/**
 * 
 */
package org.wsp.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wsp.models.Users;
import org.wsp.service.Implements.UsersServiceImpl;

/**
 * @author bechir
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/wsp-context.xml"})
public class UsersService {

	@Autowired
	private UsersServiceImpl service;
	/**
	 * Test method for {@link org.wsp.service.Implements.UsersServiceImpl#Connect(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testConnect() {
		Boolean test=service.Connect("bechir", "51689498");
		assertTrue(test);
		test=service.Connect("bechir", "5168949800");
		assertFalse(test);
		test=service.Connect("hedi", "5168949800");
		assertFalse(test);
		
	}
	
	@Test
	public void testgetUserByLogin() {
		Users test=service.getUserByLogin("bechir", "51689498");
		assertNotNull(test);
		test=service.getUserByLogin("bechir", "5168949800");
		assertNull(test);
		test=service.getUserByLogin("hedi", "5168949800");
		assertNull(test);
		
	}
	

}
