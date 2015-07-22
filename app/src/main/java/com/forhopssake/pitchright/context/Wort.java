package com.forhopssake.pitchright.context;

/**
 * Created by richie on 21/07/2015.
 */
public class Wort {
    private double targetPitchRate;
    private double batchVolume;
    private VolumeUnits units;
    private double OriginalGravity;
    private int cellCount;
    private int cellsRequired;

    public double getTargetPitchRate() {
        return targetPitchRate;
    }

    public void setTargetPitchRate(double targetPitchRate) {
        this.targetPitchRate = targetPitchRate;
    }

    public double getBatchVolume() {
        return batchVolume;
    }

    public void setBatchVolume(double batchVolume) {
        this.batchVolume = batchVolume;
    }

    public VolumeUnits getUnits() {
        return units;
    }

    public void setUnits(VolumeUnits units) {
        this.units = units;
    }

    public double getOriginalGravity() {
        return OriginalGravity;
    }

    public void setOriginalGravity(double originalGravity) {
        OriginalGravity = originalGravity;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
    }

    public int getCellsRequired() {
        return cellsRequired;
    }

    public void setCellsRequired(int cellsRequired) {
        this.cellsRequired = cellsRequired;
    }
}
