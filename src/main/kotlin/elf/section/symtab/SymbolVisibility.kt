package elf.section.symtab

enum class SymbolVisibility(val value: Int) {

    /*
    * The visibility of symbols with the STV_DEFAULT attribute is as specified by the symbol's binding type.
    * That is, global and weak symbols are visible outside of their defining component (executable file or shared object).
    * Local symbols are hidden, as described below. Global and weak symbols are also preemptable, that is,
    * they may by preempted by definitions of the same name in another component.
    * An implementation may restrict the set of global and weak symbols that are externally visible
    * */
    STV_DEFAULT(0),

    /*
    * The meaning of this visibility attribute may be defined by processor supplements to further constrain hidden symbols.
    *  A processor supplement's definition should be such that generic tools can safely treat internal symbols as hidden.
    *  An internal symbol contained in a relocatable object must be either removed or converted to STB_LOCAL binding by
    *  the link-editor when the relocatable object is included in an executable file or shared object.
    * */
    STV_INTERNAL(1),

    /*
    *  A symbol defined in the current component is hidden if its name is not visible to other components.
    *  Such a symbol is necessarily protected. This attribute may be used to control the external interface of a component.
    *  Note that an object named by such a symbol may still be referenced from another component if its address is passed outside.
    *  A hidden symbol contained in a relocatable object must be either removed or converted to STB_LOCAL binding by
    *  the link-editor when the relocatable object is included in an executable file or shared object.
    * */
    STV_HIDDEN(2),

    /*
    * A symbol defined in the current component is protected if it is visible in other components but not preemptable,
    * meaning that any reference to such a symbol from within the defining component must be resolved to the definition in that component,
    * even if there is a definition in another component that would preempt by the default rules.
    * A symbol with STB_LOCAL binding may not have STV_PROTECTED visibility.
    * If a symbol definition with STV_PROTECTED visibility from a shared object is taken as resolving a reference from an executable or another shared object,
    * the SHN_UNDEF symbol table entry created has STV_DEFAULT visibility.
    * */
    STV_PROTECTED(3);
    /*
    * None of the visibility attributes affects resolution of symbols within an executable or shared object during link-editing
    * -- such resolution is controlled by the binding type. Once the link-editor has chosen its resolution,
    * these attributes impose two requirements, both based on the fact that references in the code being linked may have been optimized to take advantage of the attributes.
    * First, all of the non-default visibility attributes, when applied to a symbol reference, imply that a definition
    *  to satisfy that reference must be provided within the current executable or shared object.
    *   If such a symbol reference has no definition within the component being linked,
    *   then the reference must have STB_WEAK binding and is resolved to zero.
    * Second, if any reference to or definition of a name is a symbol with a non-default visibility attribute,
    *  the visibility attribute must be propagated to the resolving symbol in the linked object.
    *  If different visibility attributes are specified for distinct references to or definitions of a symbol,
    *  the most constraining visibility attribute must be propagated to the resolving symbol in the linked object.
    *  The attributes, ordered from least to most constraining, are: STV_PROTECTED, STV_HIDDEN and STV_INTERNAL.
    *
    * */

    companion object {
        fun getValueTypeBy(value: Int) = SymbolVisibility.values().find { it.value == value }!!
    }
}