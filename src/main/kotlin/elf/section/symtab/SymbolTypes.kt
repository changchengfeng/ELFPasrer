package elf.section.symtab

enum class SymbolTypes(val value: Int) {

    STT_NOTYPE(0), // The symbol's type is not specified.

    // The symbol is associated with a data object, such as a variable, an array, and so on.
    STT_OBJECT(1),

    // The symbol is associated with a function or other executable code.
    STT_FUNC(2),

    // The symbol is associated with a section.
    // Symbol table entries of this type exist primarily for relocation and normally have STB_LOCAL binding.
    STT_SECTION(3),

    // Conventionally, the symbol's name gives the name of the source file associated with the object file.
    // A file symbol has STB_LOCAL binding, its section index is SHN_ABS,
    // and it precedes the other STB_LOCAL symbols for the file, if it is present.
    STT_FILE(4),

    // The symbol labels an uninitialized common block. See below for details.
    STT_COMMON(5),

    // The symbol specifies a Thread-Local Storage entity. When defined, it gives the assigned offset for the symbol,
    // not the actual address. Symbols of type STT_TLS can be referenced by only special thread-local storage relocations
    // and thread-local storage relocations can only reference symbols with type STT_TLS.
    // Implementation need not support thread-local storage.
    STT_TLS(6),

    // Values in this inclusive range are reserved for operating system-specific semantics.
    STT_LOOS(10),
    STT_HIOS(12),

    // Values in this inclusive range are reserved for processor-specific semantics.
    // If meanings are specified, the processor supplement explains them.
    STT_LOPROC(13),
    STT_HIPROC(15);

    companion object {
        fun getValueTypeBy(value: Int) = SymbolTypes.values().find { it.value == value }!!
    }
}