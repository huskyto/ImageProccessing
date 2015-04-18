package com.sh

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by Huskyto on 17/04/2015.
 */
class Image(val bufferedImage: BufferedImage, val uri: String) {

    val format = uri.substring(uri.lastIndexOf('.') + 1)
    def saveImage(uri: String) = ImageIO.write(bufferedImage, format, new File(uri))
    def data = bufferedImage
}

object Image {

    def apply(uri: String) = {
        new Image(loadImage(uri), uri)
    }

    private def loadImage(uri: String): BufferedImage = ImageIO.read(new File(uri))
}