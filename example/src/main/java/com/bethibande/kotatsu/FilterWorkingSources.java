package com.bethibande.kotatsu;

import com.bethibande.kotatsu.java.KotatsuParser;
import com.bethibande.kotatsu.java.download.MangaResource;
import com.bethibande.kotatsu.java.download.ResourceDownloader;
import com.bethibande.kotatsu.java.ratelimit.RateLimits;
import org.koitharu.kotatsu.parsers.model.Manga;
import org.koitharu.kotatsu.parsers.model.MangaListFilter;
import org.koitharu.kotatsu.parsers.model.MangaParserSource;
import org.koitharu.kotatsu.parsers.model.SortOrder;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class FilterWorkingSources {

    private static boolean canDownload(final String url, final KotatsuParser parser) throws IOException {
        final ResourceDownloader downloader = new ResourceDownloader(parser);

        try (MangaResource resource = downloader.httpGet(url)) {
            ImageIO.read(resource.stream());
            return true;
        } catch (final Throwable th) {
            return false;
        }
    }

    private static KotatsuParser createParser(final MangaParserSource source) {
        final KotatsuParser parser = KotatsuParser.of(source);
        final RateLimits rateLimits = RateLimits.of(2_500);

        return KotatsuParser.withRateLimit(rateLimits, parser);
    }

    public static void main(String[] args) throws IOException {
        final Path file = Path.of("working.txt");
        final PrintWriter writer = new PrintWriter(Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));

        final int total = MangaParserSource.values().length;
        int current = 0;
        for (final MangaParserSource value : MangaParserSource.values()) {
            current++;
            try {
                final KotatsuParser parser = createParser(value);
                final List<Manga> mangas = parser.getList(0, SortOrder.ADDED, MangaListFilter.getEMPTY());
                final Manga manga = mangas.getFirst();

                final Manga detailedManga = parser.getDetails(manga);

                if (detailedManga == null) continue;
                if (!canDownload(detailedManga.coverUrl, parser)) continue;

                writer.println(value);
                writer.flush();
            } catch (final Throwable th) {
                /* Ignore errors */
            }

            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2500));

            System.out.printf("\r%d/%d", current, total);
        }
    }

}
