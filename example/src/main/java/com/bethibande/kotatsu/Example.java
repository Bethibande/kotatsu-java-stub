package com.bethibande.kotatsu;

import com.bethibande.kotatsu.java.KotatsuParser;
import com.bethibande.kotatsu.java.download.MangaResource;
import com.bethibande.kotatsu.java.download.ResourceDownloader;
import com.bethibande.kotatsu.java.ratelimit.RateLimits;
import org.koitharu.kotatsu.parsers.model.Manga;
import org.koitharu.kotatsu.parsers.model.MangaParserSource;
import org.koitharu.kotatsu.parsers.model.SortOrder;
import org.koitharu.kotatsu.parsers.model.search.MangaSearchQuery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Example {

    private static KotatsuParser createParser(final MangaParserSource source) {
        final KotatsuParser parser = KotatsuParser.of(source);
        final RateLimits rateLimits = RateLimits.of(5_000);

        return KotatsuParser.withRateLimit(rateLimits, parser);
    }

    public static void main(String[] args) throws IOException {
        final KotatsuParser parser = createParser(MangaParserSource.MANGADEX);

        // Note that operations like getList are performed in a blocking manner and are meant to run on virtual threads for peak performance.
        final List<Manga> mangas = parser.getList(new MangaSearchQuery.Builder()
                .order(SortOrder.ADDED)
                .offset(0)
                .build());

        for (final Manga manga : mangas) {
            System.out.println(manga);
        }

        final ResourceDownloader downloader = new ResourceDownloader(parser);
        final MangaResource favicon = downloader.getAnyFavicon();

        Files.write(Path.of("favicon"), favicon.stream().readAllBytes());
    }

}
