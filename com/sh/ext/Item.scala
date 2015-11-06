package com.sh.ext

import com.sh.Image

import scala.reflect.io.Path

/**
 * Created by Huskyto on 19/09/2015.
 */
class Item(val name : String) {

    val sourceFolder = "G:/stuff/items/"

    var transformers: List[Transformer] = List()

    def addTransformer(trans : Transformer): Item = {
        transformers = trans :: transformers
        this
    }

    def addTransformers(trans : List[Transformer]): Item = {
        transformers = transformers ++ trans
        this
    }

    def getImage: Image = Image(sourceFolder + name + ".png")

    def getAllAndSave() = {
        val source = getImage
        val path : Path = Path(sourceFolder + name)
        if (!path.exists) path.createDirectory()
        for(trans <- transformers)
            trans.transformImage(source).saveImage(sourceFolder + name + "/" + trans.objPalette.name + ".png")
    }

}

object Items {

    val pellet_item = new Item("pellet_item").addTransformers(Transformers.all)

    val pellet = new Item("pellet").addTransformers(Transformers.all)
    val casing = new Item("casing").addTransformers(Transformers.all)
    val heavy = new Item("heavy").addTransformers(Transformers.all)
    val shell = new Item("shell").addTransformers(Transformers.all)
    val casing_item = new Item("casing_item").addTransformers(Transformers.all)

    val barrel_hive = new Item("barrel_hive").addTransformers(Transformers.all)
    val barrel_hive_item = new Item("barrel_hive_item").addTransformers(Transformers.all)

    val barrel_sawed_dual = new Item("barrel_sawed_dual").addTransformers(Transformers.all)
    val barrel_sawed_dual_item = new Item("barrel_sawed_dual_item").addTransformers(Transformers.all)

    val barrel_dual = new Item("barrel_dual").addTransformers(Transformers.all)
    val barrel_dual_item = new Item("barrel_dual_item").addTransformers(Transformers.all)

    val barrel_long = new Item("barrel_long").addTransformers(Transformers.all)
    val barrel_long_item = new Item("barrel_long_item").addTransformers(Transformers.all)

    val barrel_wide = new Item("barrel_wide").addTransformers(Transformers.all)
    val barrel_wide_item = new Item("barrel_wide_item").addTransformers(Transformers.all)

    val barrel_short = new Item("barrel_short").addTransformers(Transformers.all)
    val barrel_short_item = new Item("barrel_short_item").addTransformers(Transformers.all)

    val chamber_standard = new Item("chamber_standard").addTransformers(Transformers.all)
    val chamber_standard_item = new Item("chamber_standard_item").addTransformers(Transformers.all)

    val chamber_double = new Item("chamber_double").addTransformers(Transformers.all)
    val chamber_double_item = new Item("chamber_double_item").addTransformers(Transformers.all)

    val chamber_high_pressure = new Item("chamber_high_pressure").addTransformers(Transformers.all)
    val chamber_high_pressure_item = new Item("chamber_high_pressure_item").addTransformers(Transformers.all)

    val chamber_low_pressure = new Item("chamber_low_pressure").addTransformers(Transformers.all)
    val chamber_low_pressure_item = new Item("chamber_low_pressure_item").addTransformers(Transformers.all)

    val chamber_intricate = new Item("chamber_intricate").addTransformers(Transformers.all)
    val chamber_intricate_item = new Item("chamber_intricate_item").addTransformers(Transformers.all)


    val all: List[Item] = List(pellet_item, pellet, casing, heavy, shell, barrel_hive, barrel_hive_item,
        barrel_sawed_dual, barrel_sawed_dual_item,
        barrel_dual, barrel_dual_item,
        barrel_long, barrel_long_item,
        barrel_wide, barrel_wide_item,
        barrel_short, barrel_short_item,
        chamber_standard, chamber_standard_item,
        chamber_double, chamber_double_item,
        chamber_high_pressure, chamber_high_pressure_item,
        chamber_low_pressure, chamber_low_pressure_item,
        chamber_intricate, chamber_intricate_item
    )
}