package org.rs.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.rs.ConfigTest;
import org.rs.core.beans.model.RsSessionModel;
import org.rs.core.mapper.RsSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   
@SpringBootTest(classes={ConfigTest.class},webEnvironment = WebEnvironment.NONE )
public class SessionTest {

	@Autowired
	private RsSessionMapper sessionMapper;
	
	@Test
	public void operatorValue() {
		
		RsSessionModel result = sessionMapper.findBySessionId("932b2cf2129d4da1bb0eea56791ec0e7");
		System.out.println(result);
	}
}
