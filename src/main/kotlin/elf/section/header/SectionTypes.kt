package elf.section.header

enum class SectionTypes(val value: Int) {
    // This value marks the section header as inactive;
    // it does not have an associated section.
    // Other members of the section header have undefined values.

    SHT_NULL(0x0),     /* Inactive section header */
    SHT_PROGBITS(0x1),     /* Information defined by the program */
    SHT_SYMTAB(0x2),     /* Symbol table - not DLL */
    SHT_STRTAB(0x3),     /* String table */
    SHT_RELA(0x4),     /* Explicit addend relocations),     Elf64_Rela */
    SHT_HASH(0x5),     /* Symbol hash table */
    SHT_DYNAMIC(0x6),     /* Information for dynamic linking */
    SHT_NOTE(0x7),     /* A Note section */
    SHT_NOBITS(0x8),     /* Like SHT_PROGBITS with no data */
    SHT_REL(0x9),     /* Implicit addend relocations),     Elf64_Rel */
    SHT_SHLIB(0xA),     /* Currently unspecified semantics */
    SHT_DYNSYM(0xB),     /* Symbol table for a DLL */
    SHT_INIT_ARRAY(0xE),     /* Array of constructors */
    SHT_FINI_ARRAY(0xF),     /* Array of deconstructors */
    SHT_PREINIT_ARRAY(0x10),     /* Array of pre-constructors */
    SHT_GROUP(0x11),     /* Section group */
    SHT_SYMTAB_SHNDX(0x12),     /* Extended section indeces */
    SHT_NUM(0x13),     /* Number of defined types */

    SHT_LOOS(0x60000000),     /* Lowest OS-specific section type */
    SHT_GNU_ATTRIBUTES(0x6ffffff5),     /* Object attribuytes */
    SHT_GNU_HASH(0x6ffffff6),     /* GNU-style hash table */
    SHT_GNU_LIBLIST(0x6ffffff7),     /* Prelink library list */
    SHT_CHECKSUM(0x6ffffff8),     /* Checksum for DSO content */
    SHT_LOSUNW(0x6ffffffa),     /* Sun-specific low bound */
    SHT_SUNW_move(0x6ffffffa),     // Same thing
    SHT_SUNW_COMDAT(0x6ffffffb),
    SHT_SUNW_syminfo(0x6ffffffc),
    SHT_GNU_verdef(0x6ffffffd),     /* Version definition section */
    SHT_GNU_verdneed(0x6ffffffe),     /* Version needs section */
    SHT_GNY_versym(0x6fffffff),     /* Version symbol table */
    SHT_HISUNW(0x6fffffff),     /* Sun-specific high bound */
    SHT_HIOS(0x6fffffff),     /* Highest OS-specific section type */
    SHT_LOPROC(0x70000000),     /* Start of processor-specific section type */
    SHT_HIPROC(0x7fffffff),     /* End of processor-specific section type */
    SHT_LOUSER(0x80000000.toInt()),     /* Start of application-specific */
    SHT_HIUSER(0x8fffffff.toInt());/* Ennd of application-specific */

    companion object {
        fun getValueTypeBy(value: Int) = SectionTypes.values().find { it.value == value }!!
    }
}