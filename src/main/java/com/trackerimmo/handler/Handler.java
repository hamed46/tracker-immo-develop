package com.trackerimmo.handler;

public interface Handler<I, O> {
	O process(I input);
}
