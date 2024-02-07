package elf.section

import elf.ElfFile
import elf.H
import elf.section.header.Section64Header
import elf.toPrint
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RelaSectionTable(name: String, section64Header: Section64Header, byteBuffer: ByteBuffer, elfFile: ElfFile) :
    SectionTable(name, section64Header, byteBuffer, elfFile) {
    val elf64Relas: Array<Elf64_Rela>

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        elf64Relas = Array((section64Header.sh_size / section64Header.sh_entsize).toInt()) {
            Elf64_Rela(buffer)
        }
    }

    inner class Elf64_Rela(byteBuffer: ByteBuffer) {

        // This member gives the location at which to apply the relocation action. For a relocatable file,
        // the value is the byte offset from the beginning of the section to the storage unit affected by the relocation. For an executable file or a shared object, the value is the virtual address of the storage unit affected by the relocation.
        val r_offset: Long

        // This member gives both the symbol table index with respect to which the relocation must be made,
        // and the type of relocation to apply. For example, a call instruction's relocation entry would hold the symbol table index of the function being called.
        // If the index is STN_UNDEF, the undefined symbol index, the relocation uses 0 as the ``symbol value''.
        // Relocation types are processor-specific; descriptions of their behavior appear in the processor supplement. When the text below refers to a relocation entry's relocation type or symbol table index,
        // it means the result of applying ELF32_R_TYPE (or ELF64_R_TYPE) or ELF32_R_SYM (or ELF64_R_SYM), respectively, to the entry's r_info member.
        val r_info: Long

        //This member specifies a constant addend used to compute the value to be stored into the relocatable field.
        val r_addend: Long

        init {
            r_offset = byteBuffer.long
            r_info = byteBuffer.long
            r_addend = byteBuffer.long
        }

        /*
        *   #define ELF64_R_SYM(i)    ((i)>>32)
            #define ELF64_R_TYPE(i)   ((i)&0xffffffffL)
            #define ELF64_R_INFO(s,t) (((s)<<32)+((t)&0xffffffffL))
        * */
        override fun toString(): String {
            val sym = r_info shr 32
            val type = r_info and 0xffffffffL
            return """Elf64_Rela(
             r_offset $r_offset
             r_info $r_info
             sym ${sym} type ${type}
             r_addend $r_addend)
            """
        }
    }


    override fun toString(): String {
        return """${name} RelaSectionTable (
         ${section64Header}
         ${elfFile.sectionTables[section64Header.sh_link]}
         elf64Relas = 
         ${elf64Relas.toPrint(H.h1)} )
        """
    }
}