package com.github.lodolant.java21.kitchen;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceCaller {
	static ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

	public static <RESULT> Future<RESULT> submit(Callable<RESULT> callable) {
		return executorService.submit(callable);
	}
}
