package elf.section

import elf.ElfFile
import elf.section.header.Section64Header
import java.nio.ByteBuffer


class SHT_NOBITS_SectionTable(
    name: String,
    section64Header: Section64Header,
    byteBuffer: ByteBuffer,
    elfFile: ElfFile
) :
    SectionTable(name, section64Header, byteBuffer, elfFile) {

    // This section holds uninitialized data that contribute to the program's memory image. By definition, the system initializes the data with zeros when the program begins to run. The section occupies no file space, as indicated by the section type, SHT_NOBITS.
    override fun initData() {

    }

    override fun toString(): String {
        return """ SHT_NOBITS_SectionTable
        ${section64Header}
        """
    }
}