package elf.section.symtab

import elf.ElfFile
import elf.H
import elf.section.SectionTable
import elf.section.StringSectionTable
import elf.section.header.Section64Header
import elf.section.header.SpecialSectionIndexes
import elf.toPrint
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.experimental.and

class SymtabSectionTable(name: String, section64Header: Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile) :
    SectionTable(name, section64Header, byteBuffer, elfFile) {
    val symbols: Array<Symbol>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        symbols = Array((section64Header.sh_size / section64Header.sh_entsize).toInt()) {
            Symbol(buffer)
        }
    }


    inner class Symbol(byteBuffer: ByteBuffer) {
        val st_name: Int /* Symbol name (string tbl index) */
        val st_info: Byte        /* Symbol type and binding */
        val st_other: Byte        /* Symbol visibility */
        val st_shndx: Short        /* Section index */
        val st_value: Long        /* Symbol value */
        val st_size: Long        /* Symbol size */

        init {
            st_name = byteBuffer.int
            st_info = byteBuffer.get()
            st_other = byteBuffer.get()
            st_shndx = byteBuffer.short
            st_value = byteBuffer.long
            st_size = byteBuffer.long
        }

        /*
        *    #define ELF64_ST_BIND(i)   ((i)>>4)
             #define ELF64_ST_TYPE(i)   ((i)&0xf)
             #define ELF64_ST_INFO(b,t) (((b)<<4)+((t)&0xf))
             #define ELF64_ST_VISIBILITY(o) ((o)&0x3)
        * */

        override fun toString(): String {
            val sectionTable = elfFile.sectionTables[section64Header.sh_link] as StringSectionTable
            val bind = (st_info.toInt() shr 4) and 0xf
            val type = st_info.toInt() and 0xf
            return """
            st_name ${st_name} ${sectionTable.getIndex(st_name)} 
            st_info ${st_info} bind ${SymbolBinding.getValueTypeBy(bind)} type ${SymbolTypes.getValueTypeBy(type)} 
            st_other ${st_other} visibility ${SymbolVisibility.getValueTypeBy((st_other and 0x3).toInt())}
            st_shndx $st_shndx 
            ${SpecialSectionIndexes.getValueTypeBy(st_shndx.toUShort())
                    ?.also { } ?: elfFile.sectionTables[st_shndx.toInt()]
            } 
            st_value ${st_value.toString(16)}
            st_size ${st_size}
            """
        }
    }

    override fun toString(): String {
        return """${name} SectionTable (
    symbols = 
    ${symbols.toPrint(H.h1)} )
        """
    }
}