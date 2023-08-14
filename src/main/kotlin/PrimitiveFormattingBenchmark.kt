/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.*

import java.util.HexFormat as JHexFormat

@State(Scope.Benchmark)
open class PrimitiveFormattingBenchmark {
    private val zeroPaddedFmt = HexFormat { number.removeLeadingZeros = false }
    private val zeroStrippedFmt = HexFormat { number.removeLeadingZeros = true }
    private val jhexFormat = JHexFormat.of()

    @Param("false", "true")
    var isLarge: Boolean = false

    private val largeLongStringLiteral = "deadc0dedeadc0d"
    private val smallLongStringLiteral = "3a"

    private var longLiteral: Long = 0
    private var longHexString: String = ""
    private var intLiteral: Int = 0
    private var intHexString: String = ""
    private var byteLiteral: Byte = 0
    private var byteHexString: String = ""

    @Setup
    fun setup() {
        if (isLarge) {
            longHexString = largeLongStringLiteral
            longLiteral = longHexString.toLong(radix = 16)
            intHexString = largeLongStringLiteral.substring(0, 7)
            intLiteral = intHexString.toInt(radix = 16)
        } else {
            longHexString = smallLongStringLiteral
            longLiteral = longHexString.toLong(radix = 16)
            intHexString = smallLongStringLiteral
            intLiteral = intHexString.toInt(radix = 16)
        }
        byteHexString = smallLongStringLiteral
        byteLiteral = byteHexString.toByte(radix = 16)
    }

    // Long -> Hex

    @Benchmark
    fun formatLongPaddedBaseline(): String = longLiteral.toString(16).padStart(16, '0')

    @Benchmark
    fun formatLongPaddedJava(): String = jhexFormat.toHexDigits(longLiteral)

    @Benchmark
    fun formatLongPaddedKotlin(): String = longLiteral.toHexString(zeroPaddedFmt)

    @Benchmark
    fun formatLongStrippedBaseline(): String = longLiteral.toString(16)

    @Benchmark
    fun formatLongStrippedKotlin(): String = longLiteral.toHexString(zeroStrippedFmt)

    // Hex -> Long

    @Benchmark
    fun parseLongBaseline(): Long = longHexString.toLong(16)

    @Benchmark
    fun parseLongJava(): Long = JHexFormat.fromHexDigitsToLong(longHexString)

    @Benchmark
    fun parseLongKotlin(): Long = longHexString.hexToLong()

    // Int -> Hex

    @Benchmark
    fun formatIntPaddedBaseline(): String = intLiteral.toString(16).padStart(8, '0')

    @Benchmark
    fun formatIntPaddedJava(): String = jhexFormat.toHexDigits(intLiteral)

    @Benchmark
    fun formatIntPaddedKotlin(): String = intLiteral.toHexString(zeroPaddedFmt)

    @Benchmark
    fun formatIntStrippedBaseline(): String = intLiteral.toString(16)

    @Benchmark
    fun formatIntStrippedKotlin(): String = intLiteral.toHexString(zeroStrippedFmt)

    // Hex -> Int

    @Benchmark
    fun parseIntBaseline(): Int = intHexString.toInt(16)

    @Benchmark
    fun parseIntJava(): Int = JHexFormat.fromHexDigits(intHexString)

    @Benchmark
    fun parseIntKotlin(): Int = intHexString.hexToInt()

    // Byte -> Hex

    @Benchmark
    fun formatBytePaddedBaseline(): String = byteLiteral.toString(16).padStart(2, '0')

    @Benchmark
    fun formatBytePaddedJava(): String = jhexFormat.toHexDigits(byteLiteral)

    @Benchmark
    fun formatBytePaddedKotlin(): String = byteLiteral.toHexString(zeroPaddedFmt)

    @Benchmark
    fun formatByteStrippedBaseline(): String = byteLiteral.toString(16)

    @Benchmark
    fun formatByteStrippedKotlin(): String = byteLiteral.toHexString(zeroStrippedFmt)

    // Hex -> Byte

    @Benchmark
    fun parseByteBaseline(): Byte = byteHexString.toByte(16)

    @Benchmark
    fun parseByteJava(): Byte = JHexFormat.fromHexDigits(byteHexString).toByte()

    @Benchmark
    fun parseByteKotlin(): Byte = byteHexString.hexToByte()
}
