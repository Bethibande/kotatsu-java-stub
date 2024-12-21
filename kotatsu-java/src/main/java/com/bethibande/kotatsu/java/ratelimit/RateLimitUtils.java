package com.bethibande.kotatsu.java.ratelimit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public class RateLimitUtils {

    /**
     * Enforces a cooldown period before allowing further requests to proceed. This method ensures
     * thread-safe rate-limiting by using atomic operations to manage timestamps for the next
     * allowable request. If the cooldown has not yet expired, the thread is parked until the
     * cooldown period has elapsed.
     *
     * @param nextRequestTime an {@code AtomicLong} representing the timestamp of the next allowable
     *                        request. This value is atomically updated by the method.
     * @param cooldown a {@code Long} specifying the cooldown duration in milliseconds. If null,
     *                 the method does nothing.
     */
    public static void enforceCooldown(final AtomicLong nextRequestTime, final Long cooldown) {
        if (cooldown == null) return;

        final long now = System.currentTimeMillis();
        final long nextRequest = nextRequestTime.get(); // nextRequest is the timestamp of when the next request may be sent
        final long nextAvailableRequest = Math.max(now, nextRequest) + cooldown;

        // We need to atomically update the next request timestamp before we can send our request
        // to ensure we don't send two requests at the same time.
        if (nextRequestTime.compareAndSet(nextRequest, nextAvailableRequest)) {
            final long remaining = nextRequest - now;

            // Now that we have ensured no other thread will send a request at `nextRequest`,
            // we wait for `nextRequest` if required
            if (remaining > 0) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(remaining));
            }
        } else {
            // We failed to reserve the next execution timestamp, now we need to try again.
            enforceCooldown(nextRequestTime, cooldown);
        }
    }

}
