package com.sbrf.reboot.utils;

import com.sbrf.reboot.utils.rxjava.ObjectToSingleCustomFunction;
import io.reactivex.rxjava3.core.Single;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RxJavaUtils {
    private RxJavaUtils() {
    }

    public static <T, R> List<Single<R>> toCustomSingle(List<T> items, ObjectToSingleCustomFunction<T, R> lambda) {
        return items.stream()
                .map(lambda::applyAsSingle)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> Single<List<T>> zip(List<Single<T>> list) {
        return Single.zip(list, a -> Arrays.stream(a).map(o -> (T) o))
                .flattenStreamAsObservable(it -> it)
                .toList();
    }
}
