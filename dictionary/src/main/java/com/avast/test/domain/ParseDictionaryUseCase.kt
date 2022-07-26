package com.avast.test.domain

import com.avast.test.data.repository.DictionaryRepository
import java.util.Locale
import javax.inject.Inject

/**
 * Segment the input string into a space-separated sequence of dictionary words if possible.
 *
 * @return space-separated sequence of words found.
 */
class ParseDictionaryUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
) {

    operator fun invoke(input: String): String {
        if (input.isBlank()) {
            return ""
        }

        val inputLc = input.lowercase(Locale.getDefault())
        val dictionary = dictionaryRepository.getDictionary()
        val segmentedStrings = mutableSetOf<String>()

        findSegments(inputLc, dictionary, segmentedStrings)
        return segmentedStrings.joinToString(" ")
    }

    /**
     * Example:
     * Input - "aspineappleassecondaryapple"
     * 1- start with the first char "a", then "as", "asp".."aspineappleassecondaryapple" -> no matches;
     * 2- start with the second char "s" ->  no matches;
     * 3- start with the third char "p", find "pineapple" -> save result,
     *   check the second part of the string "assecondaryapple"
     *   3.1- repeat steps 2 and 3
     *   3.2- find "second" in "secondaryapple" -> check the suffix "aryapple",
     *     3.2.1- repeat until "apple" found;
     *   3.3- find "secondary" in "secondaryapple" -> check the suffix "apple" (already in the dictionary map),
     * 4- find "apple" in "pineapple" -> already in the map.
     *
     * Result -> "pineapple second apple secondary"
     */
    private fun findSegments(
        input: String,
        dictionary: Set<String>,
        segmentedStrings: MutableSet<String>
    ) {
        if (segmentedStrings.contains(input)) {
            return
        }

        val inputLength = input.length
        var lastCharIndex = inputLength

        for (start in 0..inputLength) {
            if (start == lastCharIndex) {
                return
            }
            for(end in start until inputLength) {
                val substringStart = input.substring(start .. end)
                if (substringStart in dictionary) {
                    // remember last char index that needs to be checked so we don't check the same sections twice
                    lastCharIndex = end
                    segmentedStrings.add(substringStart)
                    val substringEnd = input.substring(end + 1, inputLength)
                    findSegments(substringEnd, dictionary, segmentedStrings)
                }
            }
        }
    }
}
