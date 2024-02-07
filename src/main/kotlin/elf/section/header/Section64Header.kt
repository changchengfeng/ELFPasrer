package elf.section.header

import java.nio.ByteBuffer

class Section64Header(byteBuffer: ByteBuffer) {
    /*
    * An offset to a string in the .shstrtab section that represents the name of this section.
    * */
    val sh_name: Int

    /*Identifies the type of this header.*/
    /*
    SHT_NULL(0x0),   // Section header table entry unused
    SHT_PROGBITS(0x1),   // Program data
    SHT_SYMTAB(0x2),   // Symbol table
    SHT_STRTAB(0x3),   // String table
    SHT_RELA(0x4),   // Relocation entries with addends
    SHT_HASH(0x5),   // Symbol hash table
    SHT_DYNAMIC(0x6),    // Dynamic linking information
    SHT_NOTE(0x7),       // Notes
    SHT_NOBITS(0x8),    // Program space with no data (bss)
    SHT_REL(0x9),    // Relocation entries, no addends
    SHT_SHLIB(0x0A),   //  Reserved
    SHT_DYNSYM(0x0B),  //  Dynamic linker symbol table
    SHT_INIT_ARRAY(0x0E),  //  Array of constructors
    SHT_FINI_ARRAY(0x0F),   //  Array of destructors
    SHT_PREINIT_ARRAY(0x10),  //  Array of pre-constructors
    SHT_GROUP(0x11),   //  Section group
    SHT_SYMTAB_SHNDX(0x12),   //  Extended section indices
    SHT_NUM(0x13),  //Number of defined types.
    SHT_LOOS(0x60000000); // Start OS-specific.
    */
    val sh_type: SectionTypes

    /*Identifies the attributes of the section.*/
    /*
    SHF_WRITE(0x1),  // Writable
    SHF_ALLOC(0x2),  // Occupies memory during execution
    SHF_EXECINSTR(0x4),  // Executable
    SHF_MERGE(0x10),  // Might be merged
    SHF_STRINGS(0x20), // Contains null-terminated strings
    SHF_INFO_LINK(0x40),  // 'sh_info' contains SHT index
    SHF_LINK_ORDER(0x80),   // Preserve order after combining
    SHF_OS_NONCONFORMING(0x100),    // Non-standard OS specific handling required
    SHF_GROUP(0x200),  // Section is member of a group
    SHF_TLS_Section(0x400), // hold thread-local data
    SHF_MASKOS(0x0FF00000),   // OS-specific
    SHF_MASKPROC(0xF0000000), // Processor-specific
    SHF_ORDERED(0x4000000),   // 	Special ordering requirement (Solaris)
    SHF_EXCLUDE(0x8000000); // Section is excluded unless referenced or allocated (Solaris)
    *
    * */
    val sh_flags: Long

    /*Virtual address of the section in memory, for sections that are loaded.*/
    val sh_addr: Long

    /*Offset of the section in the file image.*/
    val sh_offset: Long

    /*Size in bytes of the section in the file image. May be 0.*/
    val sh_size: Long

    /*Contains the section index of an associated section.
      This field is used for several purposes, depending on the type of section.*/
    val sh_link: Int

    /*Contains extra information about the section.
      This field is used for several purposes, depending on the type of section.*/
    val sh_info: Int

    /*Contains the required alignment of the section. This field must be a power of two.*/
    val sh_addralign: Long

    /*Contains the size, in bytes, of each entry, for sections that contain fixed-size entries.
     Otherwise, this field contains zero.*/
    val sh_entsize: Long

    init {
        sh_name = byteBuffer.int
        sh_type = SectionTypes.getValueTypeBy(byteBuffer.int)
        sh_flags = byteBuffer.long
        sh_addr = byteBuffer.long
        sh_offset = byteBuffer.long
        sh_size = byteBuffer.long
        sh_link = byteBuffer.int
        sh_info = byteBuffer.int
        sh_addralign = byteBuffer.long
        sh_entsize = byteBuffer.long
    }

    override fun toString(): String {
        return """
         sh_name $sh_name
         sh_type $sh_type
         sh_flags ${SectionFlags.getStringBy(sh_flags)}
         sh_addr $sh_addr
         sh_offset $sh_offset
         sh_size $sh_size
         sh_link $sh_link
         sh_info $sh_info
         sh_addralign $sh_addralign
         sh_entsize $sh_entsize
        """
    }
}
