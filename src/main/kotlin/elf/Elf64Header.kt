package elf

import okio.ByteString.Companion.toByteString
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Elf64Header(byteBuffer: ByteBuffer) {
    /*
    *  0x7F followed by ELF(45 4c 46) in ASCII;
    *  these four bytes constitute the magic number.
    * */
    val magic: ByteArray = ByteArray(4)

    // This byte is set to either 1 or 2 to signify 32- or 64-bit format, respectively.
    val elClass: Byte

    /*
    * This byte is set to either 1 or 2 to signify little or big endianness, respectively.
    * This affects interpretation of multibyte fields starting with offset 0x10.
    * */
    val byteOrder: Byte

    /*
    * Set to 1 for the original and current version of ELF.
    * */
    val version: Byte

    /*
    * Identifies the target operating system ABI.
    *
    *   0x00	System V
    *   0x01	HP-UX
    *   0x02	NetBSD
    *   0x03	Linux
    *   0x04	GNU Hurd
    *   0x06	Solaris
    *   0x07	AIX (Monterey)
    *   0x08	IRIX
    *   0x09	FreeBSD
    *   0x0A	Tru64
    *   0x0B	Novell Modesto
    *   0x0C	OpenBSD
    *   0x0D	OpenVMS
    *   0x0E	NonStop Kernel
    *   0x0F	AROS
    *   0x10	FenixOS
    *   0x11	Nuxi CloudABI
    *   0x12	Stratus Technologies OpenVOS
    * */
    val osABI: Byte

    /*Further specifies the ABI version. Its interpretation depends on the target ABI.
      Linux kernel (after at least 2.6) has no definition of it,[6] so it is ignored for statically-linked executables.
      In that case, offset and size of EI_PAD are 8.
      glibc 2.12+ in case e_ident[EI_OSABI] == 3 treats this field as ABI version of the dynamic linker:[7]
       it defines a list of dynamic linker's features,[8]
       treats e_ident[EI_ABIVERSION] as a feature level requested by the shared object (executable or dynamic library)
       and refuses to load it if an unknown feature is requested,
       i.e. e_ident[EI_ABIVERSION] is greater than the largest known feature.[9]
       */
    val abiVersion: Byte

    // Reserved padding bytes. Currently unused. Should be filled with zeros and ignored when read.
    val padding: ByteArray = ByteArray(7)

    /*Identifies object file type.
    *   0x00	ET_NONE	Unknown.
    *   0x01	ET_REL	Relocatable file.
    *   0x02	ET_EXEC	Executable file.
    *   0x03	ET_DYN	Shared object.
    *   0x04	ET_CORE	Core file.
    *   0xFE00	ET_LOOS	Reserved inclusive range. Operating system specific.
    *   0xFEFF	ET_HIOS
    *   0xFF00	ET_LOPROC	Reserved inclusive range. Processor specific.
    *   0xFFFF	ET_HIPROC
    * */
    val e_type: ElfType

    /*Specifies target instruction set architecture. Some examples are:
    *      Value	ISA
           0x00	No specific instruction set
           0x01	AT&T WE 32100
           0x02	SPARC
           0x03	x86
           0x04	Motorola 68000 (M68k)
           0x05	Motorola 88000 (M88k)
           0x06	Intel MCU
           0x07	Intel 80860
           0x08	MIPS
           0x09	IBM System/370
           0x0A	MIPS RS3000 Little-endian
           0x0B - 0x0D	Reserved for future use
           0x0E	Hewlett-Packard PA-RISC
           0x0F	Reserved for future use
           0x13	Intel 80960
           0x14	PowerPC
           0x15	PowerPC (64-bit)
           0x16	S390, including S390x
           0x17	IBM SPU/SPC
           0x18 - 0x23	Reserved for future use
           0x24	NEC V800
           0x25	Fujitsu FR20
           0x26	TRW RH-32
           0x27	Motorola RCE
           0x28	Arm (up to Armv7/AArch32)
           0x29	Digital Alpha
           0x2A	SuperH
           0x2B	SPARC Version 9
           0x2C	Siemens TriCore embedded processor
           0x2D	Argonaut RISC Core
           0x2E	Hitachi H8/300
           0x2F	Hitachi H8/300H
           0x30	Hitachi H8S
           0x31	Hitachi H8/500
           0x32	IA-64
           0x33	Stanford MIPS-X
           0x34	Motorola ColdFire
           0x35	Motorola M68HC12
           0x36	Fujitsu MMA Multimedia Accelerator
           0x37	Siemens PCP
           0x38	Sony nCPU embedded RISC processor
           0x39	Denso NDR1 microprocessor
           0x3A	Motorola Star*Core processor
           0x3B	Toyota ME16 processor
           0x3C	STMicroelectronics ST100 processor
           0x3D	Advanced Logic Corp. TinyJ embedded processor family
           0x3E	AMD x86-64
           0x3F	Sony DSP Processor
           0x40	Digital Equipment Corp. PDP-10
           0x41	Digital Equipment Corp. PDP-11
           0x42	Siemens FX66 microcontroller
           0x43	STMicroelectronics ST9+ 8/16 bit microcontroller
           0x44	STMicroelectronics ST7 8-bit microcontroller
           0x45	Motorola MC68HC16 Microcontroller
           0x46	Motorola MC68HC11 Microcontroller
           0x47	Motorola MC68HC08 Microcontroller
           0x48	Motorola MC68HC05 Microcontroller
           0x49	Silicon Graphics SVx
           0x4A	STMicroelectronics ST19 8-bit microcontroller
           0x4B	Digital VAX
           0x4C	Axis Communications 32-bit embedded processor
           0x4D	Infineon Technologies 32-bit embedded processor
           0x4E	Element 14 64-bit DSP Processor
           0x4F	LSI Logic 16-bit DSP Processor
           0x8C	TMS320C6000 Family
           0xAF	MCST Elbrus e2k
           0xB7	Arm 64-bits (Armv8/AArch64)
           0xDC	Zilog Z80
           0xF3	RISC-V
           0xF7	Berkeley Packet Filter
           0x101	WDC 65C816
    *
    * */
    val e_machine: Short

    // 	Set to 1 for the original version of ELF.
    val e_version: Int

    /*
    * This is the memory address of the entry point from where the process starts executing.
    * This field is either 32 or 64 bits long, depending on the format defined earlier (byte 0x04).
    *  If the file doesn't have an associated entry point, then this holds zero.
    * */
    val e_entry: Long

    /*
    * Points to the start of the program header table. It usually follows the file header immediately following this one,
    *  making the offset 0x34 or 0x40 for 32- and 64-bit ELF executables, respectively.
    * */
    val e_phoff: Long

    /*
    * Points to the start of the section header table.
    * */
    val e_shoff: Long

    /*Interpretation of this field depends on the target architecture.*/
    val e_flags: Int

    /*
    * Contains the size of this header, normally 64 Bytes for 64-bit and 52 Bytes for 32-bit format.
    * */
    val e_ehsize: Short

    /*
    * Contains the size of a program header table entry.
    * */
    val e_phentsize: Short

    /*
    * Contains the number of entries in the program header table.
    * */
    val e_phnum: Short

    /*
    * Contains the size of a section header table entry.
    * */
    val e_shentsize: Short

    /*
    * Contains the number of entries in the section header table.
    * */
    val e_shnum: Short

    /*
    Contains index of the section header table entry that contains the section names.
    * */
    val e_shstrndx: Short


    init {
        byteBuffer.get(magic)
        elClass = byteBuffer.get()
        byteOrder = byteBuffer.get()
        version = byteBuffer.get()
        osABI = byteBuffer.get()
        abiVersion = byteBuffer.get()
        byteBuffer.get(padding)
        e_type = ElfType.getValueTypeBy(byteBuffer.short)
        e_machine = byteBuffer.short
        e_version = byteBuffer.int
        e_entry = byteBuffer.long
        e_phoff = byteBuffer.long
        e_shoff = byteBuffer.long


        e_flags = byteBuffer.int

        e_ehsize = byteBuffer.short

        e_phentsize = byteBuffer.short


        e_phnum = byteBuffer.short

        e_shentsize = byteBuffer.short

        e_shnum = byteBuffer.short

        e_shstrndx = byteBuffer.short

    }


    override fun toString(): String {
        return """
               magic ${magic.toByteString().utf8()}
               elClass $elClass ${if (elClass.toInt() == 1) "32 bit" else "64 bit"} 
               byteOrder $byteOrder ${if (byteOrder.toInt() == 1) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN}
               version ${version}
               osABI ${osABI}
               abiVersion ${abiVersion}
               e_type ${e_type}
               e_machine ${e_machine.toString(16)} ${if (e_machine.toInt() == 0xB7) "Arm 64-bits (Armv8/AArch64)" else "Unknown"})
               e_version ${e_version}
               e_entry 0x${e_entry.toString(16)}
               e_phoff 0x${e_phoff.toString(16)}
               e_shoff 0x${e_shoff.toString(16)}
               e_flags ${e_flags}
               e_ehsize ${e_ehsize}
               e_phentsize ${e_phentsize}
               e_phnum ${e_phnum}
               e_shentsize ${e_shentsize}
               e_shnum ${e_shnum}
               e_shstrndx ${e_shstrndx}
               """.trimIndent()
    }
}