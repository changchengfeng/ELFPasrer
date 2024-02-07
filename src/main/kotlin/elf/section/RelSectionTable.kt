package elf.section

import elf.ElfFile
import elf.H
import elf.section.header.Section64Header
import elf.toPrint
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RelSectionTable(name: String, section64Header: Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile) :
    SectionTable(name, section64Header, byteBuffer, elfFile) {
    val elf64Rels: Array<Elf64_Rel>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        elf64Rels = Array((section64Header.sh_size / section64Header.sh_entsize).toInt()) {
            Elf64_Rel(buffer)
        }
    }

    inner class Elf64_Rel(byteBuffer: ByteBuffer) {
        val r_offset: Long
        val r_info: Long

        init {
            r_offset = byteBuffer.long
            r_info = byteBuffer.long
        }

        /*
        * #define ELF64_R_SYM(i)    ((i)>>32)
        * #define ELF64_R_TYPE(i)   ((i)&0xffffffffL)
        * #define ELF64_R_INFO(s,t) (((s)<<32)+((t)&0xffffffffL))
        * * */

        override fun toString(): String {
            val sym = r_info shr 32
            val type = r_info and 0xffffffffL
            return """Elf64_Rela(
             r_offset $r_offset
             r_info $r_info)
             sym ${sym} type ${type}
            """
        }
    }


    override fun toString(): String {
        return """${name} RelSectionTable (
            ${section64Header}
            ${elfFile.sectionTables[section64Header.sh_link]}
            elf64Rels = 
            ${elf64Rels.toPrint(H.h1)} )
        """
    }

}