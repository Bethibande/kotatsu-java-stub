package com.bethibande.kotatsu.java.coroutines;

import kotlin.Result;
import kotlin.coroutines.Continuation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SyncSuspendingDispatcher<T> {

    private final ProxyContinuation<T> continuation = new ProxyContinuation<>(this::onSuccess, this::onError);
    private final CompletableFuture<T> future = new CompletableFuture<>();
    private final Consumer<Continuation<T>> function;

    public SyncSuspendingDispatcher(final Consumer<Continuation<T>> function) {
        this.function = function;
    }

    private void onSuccess(final T result) {
        if (result instanceof Result.Failure failure) {
            this.onError(failure.exception);
            return;
        }
        future.complete(result);
    }

    private void onError(final Throwable throwable) {
        future.completeExceptionally(throwable);
    }

    public T dispatch() {
        if (future.isDone()) return future.getNow(null);

        function.accept(this.continuation);
        return future.join();
    }

}
