package org.rs.core.service.impl;

import org.rs.core.service.RsTestService;
import org.springframework.stereotype.Service;

@Service
public class RsTestServiceImpl implements RsTestService{

	@Override
	public void queryTest() {
		System.out.println("queryTest");
	}

	@Override
	public void updateTest() {
		System.out.println("updateTest");
	}

}
