package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class FlyToRandomNearbyTileSequence extends FlyToDestinationSequence {

    public FlyToRandomNearbyTileSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, nav.getRandomNearbyTile());
    }
}
