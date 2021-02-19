package org.rs.core.beantest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RsTest {

	@Transactional(propagation = Propagation.REQUIRED)
	public void Test() {
		System.out.println("Test");
	}
}
