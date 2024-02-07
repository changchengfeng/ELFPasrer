package elf.section


import elf.ElfFile
import elf.H
import elf.readString
import elf.section.header.Section64Header
import elf.toPrintList
import java.nio.ByteBuffer


open class StringSectionTable(
    name: String, section64Header:
    Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile
) : SectionTable(name, section64Header, byteBuffer, elfFile) {

    val strings: MutableList<String> = mutableListOf()
    var strBuffer: ByteBuffer = ByteBuffer.wrap(byteArrayOf())

    init {
        val limit = byteBuffer.limit()
        byteBuffer.position((section64Header.sh_offset).toInt())
        byteBuffer.limit((section64Header.sh_offset + section64Header.sh_size).toInt())
        var bytes = ByteArray(section64Header.sh_size.toInt())
        byteBuffer.get(bytes)
        strBuffer = ByteBuffer.wrap(bytes)
        byteBuffer.position((section64Header.sh_offset).toInt())
        byteBuffer.limit((section64Header.sh_offset + section64Header.sh_size).toInt())
        while (byteBuffer.position() < byteBuffer.limit()) {
            val string = byteBuffer.readString()
            strings.add(string)
        }
        byteBuffer.limit(limit)
    }

    override fun toString(): String {
        return """
StringSectionTable( 
    section64Header $section64Header
    name $name
    strings = 
${strings.toPrintList(H.h1)})"""
    }

    fun getIndex(index: Int): String {
        val byteBuffer = strBuffer.position(index)
        return byteBuffer.readString()
    }
}