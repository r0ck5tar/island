package fr.unice.polytech.qgl.qdd.ai.sequences.phase2;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class MoveSequence extends Sequence {
    protected Tile destinationTile;

    public MoveSequence(Navigator nav, CheckList checkList, Tile destinationTile) {
        super(nav, checkList);
        this.destinationTile = destinationTile;
    }

    @Override
    public Action execute() {
        switch(nav.finder().relativeDirectionOfTile(destinationTile)) {
            case FRONT: return move(nav.front());
            case RIGHT: return move(nav.right());
            case LEFT: return move(nav.left());
            case BACK: return move(nav.back());
        }

        //Should not enter here.
        return stop();
    }

    @Override
    public boolean completed() {
        return destinationReached();
    }

    private boolean destinationReached(){
        if (destinationTile.equals(nav.map().currentTile())){
            destinationTile = null;
            return true;
        }
        return false;
    }
}
