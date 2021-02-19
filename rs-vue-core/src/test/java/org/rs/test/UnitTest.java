package org.rs.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.rs.core.utils.BeanUtils;

public class UnitTest {

	static public class Test1{
		private String t1;

		public String getT1() {
			return t1;
		}

		public void setT1(String t1) {
			this.t1 = t1;
		}
	}
	static public class Test2{
		private String t1;

		public String getT1() {
			return t1;
		}

		public void setT1(String t1) {
			this.t1 = t1;
		}
	}
	@Test
	public void test1() {
		
		Test1 t1 = new Test1();
		t1.setT1("haha");
		Test2 t2 = new Test2();
		Map<String,Object> map = new HashMap<>();
		BeanUtils.objectToMap(t1, map);
		BeanUtils.mapToObject( map, t2);
		
		System.out.println(t2);
	}
}
