package com.bethibande.kotatsu.java.ratelimit;

import com.bethibande.kotatsu.java.KotatsuParser;
import okhttp3.Headers;
import org.koitharu.kotatsu.parsers.config.ConfigKey.Domain;
import org.koitharu.kotatsu.parsers.config.MangaSourceConfig;
import org.koitharu.kotatsu.parsers.model.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A wrapper implementation of {@code KotatsuParser} that enforces rate limits on various endpoints.
 * This class ensures that requests to specific functionalities are throttled based on predefined cooldown times.
 * The rate-limiting logic is applied to prevent overloading the system or violating external service policies.
 * <p>
 * This implementation delegates actual parsing operations to a specified {@code KotatsuParser},
 * while applying rate-limiting rules according to the provided {@code RateLimits}.
 * <p>
 * Thread-safe behavior is ensured using {@code ConcurrentMap} and atomic operations.
 */
public class RateLimitedKotatsuParser implements KotatsuParser {

    private final RateLimits rateLimits;
    private final KotatsuParser delegate;

    private final ConcurrentMap<String, AtomicLong> requestTimes = new ConcurrentHashMap<>();

    public RateLimitedKotatsuParser(final RateLimits rateLimits, final KotatsuParser delegate) {
        this.rateLimits = rateLimits;
        this.delegate = delegate;
    }

    private void enforceCooldown(final String endpoint, final Long cooldown) {
        if (cooldown == null) return;
        final AtomicLong nextRequestTime = this.requestTimes.computeIfAbsent(endpoint, __ -> new AtomicLong(0L));

        RateLimitUtils.enforceCooldown(nextRequestTime, cooldown);
    }

    public KotatsuParser getDelegate() {
        return delegate;
    }

    public RateLimits getRateLimits() {
        return rateLimits;
    }

    @Override
    public Set<SortOrder> getAvailableSortOrders() {
        return delegate.getAvailableSortOrders();
    }

    @Override
    public MangaSourceConfig getConfig() {
        return delegate.getConfig();
    }

    @Override
    public Domain getConfigKeyDomain() {
        return delegate.getConfigKeyDomain();
    }

    @Override
    public Manga getDetails(final Manga manga) {
        enforceCooldown("details", rateLimits.details());
        return delegate.getDetails(manga);
    }

    @Override
    public Favicons getFavicons() {
        enforceCooldown("favicons", rateLimits.favicons());
        return delegate.getFavicons();
    }

    @Override
    public MangaListFilterCapabilities getFilterCapabilities() {
        return delegate.getFilterCapabilities();
    }

    @Override
    public MangaListFilterOptions getFilterOptions() {
        enforceCooldown("filterOptions", rateLimits.filterOptions());
        return delegate.getFilterOptions();
    }

    @Override
    public List<Manga> getList(final int i, final SortOrder sortOrder, final MangaListFilter filter) {
        enforceCooldown("list", rateLimits.list());
        return delegate.getList(i, sortOrder, filter);
    }

    @Override
    public List<MangaPage> getPages(final MangaChapter chapter) {
        enforceCooldown("pages", rateLimits.pages());
        return delegate.getPages(chapter);
    }

    @Override
    public String getPageUrl(final MangaPage page) {
        enforceCooldown("pageUrl", rateLimits.pageUrl());
        return delegate.getPageUrl(page);
    }

    @Override
    public List<Manga> getRelatedManga(final Manga seed) {
        enforceCooldown("relatedManga", rateLimits.relatedManga());
        return delegate.getRelatedManga(seed);
    }

    @Override
    public Headers getRequestHeaders() {
        return delegate.getRequestHeaders();
    }

    @Override
    public MangaParserSource getSource() {
        return delegate.getSource();
    }
}
