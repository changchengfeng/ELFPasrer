package elf

enum class ElfType(val value: Short) {
    /*Identifies object file type.*/

    ET_NONE(0x00), // Unknown.
    ET_REL(0x01), // Relocatable file.
    ET_EXEC(0x02), // Executable file.
    ET_DYN(0x03), // Shared object.
    ET_CORE(0x04), // Core file.
    ET_LOOS(0xFE00.toShort()), // Reserved inclusive range. Operating system specific.
    ET_HIOS(0xFEFF.toShort()),
    ET_LOPROC(0xFF00.toShort()), // Reserved inclusive range. Processor specific.
    ET_HIPROC(0xFFFF.toShort());

    companion object {
        fun getValueTypeBy(value: Short) = ElfType.values().find { it.value == value }!!
    }
}