package com.bethibande.kotatsu.java.custom;

import com.bethibande.kotatsu.java.KotatsuParser;
import org.koitharu.kotatsu.parsers.model.MangaParserSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public class CustomParsers {

    private static final Map<MangaParserSource, CustomParser> PARSERS = new HashMap<>();

    static {
        for (final CustomParser customParser : ServiceLoader.load(CustomParser.class)) {
            PARSERS.put(customParser.getSource(), customParser);
        }
    }

    public static Optional<KotatsuParser> getInstance(final MangaParserSource source) {
        return Optional.ofNullable(PARSERS.get(source));
    }

}
