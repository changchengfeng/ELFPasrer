package elf.section

import elf.ElfFile
import elf.section.header.Section64Header
import elf.section.symtab.SymtabSectionTable
import okio.ByteString.Companion.toByteString
import java.nio.ByteBuffer

const val SHT_NULL = ""
const val INTERP = ".interp"
const val NOTE_ANDROID_IDENT = ".note.android.ident"
const val NOTE_GNU_BUILD_ID = ".note.gnu.build-id"
const val DYNSYM = ".dynsym"
const val GNU_VERSION = ".gnu.version"
const val GNU_VERSION_R = ".gnu.version_r"
const val GNU_HASH = ".gnu.hash"
const val DYNSTR = ".dynstr"
const val REL_TEXT = ".rel.text"
const val REL_DATA = ".rel.data"
const val RELA_TEXT = ".rela.text"
const val RELA_DATA = ".rela.data"
const val RELA_DYN = ".rela.dyn"
const val RELA_PLT = ".rela.plt"
const val GCC_EXCEPT_TABLE = ".gcc_except_table"
const val RODATA = ".rodata"
const val EH_FRAME_HDR = ".eh_frame_hdr"
const val EH_FRAME = ".eh_frame"
const val TEXT = ".text"
const val PLT = ".plt"
const val DATA_REL_RO = ".data.rel.ro"
const val FINI_ARRAY = ".fini_array"
const val INIT_ARRAY = ".init_array"
const val DYNAMIC = ".dynamic"
const val GOT = ".got"
const val GOT_PLT = ".got.plt"
const val DATA = ".data"
const val BSS = ".bss"
const val COMMENT = ".comment" // This section holds version control information.
const val SHSTRTAB = ".shstrtab"
const val STRTAB = ".strtab"
const val SYMTAB = ".symtab"

abstract class SectionTable(
    val name: String,
    val section64Header: Section64Header,
    val byteBuffer: ByteBuffer,
    val elfFile: ElfFile
) {

    var data: ByteArray = byteArrayOf()

    init {
        initData()
    }

    open fun initData() {
        val shSize = section64Header.sh_size
        val position = byteBuffer.position()
        byteBuffer.position(section64Header.sh_offset.toInt())
        data = ByteArray(shSize.toInt())
        byteBuffer.get(data)
        byteBuffer.position(position)
    }

    companion object {
        fun readSectionTable(
            stringSectionTable: StringSectionTable,
            section64Header: Section64Header,
            byteBuffer: ByteBuffer, elfFile: ElfFile
        ): SectionTable {

            val name = stringSectionTable.getIndex(section64Header.sh_name)
            println("const val $name")

            return when (name) {
                SHT_NULL -> SHT_NULL_SectionTable(name, section64Header, byteBuffer, elfFile)
                INTERP, STRTAB, DYNSTR -> StringSectionTable(name, section64Header, byteBuffer, elfFile)
                TEXT -> TextSectionTable(name, section64Header, byteBuffer, elfFile)
                BSS -> SHT_NOBITS_SectionTable(name, section64Header, byteBuffer, elfFile)
                RODATA, DATA -> StringSectionTable(name, section64Header, byteBuffer, elfFile)
                SYMTAB, DYNSYM -> SymtabSectionTable(name, section64Header, byteBuffer, elfFile)
                REL_TEXT, REL_DATA -> RelSectionTable(name, section64Header, byteBuffer, elfFile)
                RELA_TEXT, RELA_DATA, RELA_DYN, RELA_PLT -> RelaSectionTable(name, section64Header, byteBuffer, elfFile)
                else -> object : SectionTable(name, section64Header, byteBuffer, elfFile) {}
            }
        }
    }

    override fun toString(): String {
        return """SectionTable(
       name $name     
       section64Header  $section64Header    
       data ${data.toByteString().hex()}    )
        """
    }
}