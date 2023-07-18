/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.State
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope

@State(Scope.Benchmark)
open class PrimitiveFormattingBenchmark {
    private val zeroPaddedFmt = HexFormat { number.removeLeadingZeros = false }
    private val zeroStrippedFmt = HexFormat { number.removeLeadingZeros = true }

    private val longLiteral = 1234L
    private val longStringLiteral = "deadc0de"
    private val intLiteral = 1234
    private val intStringLiteral = "deadc0d"

    @Benchmark
    fun long2hexPaddedBaseline() = longLiteral.toString(16).padStart(8, '0')

    @Benchmark
    fun long2hexPaddedHexFormat() = longLiteral.toHexString(zeroPaddedFmt)

    @Benchmark
    fun long2hexBaseline() = longLiteral.toString(16)

    @Benchmark
    fun long2hexHexFmt() = longLiteral.toHexString(zeroStrippedFmt)

    @Benchmark
    fun hex2longBaseline() = longStringLiteral.toLong(16)

    @Benchmark
    fun hex2longHexFormat() = longStringLiteral.hexToLong(zeroStrippedFmt)

    @Benchmark
    fun int2hexPaddedBaseline() = intLiteral.toString(16).padStart(4, '0')

    @Benchmark
    fun int2hexPaddedHexFormat() = intLiteral.toHexString(zeroPaddedFmt)

    @Benchmark
    fun int2hexBaseline() = intLiteral.toString(16)

    @Benchmark
    fun int2hexHexFmt() = intLiteral.toHexString(zeroStrippedFmt)

    @Benchmark
    fun hex2intBaseline() = intStringLiteral.toInt(16)

    @Benchmark
    fun hex2intHexFormat() = intStringLiteral.hexToInt(zeroStrippedFmt)
}
