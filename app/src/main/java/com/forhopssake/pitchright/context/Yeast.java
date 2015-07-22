package com.forhopssake.pitchright.context;

import java.util.Date;

/**
 * Created by richie on 21/07/2015.
 */
public class Yeast {
    private int intialCellCount;
    private Date productionDate;
    private int viability;
    private int viableCellCount;

    public Yeast() {
        productionDate = new Date();
    }

    public Yeast(int intialCellCount, int viability, int viableCellCount) {
        super();
        this.intialCellCount = intialCellCount;
        this.viability = viability;
        this.viableCellCount = viableCellCount;
    }

    public int getIntialCellCount() {
        return intialCellCount;
    }

    public void setIntialCellCount(int intialCellCount) {
        this.intialCellCount = intialCellCount;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getViability() {
        return viability;
    }

    public void setViability(int viability) {
        this.viability = viability;
    }

    public int getViableCellCount() {
        return viableCellCount;
    }

    public void setViableCellCount(int viableCellCount) {
        this.viableCellCount = viableCellCount;
    }
}
