package com.sbrf.reboot.service;

import java.time.Duration;
import java.util.concurrent.*;

public class SomeService {

    private final ReportService reportService;

    public SomeService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void doSomething() throws ExecutionException, InterruptedException, TimeoutException {

        final Duration timeout = Duration.ofSeconds(5);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                ExecutorService executor = Executors.newSingleThreadExecutor();

                CompletableFuture<String> reportFuture = CompletableFuture
                        .supplyAsync(() -> reportService.sendReport("Отправляю отчет"), executor);

                //какой то код..
                Thread.sleep(Duration.ofSeconds(3).toMillis());

                String reportResult = reportFuture.get();
                executor.shutdown();

                if (reportResult.equals("SUCCESS")) {
                    System.out.println("Отчет отправлен успешно");
                }

                return "some return";
            }
        });

        handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);

        executor.shutdownNow();

    }
}
