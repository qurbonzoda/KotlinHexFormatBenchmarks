/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.State
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope

import java.util.HexFormat as JHexFormat

@State(Scope.Benchmark)
open class ComplexByteArrayFormattingBenchmark {
    private val kotlinIpv4Fmt = HexFormat {
        bytes.bytesPerGroup = 1
        bytes.groupSeparator = "."
    }
    private val jdk17Ipv4Fmt = JHexFormat.ofDelimiter(".")

    private val ipv4Address = byteArrayOf(0xd9.toByte(), 0x6e, 0x99.toByte(), 0x4a)
    private val ipv4AddressString = "d9.6e.99.4a"

    @Benchmark
    fun ipv4FormatBaseline() = jdk17Ipv4Fmt.formatHex(ipv4Address)

    @Benchmark
    fun ipv4FormatHexFormat() = ipv4Address.toHexString(kotlinIpv4Fmt)

    @Benchmark
    fun ipv4ParseBaseline() = jdk17Ipv4Fmt.parseHex(ipv4AddressString)

    @Benchmark
    fun ipv4ParseHexFormat() = ipv4AddressString.hexToByteArray(kotlinIpv4Fmt)
}