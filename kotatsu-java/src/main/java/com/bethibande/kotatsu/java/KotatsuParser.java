package com.bethibande.kotatsu.java;

import com.bethibande.kotatsu.java.ratelimit.RateLimitedKotatsuParser;
import com.bethibande.kotatsu.java.ratelimit.RateLimits;
import okhttp3.Headers;
import org.koitharu.kotatsu.parsers.MangaLoaderContext;
import org.koitharu.kotatsu.parsers.config.ConfigKey.Domain;
import org.koitharu.kotatsu.parsers.config.MangaSourceConfig;
import org.koitharu.kotatsu.parsers.model.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This interface models all methods of the {@link org.koitharu.kotatsu.parsers.MangaParser} class provided by kotatsu.
 * Please note that this interface and all of its implementing classes are work in a blocking manner,
 * they are designed to run on virtual threads for optimal performance and ease of use.
 */
public interface KotatsuParser {

    static KotatsuParser of(final MangaParserSource source) {
        return KotatsuParserImpl.getInstance(source);
    }

    static KotatsuParser withRateLimit(final RateLimits rateLimits, final KotatsuParser delegate) {
        return new RateLimitedKotatsuParser(rateLimits, delegate);
    }

    static KotatsuParser withRateLimit(final long rateLimit, final KotatsuParser delegate) {
        return new RateLimitedKotatsuParser(RateLimits.of(rateLimit), delegate);
    }

    MangaLoaderContext getContext();

    Set<SortOrder> getAvailableSortOrders();

    MangaSourceConfig getConfig();

    Domain getConfigKeyDomain();

    SortOrder getDefaultSortOrder();

    Manga getDetails(final Manga manga);

    Favicons getFavicons();

    MangaListFilterCapabilities getFilterCapabilities();

    MangaListFilterOptions getFilterOptions();

    List<Manga> getList(final int i, final SortOrder sortOrder, final MangaListFilter filter);

    List<MangaPage> getPages(final MangaChapter chapter);

    String getPageUrl(final MangaPage page);

    List<Manga> getRelatedManga(final Manga seed);

    Headers getRequestHeaders();

    MangaParserSource getSource();

    Locale getSourceLocale();

}
