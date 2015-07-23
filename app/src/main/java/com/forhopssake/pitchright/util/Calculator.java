package com.forhopssake.pitchright.util;

import java.text.ParseException;

/**
 * Created by richie on 22/07/2015.
 */
public class Calculator {

    public static int calculateYeastRequired(double target, double batch, double gravity, double overBuild, boolean isGallons) {
        double plato = GravityConverter.convertOGToPlato(gravity);
        if (isGallons) {
            batch = batch * 3.78541;
        }
        Double result = (target * batch * plato) + overBuild;
        long rounded = Math.round(result);
        int intResult = (int) rounded;
        return intResult;
    }

    public static double getDouble(String s) {
        try {
            double d = Double.parseDouble(s);
            return (d < 0 ? 0 : d);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
