package com.forhopssake.pitchright.util;

import java.text.ParseException;
import java.util.Date;

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

    public static int calculateYeastViability(Date productionDate) {


        Date now = new Date();

        double noDays = daysBetween(productionDate, now);
        double base = 97;  //base viability
        for (int a = 0; a < Math.floor(noDays / 30); a++) {
            base = base * .8;
        }

        double remain = (noDays % 30) / 30 * 20 * .01;

        base = base * (1 - remain);

        if (base > 97) {
            base = 97;
        }

        long rounded = Math.round(base);
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

    public static double daysBetween(Date d1, Date d2) {
        double days = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
        return days;
    }
}
