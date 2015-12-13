package fr.unice.polytech.qgl.qdd.ai.sequences.phase2;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class ScoutSequence extends MoveSequence {
    private int counter;
    private static final int MAX_ITERATIONS = 8;

    public ScoutSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, null);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        if(isScoutable(nav.getCurrentTile())) { destinationTile = null; return scan(); }

        if(destinationTile == null) {
            for(Tile t: nav.getNeighbouringTiles(nav.getCurrentTile())) {
                if(isScoutable(t)) { destinationTile = t; }
            }
        }

        return super.execute();
    }

    @Override
    public boolean completed() {
        return counter>8;
    }

    public boolean isScoutable(Tile tile) {
        return !tile.isSea() && tile.isUnscouted();
    }
}
