package org.jetbrains.kotlin.benchmarks

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Mode
import kotlin.random.*

@State(Scope.Benchmark)
open class TheoriesBenchmark {

    @Param("128", "256")
    var size = 128


    private val hexCharCodes = listOf('0'..'9', 'A'..'F', 'a'..'f').flatten().map { it.code }.toSet()
    private val intData = IntArray(size) { if (it in hexCharCodes) it.toChar().digitToInt(16) else -1 }
    private val longData = LongArray(size) { if (it in hexCharCodes) it.toChar().digitToInt(16).toLong() else -1 }
    private val string = CharArray(16) { hexCharCodes.random().toChar() }.concatToString().also(::println)


    private fun longDigitUshr8(index: Int): Long {
        val code = string[index].code
        if (code ushr 8 == 0 && longData[code] >= 0) {
            return longData[code]
        }
        error(code)
    }

    private fun intDigitUshr8(index: Int): Int {
        val code = string[index].code
        if (code ushr 8 == 0 && intData[code] >= 0) {
            return intData[code]
        }
        error(code)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun sumLongsUshr8(): Long {
        var r: Long = 0
        for (i in 0 until 16) {
            r = (r shl 4) or longDigitUshr8(i)
        }
        return r
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun sumIntsUshr8(): Long {
        var r: Long = 0
        for (i in 0 until 16) {
            r = (r shl 4) or intDigitUshr8(i).toLong()
        }
        return r
    }


    private fun longDigitAnd7f(index: Int): Long {
        val code = string[index].code
        if (code and 0x7f.inv() == 0 && longData[code] >= 0) {
            return longData[code]
        }
        error(code)
    }

    private fun intDigitAnd7f(index: Int): Int {
        val code = string[index].code
        if (code and 0x7f.inv() == 0 && intData[code] >= 0) {
            return intData[code]
        }
        error(code)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun sumLongsAnd7f(): Long {
        var r: Long = 0
        for (i in 0 until 16) {
            r = (r shl 4) or longDigitAnd7f(i)
        }
        return r
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    fun sumIntsAnd7f(): Long {
        var r: Long = 0
        for (i in 0 until 16) {
            r = (r shl 4) or intDigitAnd7f(i).toLong()
        }
        return r
    }
}