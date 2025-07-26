# Kotatsu java stub
This is a project that wraps the MangaParser class and its functionality of the [kotatsu-parsers](https://github.com/KotatsuApp/kotatsu-parsers) library.
The original library is written in kotlin and uses kotlin coroutines, which makes it difficult to use in Java projects.

### Download
```kotlin
repositories {
    mavenCentral()
    google()
    maven("https://pckg.bethibande.com/repository/maven-releases/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.bethibande:kotatsu-kotlin:21.8")
    implementation("com.bethibande:kotatsu-java:21.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0")
}
```

### Usage example
```java
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
```
