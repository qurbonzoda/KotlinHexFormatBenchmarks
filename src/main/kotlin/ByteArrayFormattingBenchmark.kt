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
open class ByteArrayFormattingBenchmark {
    private val kotlinHexFormat = HexFormat {}
    private val jdk17HexFormat = JHexFormat.of()

    @Param("1", "4", "8", "32", "128")
    var size: Int = 0

    private var array = ByteArray(0)
    private var hexString = ""

    @Setup
    fun setup() {
        array = ByteArray(size) { (it and 0xFF).toByte() }
        hexString = array.toHexString()
    }

    @Benchmark
    fun array2hexBaseline() = jdk17HexFormat.formatHex(array)

    @Benchmark
    fun array2hexHexFormat() = array.toHexString(kotlinHexFormat)

    @Benchmark
    fun hex2arrayBaseline() = jdk17HexFormat.parseHex(hexString)

    @Benchmark
    fun hex2arrayHexFormat() = hexString.hexToByteArray(kotlinHexFormat)
}