package com.sh.processes

import java.awt.Color
import java.awt.image.BufferedImage

import com.sh.Image

/**
 * Created by Huskyto on 17/04/2015.
 */
object FocusEffects {

    def blur(image: Image, strength: Float, range: Int): Image = {
        
        def neighborAverage(x: Int, y: Int): Color = {
            var (r, g, b, count) = (0, 0, 0, 0)
            for {
                i <- -range to range
                j <- -(range - Math.abs(i)) to (range - Math.abs(i))
                if i != 0 && j != 0
                if x + i > 0 && x + i < image.data.getWidth
                if y + j > 0 && y + j < image.data.getHeight
            } {
                val color = new Color(image.data.getRGB(x + i, y + j))
                //(r, g, b) = (r + color.getRed, g + color.getGreen, b + color.getBlue)  todo is there a way to do this?
                r = r + color.getRed
                g = g + color.getGreen
                b = b + color.getBlue
                count += 1
            }
            new Color(r / count, g / count, b / count)
        }
        
        val buffered  = new BufferedImage(image.data.getWidth, image.data.getHeight, image.data.getType)
        for {
            x <- 0 until image.data.getWidth
            y <- 0 until image.data.getHeight
        } {
            val average = neighborAverage(x, y)
            val start = new Color(image.data.getRGB(x, y))
            val end = new Color((start.getRed * (1 - strength)).toInt + (average.getRed * strength).toInt,
                                (start.getGreen * (1 - strength)).toInt + (average.getGreen * strength).toInt,
                                (start.getBlue * (1 - strength)).toInt + (average.getBlue * strength).toInt)
            buffered.setRGB(x, y, end.getRGB)
        }

        new Image(buffered, image.uri)
    }
}
