package elf.section.symtab

enum class SymbolBinding(val value: Int) {

    STB_LOCAL(0),
    STB_GLOBAL(1),
    STB_WEAK(2),
    STB_LOOS(10),
    STB_HIOS(12),
    STB_LOPROC(13),
    STB_HIPROC(15);

    companion object {
        fun getValueTypeBy(value: Int) = SymbolBinding.values().find { it.value == value }!!
    }

}