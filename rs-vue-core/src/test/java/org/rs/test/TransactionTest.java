package org.rs.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.rs.ConfigTest;
import org.rs.core.beantest.RsTest;
import org.rs.core.service.RsTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   
@SpringBootTest(classes={ConfigTest.class},webEnvironment = WebEnvironment.NONE )
public class TransactionTest {

	@Autowired
	private RsTestService testService;
	@Autowired
	private RsTest rsTest;
	@Autowired
	private ApplicationContext ctx;
	
	@Test
	public void initDb() {
		System.out.println(ctx);
		//ExposeInvocationInterceptor
		rsTest.Test();
		
		testService.queryTest();
		testService.updateTest();
	}
}
