package com.sbrf.reboot.utils;

import com.sbrf.reboot.utils.rxjava.ObjectToSingleFunction;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RxJavaUtilsTest {

    @Test
    void orderAndTimeCheckInAsync() {
        int begin = 1;
        int end = 4;
        int seconds = 3;

        List<Integer> list = IntStream.range(begin, end + 1).boxed().collect(Collectors.toList());

        ObjectToSingleFunction<Integer> objectToSingleFunction = i -> Single.fromCallable(() -> {
            Thread.sleep(seconds * 1000L / i);
            return i;
        }).subscribeOn(Schedulers.io());

        long beginTime = System.currentTimeMillis();

        List<Integer> actual = RxJavaUtils
                .zip(RxJavaUtils.toCustomSingle(list, objectToSingleFunction))
                .blockingGet();

        long endTime = System.currentTimeMillis();

        long actualTime = (endTime - beginTime) / 1000;

        assertEquals(list, actual);
        assertEquals(seconds, actualTime);
    }
}
