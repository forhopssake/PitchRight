package com.forhopssake.pitchright.context;

import java.util.Properties;

/**
 * Created by richie on 21/07/2015.
 */
public class PitchContextFactory {
    public static final String FLASK_SIZE="flaskSize";
    public static final String STARTER_SG ="startingGravity";
    public static final String TARGET_PR = "targetPitchRate";
    public static final String VOLJUME = "batchVolume";
    public static final String UNITS = "units";
    public static final String OG =        "OriginalGravity";
    public static final String CELL_OVERBUILD = "cellOverbuild";
    public static final String INITIAL_CELL_COUNT = "intialCellCount";
    Properties properties;
    public PitchContextFactory(Properties properties) {
        this.properties = properties;
    }

    public PitchContext createContext() {
        PitchContext context = new PitchContext();
        populateStarter(context.getStarter());
        populateYeast(context.getYeast());
        populateWort(context.getWort());
        return context;
    }

    private void populateStarter(Starter starter) {
        String flaskSizeStr = properties.getProperty(FLASK_SIZE);
        double flaskSize  = Double.parseDouble(flaskSizeStr);
        starter.setFlaskSize(flaskSize);
        String starterSGStr = properties.getProperty(STARTER_SG);
        double starterSG = Double.parseDouble(starterSGStr);
        starter.setStartingGravity(starterSG);
    }

    private void populateYeast(Yeast yeast) {
        String initialCCStr = properties.getProperty(INITIAL_CELL_COUNT);
        int initalCC = Integer.parseInt(initialCCStr);
        yeast.setIntialCellCount(initalCC);
    }

    private void populateWort(Wort wort) {
        String pitchRateStr  = properties.getProperty(TARGET_PR);
        double pitchRate = Double.parseDouble(pitchRateStr);
        wort.setTargetPitchRate(pitchRate);
        String batchVolumeStr = properties.getProperty(VOLJUME);
        double batchVolume = Double.parseDouble(batchVolumeStr);
        wort.setBatchVolume(batchVolume);
        String OGStr = properties.getProperty(OG);
        double og = Double.parseDouble(OGStr);
        wort.setOriginalGravity(og);
        String unitStr = properties.getProperty(UNITS);
        VolumeUnits units = VolumeUnits.valueOf(unitStr);
        wort.setUnits(units);
        String cellCountStr = properties.getProperty(CELL_OVERBUILD);
        int cellCount = Integer.parseInt(cellCountStr);
        wort.setCellOverbuild(cellCount);
    }
}
