package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class ScanSequence extends Sequence {
    private int counter;

    public ScanSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        //TODO don't call adjacentTile, instead call adjacentTileByAir.
        if(isUnscannedGround(nav.map().currentTile())) { return scan(); }
        if(isUnscannedGround(nav.finder().adjacentTile(Direction.FRONT))) { return fly(); }
        if(isUnscannedGround(nav.finder().adjacentTile(Direction.RIGHT))) { return heading(nav.right()); }
        if(isUnscannedGround(nav.finder().adjacentTile(Direction.LEFT))) { return  heading(nav.left()); }
        else{ return scanNearbyUnscannedGround(); }
    }

    @Override
    public boolean completed() {
        return !nav.map().currentTile().isUnscanned() || counter > 20;
    }

    private boolean isUnscannedGround(Tile tile) {
        return !tile.isSea() && tile.isUnscanned();
    }

    private Action scanNearbyUnscannedGround() {
        for(Tile t: nav.finder().neighbouringTiles()) {
            if(isUnscannedGround(t)) { return new FlyToDestinationSequence(nav, checkList, t).execute(); }
        }

        return new FlyToRandomNearbyTileSequence(nav, checkList).execute();
    }
}
