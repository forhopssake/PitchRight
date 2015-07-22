package com.forhopssake.pitchright.context;

/**
 * Created by richie on 21/07/2015.
 */
public enum VolumeUnits {
    GALLONS("GALLONS"),LITRES("LITRES");

    private String name;
    VolumeUnits(String name) {
        this.name = name;
    }

    public static VolumeUnits getVolumeUnits(String name) {
        if (name == null || !name.equals("GALLONS")){
            return LITRES;
        } else {
            return GALLONS;
        }
    }
}
