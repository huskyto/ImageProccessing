package com.sh.processes

import java.awt.Color
import java.awt.image.BufferedImage

import com.sh.Image

/**
 * Created by Huskyto on 17/04/2015.
 */
object Resize {         // todo also include re samples

    def quarterSize(image: Image): Image = {

        def resamplePixel(x: Int, y: Int): Int = {              // todo how could add the use of filters?
            def average(f: Int => Int)(nums: (Int, Int, Int, Int)) = (f(nums._1) + f(nums._2) + f(nums._3) + f(nums._4)) / 4
            val colors = (image.data.getRGB(x, y), image.data.getRGB(x + 1, y), image.data.getRGB(x, y + 1), image.data.getRGB(x + 1, y + 1))

            val r = average(new Color(_).getRed)(colors)
            val g = average(new Color(_).getGreen)(colors)
            val b = average(new Color(_).getBlue)(colors)

            new Color(r, g, b).getRGB
        }

        val buffered  = new BufferedImage(image.data.getWidth / 2, image.data.getHeight / 2, image.data.getType)
        for {
            i <- 0 until buffered.getWidth
            j <- 0 until buffered.getHeight
        } {
            buffered.setRGB(i, j, resamplePixel(i * 2, j * 2))
            //buffered.setRGB(i, j, image.data.getRGB(i * 2, j * 2))
        }

        new Image(buffered, image.uri)
    }
}
