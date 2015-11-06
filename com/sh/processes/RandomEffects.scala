package com.sh.processes

import java.awt.Color
import java.awt.image.BufferedImage

import com.sh.Image

/**
 * Created by Huskyto on 18/04/2015.
 */
object RandomEffects {

    val MAX_SOFT = 0

    def decompose(image: Image): Image = {
        val quarterImage = Resize.quarterSize(image)
        val redArray = ColorFilters.getRed(quarterImage).data.getRGB(0, 0, quarterImage.data.getWidth, quarterImage.data.getHeight, null, 0, quarterImage.data.getWidth)
        val greenArray = ColorFilters.getGreen(quarterImage).data.getRGB(0, 0, quarterImage.data.getWidth, quarterImage.data.getHeight, null, 0, quarterImage.data.getWidth)
        val blueArray = ColorFilters.getBlue(quarterImage).data.getRGB(0, 0, quarterImage.data.getWidth, quarterImage.data.getHeight, null, 0, quarterImage.data.getWidth)
        val bwArray = ColorFilters.grayScale(quarterImage).data.getRGB(0, 0, quarterImage.data.getWidth, quarterImage.data.getHeight, null, 0, quarterImage.data.getWidth)

        val outBuffer =  new BufferedImage(image.data.getWidth, image.data.getHeight, image.data.getType)

        val (w, h) = (quarterImage.data.getWidth, quarterImage.data.getHeight)
        outBuffer.setRGB(0, 0, w, h, redArray, 0, w)
        outBuffer.setRGB(w, 0, w, h, greenArray, 0, w)
        outBuffer.setRGB(0, h, w, h, blueArray, 0, w)
        outBuffer.setRGB(w, h, w, h, bwArray, 0, w)

        new Image(outBuffer, image.uri)
    }

    def movementExposure(image: Image, length: Int, minPercent: Float): Image = {
        def sqr(int: Int) = int * int
        def getBrightness(color: Color) =
            Math.sqrt(0.299 * sqr(color.getRed) +.587 * sqr(color.getGreen) + 0.114 * sqr(color.getBlue)) / 255

        val cm = image.data.getColorModel
        val alphaPre = cm.isAlphaPremultiplied
        val raster = image.data.copyData(null)
        val outBuffer = new BufferedImage(cm, raster, alphaPre, null)

        def slide(x: Int, y: Int, bright: Double): Unit = {
            for {
                i <- x until x + length
                if i < image.data.getWidth
            } {
                val localColor = new Color(image.data.getRGB(i, y))
                val localBright = getBrightness(localColor)
                val movingColor = new Color(image.data.getRGB(x, y))
                if (bright >= localBright) {
                    val distSoft = ((1 - ((i.toFloat - x.toFloat) / length.toFloat)) * (1 - MAX_SOFT)) + MAX_SOFT
                    // this is most likely wrong...
                    var blue = localColor.getBlue
                    if (movingColor.getBlue > blue)
                        blue = (blue + (movingColor.getBlue - blue) * distSoft).toInt
                    var red = localColor.getRed
                    if (movingColor.getRed > red)
                        red = (red + (movingColor.getRed - red) * distSoft).toInt
                    var green = localColor.getGreen
                    if (movingColor.getGreen > green)
                        green = (green + (movingColor.getGreen - green) * distSoft).toInt
                    outBuffer.setRGB(i, y, new Color(red, green, blue).getRGB)
                }
            }
        }

        for {
            x <- 0 until image.data.getWidth
            y <- 0 until image.data.getHeight
        } {
            val bright = getBrightness(new Color(image.data.getRGB(x, y)))
            if (bright >= minPercent) slide(x, y, bright)
        }

        new Image(outBuffer, image.uri)
    }
}
