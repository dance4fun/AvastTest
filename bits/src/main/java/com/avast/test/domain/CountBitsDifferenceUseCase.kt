package com.avast.test.domain

import javax.inject.Inject

/**
 * Calculates the number of bits that will need to be changed in order to convert the first number
 * into the second one.
 *
 * @return number of bits that should be changed to convert the first number into the second one.
 */
class CountBitsDifferenceUseCase @Inject constructor() {

    operator fun invoke(first: Int, second: Int): Int {
        // XOR creates a result of bits that should be changed
        // (1 -> they have different bits, 0 -> they both have the same bit)
        val xorResult = first xor second

        return countBitsToChange(xorResult)
    }

    private fun countBitsToChange(value: Int): Int {
        var count = 0
        var result = value
        while (result > 0) {
            count += 1
            // remove 1 bit using AND operator
            result = result and (result - 1)
        }

        return count
    }
}
