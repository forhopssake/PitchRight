package com.forhopssake.pitchright.util;

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
}
