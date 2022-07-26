package com.avast.test.data.repository

import com.avast.test.data.source.DictionarySource
import javax.inject.Inject

class DictionaryRepository @Inject constructor(
    private val dictionarySource: DictionarySource
) {
    fun getDictionary() = dictionarySource.dictionary
}
