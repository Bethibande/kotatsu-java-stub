package com.bethibande.kotatsu.java.download;

import com.bethibande.kotatsu.context.MangaLoaderContextImpl;
import com.bethibande.kotatsu.java.KotatsuParser;
import com.bethibande.kotatsu.java.coroutines.CoroutineHelper;
import okhttp3.Response;
import org.koitharu.kotatsu.parsers.model.Favicon;
import org.koitharu.kotatsu.parsers.model.Manga;
import org.koitharu.kotatsu.parsers.model.MangaPage;
import org.koitharu.kotatsu.parsers.network.WebClient;

public class ResourceDownloader {

    private final KotatsuParser parser;
    private final WebClient client;

    public ResourceDownloader(final KotatsuParser parser) {
        this.parser = parser;
        this.client = MangaLoaderContextImpl.getInstance()
                .newWebClient(parser.getSource());
    }

    public MangaResource httpGet(final String url) {
        final Response response = CoroutineHelper.callCoroutineSync(cont -> client.httpGet(url, cont));
        return responseToResource(response);
    }

    private MangaResource responseToResource(final Response response) {
        final String strContentLength = response.header("Content-Length");
        final long contentLength = strContentLength != null ? Long.parseLong(strContentLength) : -1L;

        return new MangaResource(
                response.body().byteStream(),
                contentLength,
                response.header("Content-Type")
        );
    }

    public MangaResource getAnyFavicon() {
        final Favicon favicon = parser.getFavicons()
                .stream()
                .findAny()
                .orElse(null);

        if (favicon == null) return null;
        return getFavicon(favicon);
    }

    public MangaResource getFavicon(final Favicon favicon) {
        return httpGet(favicon.url);
    }

    public MangaResource getCover(final Manga manga) {
        return httpGet(manga.coverUrl);
    }

    public MangaResource getCoverLarge(final Manga manga) {
        if (manga.largeCoverUrl == null) return null;
        return httpGet(manga.largeCoverUrl);
    }

    public MangaResource getPage(final MangaPage page) {
        return httpGet(page.url);
    }

}
