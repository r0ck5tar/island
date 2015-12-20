package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ExplorerLogger;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.Collections;

/**
 * Created by danial on 12/13/2015.
 */
//TODO: improve this sequence
public class FlyToDestinationSequence extends Sequence {
    protected Tile destinationTile;

    public FlyToDestinationSequence(Navigator nav, CheckList checkList, Tile destinationTile) {
        super(nav, checkList);
        this.destinationTile = destinationTile;
    }

    @Override
    public Action execute() {
        switch (nav.finder().relativeDirectionOfTileByAir(destinationTile)) {
            case FRONT: return fly();
            case RIGHT: return heading(nav.right());
            case LEFT: return heading(nav.left());
            //case BACK: return chooseTurningDirection(destinationTile);
        }
        return fly();
    }

    @Override
    public boolean completed() {
        return destinationReached();
    }

    private boolean destinationReached(){
        if (destinationTile == nav.map().currentTile()
                || (neighbouringDestinationReached() || destinationOutOfReach()) ){
            destinationTile = null;
            return true;
        }
        return false;
    }

    private boolean neighbouringDestinationReached() {
        return nav.finder().neighbouringTiles().contains(destinationTile);
        //return !Collections.disjoint(nav.finder().getSurroundingTiles(destinationTile), nav.finder().neighbouringTiles());
    }

    //TODO: implement properly
    private boolean destinationOutOfReach(){
        return false;
    }
}
