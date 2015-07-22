package com.forhopssake.pitchright.context;

import java.lang.annotation.Target;

/**
 * Created by richie on 21/07/2015.
 */
public enum TargetType {
    ALE("Ale",0.75),LAGER("Lager",1.5),HYBRID("Hybrid",1.0),CUSTOM("Custom",0);

    String name;
    double value;
    TargetType(String name, double val) {
        this.name = name;
        this.value = val;
    }


    public double getValue() {
        return value;
    }
}
