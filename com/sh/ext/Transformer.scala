package com.sh.ext

import java.awt.Color
import java.awt.image.BufferedImage

import com.sh.{ext, Image}

/**
 * Created by Huskyto on 19/09/2015.
 */
class Transformer(val sourcePalette : Palette, val objPalette : Palette) {

    def transformColor(color : Color): Color = {
        val index = sourcePalette.getIndexOf(color)
        if (index > -1 && index < objPalette.colors.length) objPalette.colors(index)
        else color
    }

    def transformColors(colors : Array[Color]): Array[Color] =
        colors.map(transformColor)

    def transformImage(image : Image): Image = {
        val rgbArray = image.data.getRGB(0, 0, image.data.getWidth, image.data.getHeight, null, 0, image.data.getWidth)
        val gsArray = transformColors(rgbArray.map(new Color(_, true)))
        val intArray = gsArray.map(_.getRGB)

        val buffered  = new BufferedImage(image.data.getWidth, image.data.getHeight, BufferedImage.TYPE_INT_ARGB)

        buffered.setRGB(0, 0, image.data.getWidth, image.data.getHeight, intArray.toArray, 0, image.data.getWidth)
        new Image(buffered, image.uri)
    }
}

object Transformers {

    def apply(toPal : Palette): Transformer = new Transformer(Palettes.iron, toPal)

    val toIron = Transformers(Palettes.iron)
    val toReinIron = Transformers(Palettes.rein_iron)
    val toReinIronII = Transformers(Palettes.rein_iron_ii)
    val toCastIron = Transformers(Palettes.cast_iron)
    val toGold = Transformers(Palettes.gold)
    val toStone = Transformers(Palettes.stone)
    // glass
    val toQuartz = Transformers(Palettes.quartz)
    val toDiamond = Transformers(Palettes.diamond)
    val toWood = Transformers(Palettes.wood)
    val toHardFibre = Transformers(Palettes.hard_fibre)

    val all = List(toIron, toReinIron, toReinIronII, toCastIron, toGold,
        toStone, toQuartz, toDiamond, toWood, toHardFibre)

    val metals = List(toIron, toReinIron, toReinIronII, toCastIron, toGold)

}