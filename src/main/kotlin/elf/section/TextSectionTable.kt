package elf.section

import elf.ElfFile
import elf.section.header.Section64Header
import java.nio.ByteBuffer

class TextSectionTable(name: String, section64Header: Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile) :
    SectionTable(name, section64Header, byteBuffer,elfFile) {
}