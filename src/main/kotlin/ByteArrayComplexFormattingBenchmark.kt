/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.State
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Param
import kotlinx.benchmark.Setup

import java.util.HexFormat as JHexFormat

@State(Scope.Benchmark)
open class ByteArrayComplexFormattingBenchmark {
    private val javaDotDelimFormat = JHexFormat.ofDelimiter(".")
    private val kotlinDotDelimFormat = HexFormat { bytes.byteSeparator = "." }
    private val javaLongDelimFormat = JHexFormat.ofDelimiter(" .a. ")
    private val kotlinLongDelimFormat = HexFormat { bytes.byteSeparator = " .a. " }
    private val javaComplexFormat = JHexFormat.ofDelimiter(" ").withPrefix("&#x").withSuffix(";")
    private val kotlinComplexFormat = HexFormat { bytes.byteSeparator = " "; bytes.bytePrefix = "&#x"; bytes.byteSuffix = ";" }

    @Param("1", "4", "8", "32", "128", "10000", "100000")
    var size: Int = 0

    private var array = ByteArray(0)
    private var dotHexString = ""
    private var longDelimHexString = ""
    private var complexHexString = ""

    @Setup
    fun setup() {
        array = ByteArray(size) { (it and 0xFF).toByte() }
        dotHexString = array.toHexString(kotlinDotDelimFormat)
        longDelimHexString = array.toHexString(kotlinLongDelimFormat)
        complexHexString = array.toHexString(kotlinComplexFormat)
    }

    // Dot delimiter

    @Benchmark
    fun dotDelimFormatJava() = javaDotDelimFormat.formatHex(array)

    @Benchmark
    fun dotDelimFormatKotlin() = array.toHexString(kotlinDotDelimFormat)

    @Benchmark
    fun dotDelimParseJava() = javaDotDelimFormat.parseHex(dotHexString)

    @Benchmark
    fun dotDelimParseKotlin() = dotHexString.hexToByteArray(kotlinDotDelimFormat)

    // Long delimiter

    @Benchmark
    fun longDelimFormatJava() = javaLongDelimFormat.formatHex(array)

    @Benchmark
    fun longDelimFormatKotlin() = array.toHexString(kotlinLongDelimFormat)

    @Benchmark
    fun longDelimParseJava() = javaLongDelimFormat.parseHex(longDelimHexString)

    @Benchmark
    fun longDelimParseKotlin() = longDelimHexString.hexToByteArray(kotlinLongDelimFormat)

    // Complex format

    @Benchmark
    fun complexFormatJava() = javaComplexFormat.formatHex(array)

    @Benchmark
    fun complexFormatKotlin() = array.toHexString(kotlinComplexFormat)

    @Benchmark
    fun complexParseJava() = javaComplexFormat.parseHex(complexHexString)

    @Benchmark
    fun complexParseKotlin() = complexHexString.hexToByteArray(kotlinComplexFormat)
}