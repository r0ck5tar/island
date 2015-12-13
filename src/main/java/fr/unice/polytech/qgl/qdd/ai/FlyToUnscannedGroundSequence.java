package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by Hakim on 12/13/2015.
 */
public class FlyToUnscannedGroundSequence extends FlyToDestinationSequence {
    public FlyToUnscannedGroundSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, nav.getRandomUnscannedGroundTile());
    }

    @Override
    public boolean completed() {
        return checkList.isAboveGround() || super.completed() ;
    }
}
