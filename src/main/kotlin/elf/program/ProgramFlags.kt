package elf.program

enum class ProgramFlags(val value: Int) {

    PF_X(0x1),//	Execute
    PF_W(0x2),//	Write
    PF_R(0x4),//	Read
    PF_MASKOS(0x0ff00000),//	Unspecified
    PF_MASKPROC(0xf0000000.toInt());//	Unspecified

    companion object {
        fun getStringBy(value: Int):String{
            var str = ""
            for (entry in ProgramFlags.values()){
                if ((entry.value and value) !=0){
                    str ="${str} ${entry.name}"
                }
            }
            return str
        }
    }
}