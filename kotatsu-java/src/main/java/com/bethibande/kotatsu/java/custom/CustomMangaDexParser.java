package com.bethibande.kotatsu.java.custom;

import com.bethibande.kotatsu.java.KotatsuParserImpl;
import com.google.auto.service.AutoService;
import org.koitharu.kotatsu.parsers.model.Manga;
import org.koitharu.kotatsu.parsers.model.MangaParserSource;
import org.koitharu.kotatsu.parsers.model.search.MangaSearchQuery;

import java.util.Collections;
import java.util.List;

@AutoService(CustomParser.class)
public class CustomMangaDexParser extends KotatsuParserImpl implements CustomParser {

    private static final int PAGE_SIZE = 20;
    private static final int MAX_QUERY_WINDOW = 10_000;

    public CustomMangaDexParser() {
        super(MangaParserSource.MANGADEX);
    }

    @Override
    public List<Manga> getList(final MangaSearchQuery query) {
        if (query.offset + PAGE_SIZE > MAX_QUERY_WINDOW) return Collections.emptyList();
        return super.getList(query);
    }
}
