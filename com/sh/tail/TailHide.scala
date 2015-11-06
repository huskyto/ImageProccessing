package com.sh.tail

import java.awt.image.BufferedImage
import java.nio.file.{Paths, Files}

import com.sh.Image

import scala.reflect.io.File

/**
 * Created by Huskyto on 27/08/2015.
 */
object TailHide {

    def spaceToHide(image: Image): Long =
        (image.data.getWidth * image.data.getHeight)

    def hide(image: Image, filePath: String): Image = {
        val path = Paths.get(filePath)
        val fileBytes = Files.readAllBytes(path)
        val header = Header(path.getFileName.toString, fileBytes.length)
        val headerArr = header.getHeader.getBytes

        val rgbArray = image.data.getRGB(0, 0, image.data.getWidth, image.data.getHeight, null, 0, image.data.getWidth)
        for (i <- 0 until headerArr.length)
            rgbArray(i) = encodeByte(rgbArray(i), headerArr(i))

        val pad = headerArr.length
        for (i <- 0 until fileBytes.length)
            rgbArray(i + pad) = encodeByte(rgbArray(i + pad), fileBytes(i))

        val buffered  = new BufferedImage(image.data.getWidth, image.data.getHeight, BufferedImage.TYPE_INT_ARGB)
        buffered.setRGB(0, 0, image.data.getWidth, image.data.getHeight, rgbArray, 0, image.data.getWidth)
        new Image(buffered, image.uri)
    }

    def recover(image: Image) = {
        val rgbArray = image.data.getRGB(0, 0, image.data.getWidth, image.data.getHeight, null, 0, image.data.getWidth)
        val dataArray = new Array[Byte](rgbArray.length)

        for (i <- 0 until rgbArray.length)
            dataArray(i) = decodeByte(rgbArray(i)).toByte

        val header = Header(dataArray)
        val path = Paths.get(Paths.get(image.uri).getParent.toString + "/" + header.name + " - out.png")
        Files.write(path, dataArray.slice(header.totalSize, header.totalSize + header.size))
    }


    // helpers //

    def encodeByte(color: Int, byte: Byte) = {
        val masks = 0xFCFCFCFC
        var codedValue = color & masks

        codedValue += (byte & 0xC0) << (24 - 6)
        codedValue += (byte & 0x30) << (16 - 4)
        codedValue += (byte & 0x0C) << (8  - 2)
        codedValue += byte & 0x03
        codedValue
    }

    def decodeByte(coded: Int): Int = {
        def getMoved(value: Int, spaces: Int) =
            (value & (0x03 << spaces)) >> spaces

        (getMoved(coded, 24) << 6) +
                (getMoved(coded, 16) << 4)  +
                (getMoved(coded, 8) << 2) +
                getMoved(coded, 0)
    }

}
