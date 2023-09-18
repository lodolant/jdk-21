package com.github.lodolant.java21.kitchen;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExecutorServiceCallerTest {
    @Test
    public void test() throws ExecutionException, InterruptedException {
        // GIVEN
        Callable<String> task = () -> "Result";

        // WHEN
        Future<String> submit = ExecutorServiceCaller.submit(task);

        // THEN
        String result = submit.get();
        Assertions.assertEquals("Result", result);
    }
}
