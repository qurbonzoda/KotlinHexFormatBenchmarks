/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.Param
import kotlinx.benchmark.State
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup

import java.util.HexFormat as JHexFormat

@State(Scope.Benchmark)
open class ByteArrayDefaultFormattingBenchmark {
    private val javaHexFormat = JHexFormat.of()
    private val kotlinHexFormat = HexFormat.Default

    @Param("1", "4", "8", "32", "128", "10000", "100000")
    var size: Int = 0

    private var array = ByteArray(0)
    private var hexString = ""

    @Setup
    fun setup() {
        array = ByteArray(size) { (it and 0xFF).toByte() }
        hexString = array.toHexString()
    }

    @Benchmark
    fun formatJava() = javaHexFormat.formatHex(array)

    @Benchmark
    fun formatKotlin() = array.toHexString(kotlinHexFormat)

    @Benchmark
    fun parseJava() = javaHexFormat.parseHex(hexString)

    @Benchmark
    fun parseKotlin() = hexString.hexToByteArray(kotlinHexFormat)
}