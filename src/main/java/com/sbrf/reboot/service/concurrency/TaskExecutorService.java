package com.sbrf.reboot.service.concurrency;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskExecutorService {

    @Getter
    private final int numberOfThreads;

    private final ExecutorService service;

    public TaskExecutorService(int numberOfThreads) {
        this.service = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    @SneakyThrows
    public void execute(Task task) {
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(task);
        }
    }

    public void shutdown() {
        service.shutdown();
    }

    public void setNumberOfThreads(int numberOfThreads) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) this.service;

        if (numberOfThreads == executor.getCorePoolSize())
            return;

        executor.setCorePoolSize(numberOfThreads);
        executor.setMaximumPoolSize(numberOfThreads);
    }

}
