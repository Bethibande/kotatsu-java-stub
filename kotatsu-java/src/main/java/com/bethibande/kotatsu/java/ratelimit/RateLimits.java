package com.bethibande.kotatsu.java.ratelimit;

/**
 * Represents rate limits for various endpoints in a parser system.
 * This record encapsulates different cooldown times in milliseconds,
 * which are applied to requests for specific functionalities such as
 * fetching details, favicons, filtering options, and more.
 *
 * Each property defines the amount of time to delay before
 * a request to the respective endpoint can be made again.
 *
 * Designed to be used in conjunction with {@code RateLimitedKotatsuParser}
 * to enforce defined rate limits on system functionalities.
 */
public record RateLimits(
    Long details,
    Long favicons,
    Long filterOptions,
    Long list,
    Long pages,
    Long pageUrl,
    Long relatedManga
) {

    /**
     * Creates a new instance of {@code RateLimits} with all rate limit values set to the specified value.
     *
     * @param rateLimit the rate limit value in milliseconds to be applied to all endpoints.
     * @return a {@code RateLimits} instance with uniform rate limits for all endpoints.
     */
    public static RateLimits of(final long rateLimit) {
        return new RateLimits(
                rateLimit,
                rateLimit,
                rateLimit,
                rateLimit,
                rateLimit,
                rateLimit,
                rateLimit
        );
    }

}
