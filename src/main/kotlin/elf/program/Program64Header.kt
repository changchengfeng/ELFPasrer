package elf.program

import elf.ElfFile
import java.nio.ByteBuffer

class Program64Header(val elfFile: ElfFile, val byteBuffer: ByteBuffer) {

    /*
       PT_NULL(0x00000000),   //  Program header table entry unused.
       PT_LOAD(0x00000001),   //  Loadable segment.
       PT_DYNAMIC(0x00000002),  // Dynamic linking information.
       PT_INTERP(0x00000003), // Interpreter information.
       PT_NOTE(0x00000004),   // Auxiliary information.
       PT_SHLIB(0x00000005),//  Reserved.
       PT_PHDR(0x00000006),   // Segment containing program header table itself.
       PT_TLS(0x00000007),  // Thread-Local Storage template.
       PT_LOOS(0x60000000),   // Reserved inclusive range. Operating system specific.
       PT_HIOS(0x6FFFFFFF),    // Reserved inclusive range. Operating system specific.
       PT_LOPROC(0x70000000), // Reserved inclusive range. Processor specific.
       PT_HIPROC(0x7FFFFFFF); // Reserved inclusive range. Processor specific.
    * */
    val p_type: ProgramTypes // Identifies the type of the segment.

    /*
    * 0x1	PF_X	Executable segment.
      0x2	PF_W	Writeable segment.
      0x4	PF_R	Readable segment.
    * */
    val p_flags64: Int // Segment-dependent flags (position for 64-bit structure).
    val p_offset: Long // Offset of the segment in the file image.
    val p_vaddr: Long  // Virtual address of the segment in memory.
    val p_paddr: Long  // On systems where physical address is relevant, reserved for segment's physical address.
    val p_filesz: Long // Size in bytes of the segment in the file image. May be 0.
    val p_memsz: Long   // Size in bytes of the segment in memory. May be 0.
    val p_flags32: Int  // Segment-dependent flags (position for 32-bit structure).

    /*  0 and 1 specify no alignment.
        Otherwise should be a positive, integral power of 2,
        with p_vaddr equating p_offset modulus p_align.
     */
    val p_align: Long

    init {
        p_type = ProgramTypes[byteBuffer.int]
        p_flags64 = byteBuffer.int
        p_offset = byteBuffer.long
        p_vaddr = byteBuffer.long
        p_paddr = byteBuffer.long
        p_filesz = byteBuffer.long
        p_memsz = byteBuffer.long
        p_flags32 = byteBuffer.int
        p_align = byteBuffer.long
    }

    override fun toString(): String {
        return """Program64Header(
        p_type $p_type
        p_flags64 ${p_flags64} ${ProgramFlags.getStringBy(p_flags64)}
        p_offset $p_offset
        p_vaddr $p_vaddr
        p_paddr $p_paddr
        p_filesz $p_filesz
        p_memsz $p_memsz
        p_flags32 ${p_flags32}
        p_align ${p_align})
        section ${getSection()}
        """
    }

    protected fun getSection(): String {
        val stringBuilder = StringBuilder()
        for (sectionTable in elfFile.sectionTables) {
            val section64Header = sectionTable.section64Header
            if ((p_offset <= section64Header.sh_offset)
                && (p_offset + p_filesz >= section64Header.sh_offset +
                        section64Header.sh_size)) {
                stringBuilder.append(sectionTable.name)
                stringBuilder.append("  ")
            }
        }
        return stringBuilder.toString()
    }
}

