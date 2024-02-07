package elf.program

enum class ProgramTypes(val value: Int) {
    PT_NULL(0x0),
    PT_LOAD(0x1),
    PT_DYNAMIC(0x2),
    PT_INERP(0x3),
    PT_NOTE(0x4),
    PT_SHLIB(0x5),
    PT_PHDR(0x6),
    PT_TLS(0x7),
    PT_NUM(0x8),
    PT_LOOS(0x60000000),
    PT_GNU_EH_FRAME(0x6474e550),
    PT_GNU_STACK(0x6474e551),
    PT_GNU_RELRO(0x6474e552),
    PT_LOSUNW(0x6ffffffa),
    PT_SUNWBSS(0x6ffffffa),
    PT_SUNWSTACK(0x6ffffffb),
    PT_HISUNW(0x6fffffff),
    PT_HIOS(0x6fffffff),
    PT_LOPROC(0x70000000),
    PT_HIPROC(0x7fffffff),

    // ARM Sections
    PT_SHT_ARM_EXIDX(0x70000001),
    PT_SHT_ARM_PREEMPTMAP(0x70000002),
    PT_SHT_ARM_ATTRIBUTES(0x70000003),
    PT_SHT_ARM_DEBUGOVERLAY(0x70000004),
    PT_SHT_ARM_OVERLAYSECTION(0x70000005);

    companion object {
        operator fun get(index: Int) = ProgramTypes.values().find { it.value == index }!!
    }
}