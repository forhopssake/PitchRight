package com.forhopssake.pitchright.util;

/**
 * Created by richie on 22/07/2015.
 */
public class GravityConverter {

    public static double convertOGToPlato(double og) {

        double plato = (135.997 * Math.pow(og, 3)) - (630.272 * Math.pow(og, 2)) + (1111.14 * og - 616.868);
        return plato;
    }
}
