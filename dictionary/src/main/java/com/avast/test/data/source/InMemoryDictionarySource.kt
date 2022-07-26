package com.avast.test.data.source

class InMemoryDictionarySource : DictionarySource {
    override val dictionary = setOf(
        "apple",
        "pineapple",
        "pie",
        "shoe",
        "second",
        "secondary",
        "calculate",
        "display",
        "love",
    )
}
