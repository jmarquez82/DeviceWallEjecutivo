package cl.rinno.newdevicewall.gridlibrery.calculator;


import java.util.List;

import cl.rinno.newdevicewall.gridlibrery.GridItem;


/**
 * Created by EasonX on 16/5/26.
 */
public interface PositionCalculator {

    void calculate(List<? extends GridItem> gridItemList);

}
