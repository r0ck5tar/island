package fr.unice.polytech.qgl.qdd.ai.sequences.phase2;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class ExploreSequence extends MoveSequence {
    private int counter;
    private static final int MAX_ITERATIONS = 8;

    public ExploreSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, null);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        if(isExplorable(nav.map().currentTile())) {
            destinationTile = null;
            return explore(); }

        if(destinationTile == null) {
            for(Tile t: nav.finder().neighbouringTiles()) {
                if(isExplorable(t)) { destinationTile = t; }
            }
        }
        if (destinationTile == null) {
            destinationTile = nav.finder().getRandomNearbyTile(15);
        }

        return super.execute();
    }

    @Override
    public boolean completed() {
        return !isExplorable(nav.map().currentTile()) || counter>8;
    }

    public boolean isExplorable(Tile tile) {
        return !tile.isSea() && !tile.isExplored() && !tile.isExploited();
    }
}
