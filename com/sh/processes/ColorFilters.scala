package com.sh.processes

import java.awt.Color
import java.awt.image.BufferedImage

import com.sh.Image

/**
 * Created by Huskyto on 17/04/2015.
 */
object ColorFilters {

    def applyToChannels(f: Int => Int)(image: Image): Image = {
        val rgbArray = image.data.getRGB(0, 0, image.data.getWidth, image.data.getHeight, null, 0, image.data.getWidth)
        val gsArray = rgbArray.map(rgb => f(rgb))
        val buffered  = new BufferedImage(image.data.getWidth, image.data.getHeight, image.data.getType)
        buffered.setRGB(0, 0, image.data.getWidth, image.data.getHeight, gsArray.toArray, 0, image.data.getWidth)
        new Image(buffered, image.uri)
    }

    def grayScale(image: Image): Image = {
        def pixelGrayScale(rgb: Int): Int = {                //todo use real percentage values. Use bit operations
            val color = new Color(rgb)
            val average = (color.getRed + color.getGreen + color.getBlue) / 3
            new Color(average, average, average).getRGB
        }

        applyToChannels(pixelGrayScale)(image)
    }

    //def getRed(image: Image) = applyToChannels(_ & 0xFF0000)(image)
    def getRed(image: Image) = applyToChannels(c => new Color(new Color(c).getRed, 0, 0).getRGB)(image)
    def getGreen(image: Image) = applyToChannels(_ & 0x00FF00)(image)
    def getBlue(image: Image) = applyToChannels(_ & 0x0000FF)(image)

}
