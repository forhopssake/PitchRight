package com.forhopssake.pitchright.context;

/**
 * Created by richie on 24/07/2015.
 */
public class StarterStep {
    private AerationMethod aerationMethod;
    private double starterVolume;
    private double inoculationRate;
    private int cellsCreated;
    private int totalCells;
    private double growthFactor;

    public AerationMethod getAerationMethod() {
        return aerationMethod;
    }

    public void setAerationMethod(AerationMethod aerationMethod) {
        this.aerationMethod = aerationMethod;
    }

    public double getStarterVolume() {
        return starterVolume;
    }

    public void setStarterVolume(double starterVolume) {
        this.starterVolume = starterVolume;
    }

    public double getInoculationRate() {
        return inoculationRate;
    }

    public void setInoculationRate(double inoculationRate) {
        this.inoculationRate = inoculationRate;
    }

    public int getCellsCreated() {
        return cellsCreated;
    }

    public void setCellsCreated(int cellsCreated) {
        this.cellsCreated = cellsCreated;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public void setTotalCells(int totalCells) {
        this.totalCells = totalCells;
    }

    public double getGrowthFactor() {
        return growthFactor;
    }

    public void setGrowthFactor(double growthFactor) {
        this.growthFactor = growthFactor;
    }
}
