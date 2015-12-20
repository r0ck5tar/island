package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ExplorerLogger;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class FlyToUnscannedGroundSequence extends FlyToDestinationSequence {
    public FlyToUnscannedGroundSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, nav.finder().getNearestUnscannedGroundTile());
    }

    /*@Override
    public boolean completed() {
        return checkList.isAboveGround() || super.completed() ;
    }*/

    public Action execute() {
        ExplorerLogger.shortLog("FlyToUnscannedGroundTile: (" +
                ExplorerLogger.getX(destinationTile) +
                ", " + ExplorerLogger.getY(destinationTile) + ")");
        return super.execute();
    }
}
