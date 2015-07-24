package com.forhopssake.pitchright.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richie on 21/07/2015.
 */

public class Starter {
    private double flaskSize;
    private double startingGravity;
    private List<StarterStep> starterSteps;

    public Starter(){}

    public Starter(double flaskSize, double startingGravity) {
        this.flaskSize = flaskSize;
        this.startingGravity = startingGravity;
        starterSteps = new ArrayList<StarterStep>();
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

    public List<StarterStep> getStarterSteps() {
        return starterSteps;
    }

    public void setStarterSteps(List<StarterStep> starterSteps) {
        this.starterSteps = starterSteps;
    }
}
