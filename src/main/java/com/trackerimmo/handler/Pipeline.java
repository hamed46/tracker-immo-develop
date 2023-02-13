package com.trackerimmo.handler;

public class Pipeline<I, O> {

	private final Handler<I, O> currentHandler;

	public Pipeline(Handler<I, O> currentHandler) {
		this.currentHandler = currentHandler;
	}

	public Pipeline<I, O> addHandler(Handler<O, O> newHandler) {
		return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
	}

	public O execute(I input) {
		return currentHandler.process(input);
	}

}
