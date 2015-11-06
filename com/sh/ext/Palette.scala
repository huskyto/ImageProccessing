package com.sh.ext

import java.awt.Color

/**
 * Created by Huskyto on 19/09/2015.
 */
class Palette(val colors : List[Color], val name : String) {

    def getIndexOf(color : Color): Int = {
        for (i <- 0 until colors.length)
            if (colors(i).getRed == color.getRed &&
                colors(i).getBlue == color.getBlue &&
                colors(i).getGreen == color.getBlue &&
                colors(i).getAlpha == color.getAlpha) return i
         -1
    }
}

object Palettes {

    val iron = new Palette(List(new Color(68, 68, 68),
        new Color(90, 90, 90), new Color(114, 114, 114),
        new Color(150, 150, 150), new Color(216, 216, 216)), "iron")

    val rein_iron = new Palette(List(Color(52),
        Color(69), Color(87),
        Color(125), Color(207)), "rein_iron")

    val rein_iron_ii = new Palette(List(Color(36),
        Color(48), Color(61),
        Color(101), Color(198)), "rein_iron_ii")

    val cast_iron = new Palette(List(Color(19),
        Color(26), Color(33),
        Color(54), Color(152)), "cast_iron")

    val gold = new Palette(List(new Color(80, 80, 0),
        new Color(220, 118, 19), new Color(222, 222, 0),
        new Color(255, 255, 11), new Color(255, 255, 255)), "gold")

    val stone = new Palette(List(new Color(80, 80, 80),
        Color(91), new Color(102, 102, 102),
        new Color(127, 127, 127), new Color(216, 216, 216)), "stone")

    val glass = null

    val quartz = new Palette(List(new Color(93, 74, 63),
        new Color(115, 98, 89), new Color(137, 123, 115),
        new Color(219, 204, 191), new Color(240, 237, 233)), "quartz")

    val diamond = new Palette(List(new Color(14, 63, 54),
        new Color(27, 123, 107), new Color(44, 205, 177),
        new Color(140, 244, 226), new Color(209, 250, 243)), "diamond")

    val wood = new Palette(List(new Color(61, 48, 29),
        new Color(83, 66, 40), new Color(106, 85, 52),
        new Color(155, 123, 76), new Color(180, 144, 90)), "wood")

    val hard_fibre = new Palette(List(new Color(26, 31, 13),
        new Color(65, 65, 80), new Color(85, 87, 98),
        new Color(80, 92, 40), new Color(114, 117, 134)), "hard_fibre")
}

object Color {
    def apply(i : Int): Color = new Color(i, i, i)
}