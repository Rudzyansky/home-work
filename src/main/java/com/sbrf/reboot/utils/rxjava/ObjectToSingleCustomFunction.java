package com.sbrf.reboot.utils.rxjava;

import io.reactivex.rxjava3.core.Single;

@FunctionalInterface
public interface ObjectToSingleCustomFunction<T, R> {
    Single<R> applyAsSingle(T t);
}
