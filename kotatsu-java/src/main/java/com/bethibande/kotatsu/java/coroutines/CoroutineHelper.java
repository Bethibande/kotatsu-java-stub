package com.bethibande.kotatsu.java.coroutines;

import kotlin.coroutines.Continuation;

import java.util.function.Consumer;

public class CoroutineHelper {

    public static <T> T callCoroutineSync(final Consumer<Continuation<T>> function) {
        return new SyncSuspendingDispatcher<>(function).dispatch();
    }

}
