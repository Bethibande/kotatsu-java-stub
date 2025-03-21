package com.bethibande.kotatsu.java;

import com.bethibande.kotatsu.context.MangaLoaderContextImpl;
import com.bethibande.kotatsu.java.coroutines.CoroutineHelper;
import com.bethibande.kotatsu.java.custom.CustomParsers;
import okhttp3.Headers;
import org.koitharu.kotatsu.parsers.MangaParser;
import org.koitharu.kotatsu.parsers.config.ConfigKey.Domain;
import org.koitharu.kotatsu.parsers.config.MangaSourceConfig;
import org.koitharu.kotatsu.parsers.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The main implementation of {@link KotatsuParser}.
 * This class delegates all of its methods to an internal instance of {@link MangaParser}.
 */
public class KotatsuParserImpl implements KotatsuParser {

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Map<MangaParserSource, KotatsuParser> PARSERS = new WeakHashMap<>();

    public static KotatsuParser getInstance(MangaParserSource source) {
        LOCK.lock();
        try {
            return PARSERS.computeIfAbsent(source, KotatsuParserImpl::createInstance);
        } finally {
            LOCK.unlock();
        }
    }

    private static KotatsuParser createInstance(MangaParserSource source) {
        return CustomParsers.getInstance(source)
                .orElseGet(() -> new KotatsuParserImpl(source));
    }

    private final MangaParser parser;

    public KotatsuParserImpl(MangaParserSource source) {
        this.parser = MangaLoaderContextImpl.getInstance().newParserInstance(source);
    }

    @Override
    public Set<SortOrder> getAvailableSortOrders() {
        return parser.getAvailableSortOrders();
    }

    @Override
    public MangaSourceConfig getConfig() {
        return parser.getConfig();
    }

    @Override
    public Domain getConfigKeyDomain() {
        return parser.getConfigKeyDomain();
    }

    @Override
    public Manga getDetails(final Manga manga) {
        return CoroutineHelper.callCoroutineSync(continuation -> parser.getDetails(manga, continuation));
    }

    @Override
    public Favicons getFavicons() {
        return CoroutineHelper.callCoroutineSync(parser::getFavicons);
    }

    @Override
    public MangaListFilterCapabilities getFilterCapabilities() {
        return parser.getFilterCapabilities();
    }

    @Override
    public MangaListFilterOptions getFilterOptions() {
        return CoroutineHelper.callCoroutineSync(parser::getFilterOptions);
    }

    @Override
    public List<Manga> getList(final int i, final SortOrder sortOrder, final MangaListFilter filter) {
        return CoroutineHelper.callCoroutineSync(continuation -> parser.getList(i, sortOrder, filter, continuation));
    }

    @Override
    public List<MangaPage> getPages(final MangaChapter chapter) {
        return CoroutineHelper.callCoroutineSync(continuation -> parser.getPages(chapter, continuation));
    }

    @Override
    public String getPageUrl(final MangaPage page) {
        return CoroutineHelper.callCoroutineSync(continuation -> parser.getPageUrl(page, continuation));
    }

    @Override
    public List<Manga> getRelatedManga(final Manga seed) {
        return CoroutineHelper.callCoroutineSync(continuation -> parser.getRelatedManga(seed, continuation));
    }

    @Override
    public Headers getRequestHeaders() {
        return parser.getRequestHeaders();
    }

    @Override
    public MangaParserSource getSource() {
        return parser.getSource();
    }

}
