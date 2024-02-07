package elf.section.header

enum class SpecialSectionIndexes(val value: UShort) {
    /*
    * This value marks an undefined, missing, irrelevant, or otherwise meaningless section reference. For example, a symbol ``defined'' relative to section number SHN_UNDEF is an undefined symbol.
    *  Although index 0 is reserved as the undefined value, the section header table contains an entry for index 0. If the e_shnum member of the ELF header says a file has 6 entries in the section header table, they have the indexes 0 through 5. The contents of the initial entry are specified later in this section.
    * */
    SHN_UNDEF(0u),
    // This value specifies the lower bound of the range of reserved indexes.
    SHN_LORESERVE(0xff00u),

    // Values in this inclusive range are reserved for processor-specific semantics.
    SHN_LOPROC(0xff00u),
    SHN_HIPROC(0xff1fu),


    // Values in this inclusive range are reserved for operating system-specific semantics.
    SHN_LOOS(0xff20u),
    SHN_HIOS(0xff3fu),

    // This value specifies absolute values for the corresponding reference. For example, symbols defined relative to section number SHN_ABS have absolute values and are not affected by relocation.
    SHN_ABS(0xfff1u),
    // Symbols defined relative to this section are common symbols, such as FORTRAN COMMON or unallocated C external variables.
    SHN_COMMON(0xfff2u),

    //  This value is an escape value. It indicates that the actual section header index is too large to fit in the containing field and is to be found in another location (specific to the structure where it appears).
    SHN_XINDEX(0xffffu),

    // This value specifies the upper bound of the range of reserved indexes. The system reserves indexes between SHN_LORESERVE and SHN_HIRESERVE, inclusive; the values do not reference the section header table. The section header table does not contain entries for the reserved indexes.
    SHN_HIRESERVE(0xffffu);

    companion object {
        fun getValueTypeBy(value: UShort) = SpecialSectionIndexes.values().find { it.value == value }
    }
}