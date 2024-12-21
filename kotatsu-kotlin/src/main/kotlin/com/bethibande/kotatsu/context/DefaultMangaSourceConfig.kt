package com.bethibande.kotatsu.context

import org.koitharu.kotatsu.parsers.config.ConfigKey
import org.koitharu.kotatsu.parsers.config.MangaSourceConfig

class DefaultMangaSourceConfig : MangaSourceConfig {
    override fun <T> get(key: ConfigKey<T>): T = key.defaultValue
}