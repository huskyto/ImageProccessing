package com.sh

import com.sh.processes.{FocusEffects, ColorFilters}

/**
 * Created by Huskyto on 17/04/2015.
 */
object testing extends App{

    val img = Image("G:/stuff/husky.jpg")
    ColorFilters.getGreen(img).saveImage("G:/stuff/husky123.jpg")
    //FocusEffects.blur(img, 0.7f, 3).saveImage("G:/stuff/husky321.jpg")
}
