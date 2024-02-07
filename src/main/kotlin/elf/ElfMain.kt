package elf

import okio.FileSystem
import okio.Path.Companion.toPath

const val path = "arm64-v8a/libparser.so"
const val pathElf  = "ShowElf"


fun main() {
    val pathUrl = ElfFile::class.java.classLoader.getResource(pathElf)
    FileSystem.SYSTEM.openReadOnly(pathUrl.path.toPath()).use {
        val elfFile = ElfFile(it)
    }
}