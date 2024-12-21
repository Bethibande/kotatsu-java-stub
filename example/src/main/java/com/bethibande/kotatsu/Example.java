package com.bethibande.kotatsu;

import com.bethibande.kotatsu.java.KotatsuParser;
import com.bethibande.kotatsu.java.ratelimit.RateLimits;
import org.koitharu.kotatsu.parsers.model.Manga;
import org.koitharu.kotatsu.parsers.model.MangaListFilter;
import org.koitharu.kotatsu.parsers.model.MangaParserSource;
import org.koitharu.kotatsu.parsers.model.SortOrder;

import java.util.List;

public class Example {

    private static KotatsuParser createParser(final MangaParserSource source) {
        final KotatsuParser parser = KotatsuParser.newInstance(source);
        final RateLimits rateLimits = RateLimits.of(5_000);

        return KotatsuParser.withRateLimit(rateLimits, parser);
    }

    public static void main(String[] args) {
        final KotatsuParser parser = createParser(MangaParserSource.MANGADEX);

        // Note that operations like getList are performed in a blocking manner and are meant to run on virtual threads for peak performance.
        final List<Manga> mangas = parser.getList(0, SortOrder.ADDED, MangaListFilter.getEMPTY());
        for (final Manga manga : mangas) {
            System.out.println(manga);
        }
    }

}
