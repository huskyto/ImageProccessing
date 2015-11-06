import com.sh.tail.Header
val str = "[Name=stuff.jpg]"
val arr = str.getBytes
val str2 = new String(arr)
val bt = Byte.MaxValue
val bt2 = Byte.MinValue
127 * 127
"<>.[]".getBytes
val header = Header("posixsextant.jpg", 1024)
val strHead = header.getHeader
val head2 = Header(strHead.getBytes)
head2.size
head2.name
head2.totalSize
header.totalSize
//println(arr)
//println(new String(arr))
val bin1 = Integer.parseInt("00000011", 2)
val bin2 = Integer.parseInt("00001100", 2)
val bin3 = Integer.parseInt("00110000", 2)
val bin4 = Integer.parseInt("11000000", 2)
bin2 >> 2
bin3 >> 4
bin1 << 4
//////
//val byteToEncode = Integer.parseInt("00011011", 2)
/*val byteToEncode = Integer.parseInt("11111111", 2)
val originalValue = 0x7F421C0F
var codedValue = originalValue
/*val maska = 0xFC000000
val maskr = 0x00FC0000
val maskg = 0x0000FC00
val maskb = 0x000000FC*/

val masks = 0xFCFCFCFC

codedValue = codedValue & masks

codedValue += (byteToEncode & 0xC0) << (24 - 6)
codedValue += (byteToEncode & 0x30) << (16 - 4)
codedValue += (byteToEncode & 0x0C) << (8  - 2)
codedValue += byteToEncode & 0x03
codedValue
// recover byte
//val recovered = (codedValue & (0x03 << (24 - 6))) >> (24 - 6)

val recovered = (getMoved(codedValue, (24)) << 6) +
                (getMoved(codedValue, (16)) << 4)  +
                (getMoved(codedValue, (8)) << 2) +
                (getMoved(codedValue, 0))


def getMoved(value: Int, spaces: Int) =
    (value & (0x03 << spaces)) >> spaces

*/

def encodeByte(color: Int, byte: Byte) = {
    val masks = 0xFCFCFCFC
    var codedValue = color & masks

    codedValue += (byte & 0xC0) << (24 - 6)
    codedValue += (byte & 0x30) << (16 - 4)
    codedValue += (byte & 0x0C) << (8  - 2)
    codedValue += byte & 0x03
    codedValue
}

def decodeByte(coded: Int): Byte = {
    def getMoved(value: Int, spaces: Int) =
        (value & (0x03 << spaces)) >> spaces

    ((getMoved(coded, 24) << 6) +
            (getMoved(coded, 16) << 4)  +
            (getMoved(coded, 8) << 2) +
            getMoved(coded, 0)).toByte
}

val coded = encodeByte(-100, 125.toByte)
val decoded = decodeByte(coded)