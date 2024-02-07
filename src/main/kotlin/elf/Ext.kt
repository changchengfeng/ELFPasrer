package elf

import java.nio.ByteBuffer


fun ByteBuffer.readString(): String {
    var byte: Byte
    val bytes = mutableListOf<Byte>()
    do {
        byte = get()
        bytes.add(byte)
    } while (byte.toInt() != 0)

    if (bytes.size == 1 && bytes[0].toInt() == 0) {
        return ""
    }
    return String(bytes.subList(0, bytes.size - 1).toByteArray())
}

fun Array<out Any>.toPrint(h: H = H.h0): String {
    val builder = StringBuilder()
    for (it in this.indices) {
        builder.append(h.value)
        builder.append("#0x${it.toString(16)} = ${this[it]}")
        builder.append("\n")
    }
    return builder.toString()
}

fun List<out Any>.toPrintList(h: H = H.h0): String {
    val builder = StringBuilder()
    for (it in this.indices) {
        builder.append(h.value)
        builder.append("#0x${it.toString(16)} = ${this[it]}")
        builder.append("\n")
    }
    return builder.toString()
}