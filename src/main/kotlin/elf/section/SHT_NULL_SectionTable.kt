package elf.section

import elf.ElfFile
import elf.section.header.Section64Header
import java.nio.ByteBuffer

class SHT_NULL_SectionTable(name: String, section64Header: Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile) :
    SectionTable
        (
        name,
        section64Header,
        byteBuffer,
        elfFile
    ) {

    override fun toString(): String {
        return """ ShtNullSectionTable :
        ${section64Header}
        """.trimIndent()
    }
}