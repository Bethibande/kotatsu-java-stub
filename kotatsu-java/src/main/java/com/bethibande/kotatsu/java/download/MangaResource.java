package com.bethibande.kotatsu.java.download;

import java.io.IOException;
import java.io.InputStream;

public record MangaResource(
        InputStream stream,
        Long contentLength,
        String contentType
) implements AutoCloseable {

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
