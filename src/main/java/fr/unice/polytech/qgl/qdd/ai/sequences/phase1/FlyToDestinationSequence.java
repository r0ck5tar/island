package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by danial on 12/13/2015.
 */
public class FlyToDestinationSequence extends Sequence {
    private Tile destinationTile;

    public FlyToDestinationSequence(Navigator nav, CheckList checkList, Tile destinationTile) {
        super(nav, checkList);
        this.destinationTile = destinationTile;
    }

    @Override
    public Action execute() {
        switch (nav.finder().relativeDirectionOfTile(destinationTile)) {
            case FRONT: return fly();
            case RIGHT: return heading(nav.right());
            case LEFT: return heading(nav.left());
            case BACK: return chooseTurningDirection();
        }
        return fly();
    }

    @Override
    public boolean completed() {
        return destinationReached();
    }

    private boolean destinationReached(){
        if (destinationTile.equals(nav.map().currentTile())
                || (neighbouringDestinationReached() && destinationOutOfReach()) ){
            destinationTile = null;
            return true;
        }
        return false;
    }

    private boolean neighbouringDestinationReached() {
        return nav.finder().getSurroundingTiles(destinationTile).contains(nav.map().currentTile());
    }

    private boolean destinationOutOfReach(){
        return true;
    }
}
