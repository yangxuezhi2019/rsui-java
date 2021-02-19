package org.rs.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.rs.ConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   
@SpringBootTest(classes={ConfigTest.class},webEnvironment = WebEnvironment.NONE )
public class RedisTest {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Test
	public void operatorValue() {
		
		//redisTemplate.opsForValue().set("A", 123);
		redisTemplate.boundValueOps("name").set("itcast");  
		Object obj = redisTemplate.boundValueOps("name").get();
		System.out.println(obj);
		redisTemplate.delete("name");
	}
	
	@Test
	public void operatorSet() {
		
		redisTemplate.boundSetOps("nameset").add("aaa");        
        redisTemplate.boundSetOps("nameset").add("bbb");    
        redisTemplate.boundSetOps("nameset").add("ccc");
        
        Set<Object> members = redisTemplate.boundSetOps("nameset").members();
        System.out.println(members);
        
        redisTemplate.boundSetOps("nameset").remove("ccc");
        redisTemplate.delete("nameset");
	}
	
	@Test
	public void operatorList() {
		
		redisTemplate.boundListOps("ListTest:namelist1").leftPush("刘备");
        redisTemplate.boundListOps("ListTest:namelist1").leftPush("关羽");
        redisTemplate.boundListOps("ListTest:namelist1").leftPush("张飞"); 
        
		/*redisTemplate.boundListOps("namelist1").rightPush("aaa");
		redisTemplate.boundListOps("namelist1").rightPush("bbb");
		redisTemplate.boundListOps("namelist1").rightPush("ccc");*/
        
        List<Object> list = redisTemplate.boundListOps("ListTest:namelist1").range(0, 100);
        System.out.println(list);
	}
	
	@Test
	public void operatorHash() {
		
		Map<String,Object> map = new HashMap<>();
		map.put("Test1", "value1");
		map.put("Test2", "value2");
		map.put("Test3", "value3");
		redisTemplate.boundHashOps("hashTest:test1:map1").putAll(map);
		redisTemplate.boundHashOps("hashTest:test1:map2").putAll(map);
		redisTemplate.boundHashOps("hashTest:test1:map3").putAll(map);
        
	}
}
