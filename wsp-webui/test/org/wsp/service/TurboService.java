package org.wsp.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wsp.models.Turbo;
import org.wsp.service.Implements.TurboServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/wsp-context.xml"})
public class TurboService {

	@Autowired
	TurboServiceImpl service;
	@Test
	public void testgetByTradingSession() {
		List<Turbo> list=service.getByTradingSession(null,"COMMERZBANK");
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.size()>0);
		
		
	}
	
	

}
