package com.forhopssake.pitchright.context;

/**
 * Created by richie on 21/07/2015.
 */
public class PitchContext {
    private Wort wort;
    private Starter starter;
    private Yeast yeast;

    public PitchContext() {
        wort = new Wort();
        starter = new Starter();
        yeast = new Yeast();
    }

    public Wort getWort() {
        return wort;
    }

    public void setWort(Wort wort) {
        this.wort = wort;
    }

    public Starter getStarter() {
        return starter;
    }

    public void setStarter(Starter starter) {
        this.starter = starter;
    }

    public Yeast getYeast() {
        return yeast;
    }

    public void setYeast(Yeast yeast) {
        this.yeast = yeast;
    }
}
