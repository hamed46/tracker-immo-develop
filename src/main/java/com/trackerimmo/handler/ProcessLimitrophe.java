package com.trackerimmo.handler;

import com.trackerimmo.model.Ad;

public class ProcessLimitrophe implements Handler <Ad, Ad>{

	@Override
	public Ad process(Ad input) {
		return input;
	}

}
