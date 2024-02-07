package elf.section.header

// If a flag bit is set in sh_flags, the attribute is ``on'' for the section. Otherwise, the attribute is ``off'' or does not apply. Undefined attributes are set to zero.
enum class SectionFlags(val value: Long) {

    // The section contains data that should be writable during process execution.
    SHF_WRITE(0x1),

    // The section occupies memory during process execution. Some control sections do not reside in the memory image of an object file; this attribute is off for those sections.
    SHF_ALLOC(0x2),

    // The section contains executable machine instructions.
    SHF_EXECINSTR(0x4),

    /*
    * The data in the section may be merged to eliminate duplication. Unless the SHF_STRINGS flag is also set,
    * the data elements in the section are of a uniform size. The size of each element is specified in the section header's sh_entsize field.
    *  If the SHF_STRINGS flag is also set, the data elements consist of null-terminated character strings.
    *  The size of each character is specified in the section header's sh_entsize field.
    *  Each element in the section is compared against other elements in sections with the same name, type and flags.
    *  Elements that would have identical values at program run-time may be merged. Relocations referencing elements of such sections must be resolved to the merged locations of the referenced values.
    * Note that any relocatable values, including values that would result in run-time relocations, must be analyzed to determine whether the run-time values would actually be identical.
    * An ABI-conforming object file may not depend on specific elements being merged, and an ABI-conforming link editor may choose not to merge specific elements.
    * */
    SHF_MERGE(0x10),

    // The data elements in the section consist of null-terminated character strings. The size of each character is specified in the section header's sh_entsize field.
    SHF_STRINGS(0x20),

    // The sh_info field of this section header holds a section header table index.
    SHF_INFO_LINK(0x40),

    // This flag adds special ordering requirements for link editors.
    // The requirements apply if the sh_link field of this section's header references another section (the linked-to section).
    // If this section is combined with other sections in the output file, it must appear in the same relative order with respect to those sections,
    // as the linked-to section appears with respect to sections the linked-to section is combined with.
    SHF_LINK_ORDER(0x80),

    // This section requires special OS-specific processing (beyond the standard linking rules) to avoid incorrect behavior.
    // If this section has either an sh_type value or contains sh_flags bits in the OS-specific ranges for those fields,
    // and a link editor processing this section does not recognize those values, then the link editor should reject the object file containing this section with an error.
    SHF_OS_NONCONFORMING(0x100),

    // This section is a member (perhaps the only one) of a section group.
    // The section must be referenced by a section of type SHT_GROUP. The SHF_GROUP flag may be set only for sections contained in relocatable objects (objects with the ELF header e_type member set to ET_REL).
    // See below for further details.
    SHF_GROUP(0x200),

    // This section holds Thread-Local Storage, meaning that each separate execution flow has its own distinct instance of this data. Implementations need not support this flag.
    SHF_TLS(0x400),

    // All bits included in this mask are reserved for operating system-specific semantics.
    SHF_MASKOS(0x0ff00000),

    // All bits included in this mask are reserved for processor-specific semantics. If meanings are specified, the processor supplement explains them.
    SHF_MASKPROC(0xf0000000);

    companion object {
        fun getStringBy(value: Long):String{
            var str = ""
            for (entry in values()){
                if ((entry.value and value) !=0L){
                    str ="${str} ${entry.name}"
                }
            }
            return str
        }
    }
}

const val SHF_WRITE = (1 shl 0)   /* Writable */
const val SHF_ALLOC = (1 shl 1)  /* Occupies memory during execution */
const val SHF_EXECINSTR = (1 shl 2)   /* Executable */
const val SHF_MERGE = (1 shl 4)  /* Might be merged */
const val SHF_STRINGS = (1 shl 5)  /* Contains nul-terminated strings */
const val SHF_INFO_LINK = (1 shl 6)   /* `sh_info' contains SHT index */
const val SHF_LINK_ORDER = (1 shl 7)   /* Preserve order after combining */
const val SHF_OS_NONCONFORMING = (1 shl 8)   /* Non-standard OS specific handling required */
const val SHF_GROUP = (1 shl 9)   /* Section is member of a group.  */
const val SHF_TLS = (1 shl 10) /* Section hold thread-local data.  */
const val SHF_MASKOS = 0x0ff00000 /* OS-specific.  */
const val SHF_MASKPROC = 0xf0000000 /* Processor-specific */
const val SHF_ORDERED = (1 shl 30)  /* Special ordering requirement (Solaris).  */
const val SHF_EXCLUDE = (1 shl 31)  /* Section is excluded unless referenced or allocated (Solaris).*/