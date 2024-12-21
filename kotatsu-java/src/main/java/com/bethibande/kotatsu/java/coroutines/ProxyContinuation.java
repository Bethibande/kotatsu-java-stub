package com.bethibande.kotatsu.java.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ProxyContinuation<T> implements Continuation<T> {

    private final Consumer<T> onSuccess;
    private final Consumer<Throwable> onError;

    public ProxyContinuation(final Consumer<T> onSuccess, final Consumer<Throwable> onError) {
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void resumeWith(@NotNull final Object o) {
        if (o instanceof Throwable throwable) {
            onError.accept(throwable);
        } else {
            onSuccess.accept((T) o);
        }
    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }
}
