package org.rs.utils.test;

import org.junit.Test;
import org.rs.utils.SystemInfoUtils;
import org.rs.utils.SystemInfoUtils.RsSystemInfo;

public class JvmTest {

	@Test
	public void test() {
		
		RsSystemInfo systemInfo = SystemInfoUtils.getSystemInfo();
		System.out.println(systemInfo);
	}	
}
