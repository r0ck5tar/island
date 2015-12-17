package fr.unice.polytech.qgl.qdd.ai.sequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ExplorerLogger;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.List;

/**
 * Created by danial on 12/13/2015.
 */
public abstract class Sequence {
    protected CheckList checkList;
    protected Navigator nav;

    public Sequence(Navigator nav, CheckList checkList){
        this.checkList = checkList;
        this.nav = nav;
    };

    public abstract Action execute();

    public abstract boolean completed();

    /*
        Action-building helper methods
     */

    protected Action echo(Compass direction){
        return new Action(Action.ECHO).addParameter("direction", direction.toString());
    }

    protected Action scan() { return new Action(Action.SCAN); }

    protected Action fly() {
        return checkList.isCloseToBoundary()?  chooseTurningDirection(): new Action(Action.FLY);
    }

    protected Action heading(Compass direction){
        return new Action(Action.HEADING).addParameter("direction", direction.toString());
    }

    protected Action stop(){
        return new Action(Action.STOP);
    }

    //TODO: Test this method.
    protected Action chooseTurningDirection() {
        int groundTilesOnRight = 0, groundTilesOnLeft = 0;

        for (Tile t: nav.finder().getTilesOnSide(Direction.RIGHT)){
            if(t.isGround()) { groundTilesOnRight++; }
        }

        for (Tile t: nav.finder().getTilesOnSide(Direction.LEFT)){
            if(t.isGround()) { groundTilesOnLeft++; }
        }

        if (groundTilesOnLeft > groundTilesOnRight){ return heading(nav.left()); }
        else { return heading(nav.right()); }
    }

    /*
    Chooses the turning direction when flying, based on the shortest distance to the destination tile
    when the tile is at the BACK of the drone.
    */
    protected Action chooseTurningDirection(Tile destinationTile) {
        return heading(nav.finder().chooseTurningDirection(destinationTile));
    }

    protected Action move(Compass direction) {
        return new Action(Action.MOVE_TO).addParameter("direction", direction.toString());
    }

    protected Action scout(String direction) {
        return new Action(Action.SCOUT).addParameter("direction", direction);
    }

    protected Action explore() {
        return new Action(Action.EXPLORE);
    }

    protected Action exploit(List<Resource> resources)  {
        Action action = new Action(Action.EXPLOIT);
        for(Resource r: resources) {
            action.addParameter("resource", r.toString());
        }
        return action;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
