# Kotatsu java stub
This is a project that wraps the MangaParser class and its functionality of the [kotatsu-parsers](https://github.com/KotatsuApp/kotatsu-parsers) library.
The original library is written in kotlin and uses kotlin coroutines, which makes it difficult to use in Java projects.

### Usage example
```java
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
```