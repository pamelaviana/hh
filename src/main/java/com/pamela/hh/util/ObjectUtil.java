package com.pamela.hh.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectUtil {

    public static <T> void process(T object, Consumer<T> function,
                                   Supplier<? extends RuntimeException> exceptionSupplier) {
        if (object == null) {
            if (exceptionSupplier != null) {
                throw exceptionSupplier.get();
            }
        } else {
            function.accept(object);
        }
    }
}
