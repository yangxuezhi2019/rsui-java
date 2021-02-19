package org.rs.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.rs.ConfigTest;
import org.rs.core.beans.model.RsStrategyModel;
import org.rs.core.service.RsAuthService;
import org.rs.core.service.RsStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ConfigTest.class},webEnvironment = WebEnvironment.NONE )
public class MenuTest {

	@Autowired
	private RsAuthService authService;
	@Autowired
	private RsStrategyService strategyService;
	
	@Test
	public void initDb() {
		
		List<RsStrategyModel> list = strategyService.queryStrategy();
		System.out.println(list);
		
		List<String> jsbhList = new ArrayList<>();
		jsbhList.add("ROLE_SYSADMIN");
		authService.queryMenuTreeByJsbh( jsbhList );
	}
}
