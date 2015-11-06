package com.sh.tail

import java.util
import java.util.ArrayList
import com.sh.Image

import collection.JavaConversions._

/**
 * Created by Huskyto on 27/08/2015.
 */
class Header {

    var name: String = ""
    var size: Int = 0
    def headerSize: Int =
        (name + "," + size).getBytes().length

    def totalSize: Int =
        6 + headerSize

    def getHeader: String = {
        def sym(str: String): Byte = str.getBytes()(0)

        val list = new ArrayList[Byte]
        list.add(sym(">"))
        list.add(getByteHeaderSize()._1)
        list.add(getByteHeaderSize()._2)
        list.add(sym("<"))

        list.add(sym("["))
        list.addAll((name + "," + size).getBytes().toSeq)
        list.add(sym("]"))

        new String(toArray(list))
    }

    def toArray(list: ArrayList[Byte]): Array[Byte] = {
        val arr = new Array[Byte](list.size())
        for {i <- 0 until list.size()}
            arr(i) = list.get(i)
        arr
    }

    def getByteHeaderSize(): (Byte, Byte) = getByteHeaderSize(headerSize)

    def getByteHeaderSize(size: Int): (Byte, Byte) =
        ((size / 127).toByte, (size % 127).toByte)

    /// reconstruction ///

    def getIntHeaderSize(bytes: (Byte, Byte)): Int =
        bytes._1 * 127 + bytes._2

}

object Header {

    def apply(buffer: Array[Byte]) = {
        val header = new Header
        val headSize = header.getIntHeaderSize(buffer(1), buffer(2))
        val string = new String(buffer.slice(5, 5 + headSize))
        val split = string.split(",")
        header.name = split(0)
        header.size = split(1).toInt
        header
    }

    def apply(name: String, size: Int) = {
        val header = new Header
        header.name = name
        header.size = size
        header
    }
}

/*

>HH<[NNN(...)N,SSS(...)S]

H: Header Size
N: Name
S: Size
[]: Header limits

 */