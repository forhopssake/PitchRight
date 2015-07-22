package com.forhopssake.pitchright.context;

/**
 * Created by richie on 21/07/2015.
 */

public class Starter {
    private double flaskSize;
    private double startingGravity;

    public Starter(){}

    public Starter(double flaskSize, double startingGravity) {
        this.flaskSize = flaskSize;
        this.startingGravity = startingGravity;
    }

    public double getFlaskSize() {
        return flaskSize;
    }

    public void setFlaskSize(double flaskSize) {
        this.flaskSize = flaskSize;
    }

    public double getStartingGravity() {
        return startingGravity;
    }

    public void setStartingGravity(double startingGravity) {
        this.startingGravity = startingGravity;
    }
}
