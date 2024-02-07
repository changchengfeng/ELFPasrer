package elf

import elf.program.Program64Header
import elf.section.SHSTRTAB
import elf.section.SectionTable
import elf.section.StringSectionTable
import elf.section.header.Section64Header
import okio.FileHandle
import okio.buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ElfFile(fileHandle: FileHandle) {

    val elf64Header: Elf64Header
    val section64Headers: Array<Section64Header>
    val program64Headers: Array<Program64Header>
    val sectionTables: Array<SectionTable>

    init {
        val bytes = fileHandle.source().buffer().readByteArray()
        val byteBuffer = ByteBuffer.wrap(bytes)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        elf64Header = Elf64Header(byteBuffer)

        program64Headers = Array(elf64Header.e_phnum.toInt()) {
            byteBuffer.position(elf64Header.e_phoff.toInt() + it * elf64Header.e_phentsize)
            Program64Header(this, byteBuffer)
        }

        byteBuffer.position(elf64Header.e_shoff.toInt())
        section64Headers = Array(elf64Header.e_shnum.toInt()) {
            byteBuffer.position(elf64Header.e_shoff.toInt() + it * elf64Header.e_ehsize)
            Section64Header(byteBuffer)
        }

        val strIndex = elf64Header.e_shstrndx.toInt()
        val StringSectionTable = StringSectionTable(SHSTRTAB, section64Headers[strIndex], byteBuffer, this)
        println("\n----------------------------------------------------------------------------------------------------------------------------------------------------\n")

        sectionTables = Array(elf64Header.e_shnum.toInt()) {
            if (it == strIndex) {
                StringSectionTable
            } else {
                SectionTable.readSectionTable(StringSectionTable, section64Headers[it], byteBuffer, this@ElfFile)
            }
        }
        println("\n----------------------------------------------------------------------------------------------------------------------------------------------------\n")

        println("bytes.size ${bytes.size}")
        println(elf64Header)
        println("\n----------------------------------------------------------------------------------------------------------------------------------------------------\n")
        println(program64Headers.toPrint())
        println("\n-----------------------------------------------------------------------------------------------------------------------------------------------------\n")
        println(sectionTables.toPrint())

    }
}