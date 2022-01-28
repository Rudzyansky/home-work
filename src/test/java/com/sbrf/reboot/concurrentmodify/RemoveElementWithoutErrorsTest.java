package com.sbrf.reboot.concurrentmodify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveElementWithoutErrorsTest {
    private List<Integer> list;

    final int indexToDelete = 1;

    final int beginElement = 1;
    final int endElement = 3;

    @BeforeEach
    public void setup() {
        list = IntStream.range(beginElement, endElement + 1).boxed().collect(Collectors.toList());
    }

    @Test
    public void successConcurrentModificationException() {
        assertThrows(ConcurrentModificationException.class, () -> {
            for (Integer integer : list) {
                list.remove(indexToDelete);
            }
        });
    }

    @Test
    public void successRemoveElementWithoutErrors() {
        assertDoesNotThrow(() -> {
            for (Integer integer : list) {
            }
            list.remove(indexToDelete);
        });
    }

    @Test
    public void successRemoveElementWithoutErrorsIteratorMethod() {
        assertDoesNotThrow(() -> {
            int index = 0;
            for (Iterator<Integer> it = list.iterator(); it.hasNext(); index++) {
                it.next();
                if (index == indexToDelete) {
                    it.remove();
                }
            }
        });
    }

    @Test
    public void successRemoveElementWithoutErrorsSynchronizeMethod() {
        assertDoesNotThrow(() -> {
            final Object lock = new Object();

            final Thread removeItemThread = new Thread(() -> {
                synchronized (lock) {
                    list.remove(indexToDelete);
                }
            });

            synchronized (lock) {
                for (Integer integer : list) {
                    if (!removeItemThread.isAlive()) {
                        /*
                         * The thread payload will be executed after the top-level lock object is released.
                         * Until that time, the thread will be running and waiting.
                         * So isAlive will be true and only one thread will be running.
                         */
                        removeItemThread.start();
                    }

                    /*
                     * Simulates some actions.
                     * So that the thread has time to run before the end of iterations.
                     */
                    Thread.sleep(1);
                }
            }
        });
    }
}
