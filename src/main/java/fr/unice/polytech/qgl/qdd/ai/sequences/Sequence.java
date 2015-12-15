package fr.unice.polytech.qgl.qdd.ai.sequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.enums.Resource;
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

    protected Action echo(String direction){
        return new Action(Action.ECHO).addParameter("direction", direction);
    }

    protected Action scan() { return new Action(Action.SCAN); }

    protected Action fly() {
        return checkList.isCloseToBoundary()?  chooseTurningDirection(): new Action(Action.FLY);
    }

    protected Action heading(String direction){
        return new Action(Action.HEADING).addParameter("direction", direction);
    }

    protected Action stop(){
        return new Action(Action.STOP);
    }

    protected Action chooseTurningDirection() {
        int unknownTilesOnRight = 0, unknownTilesOnLeft = 0;

        for (Tile t: nav.getTilesOnSide(Direction.RIGHT)){
            if(t.isUnknown()) { unknownTilesOnRight++; }
        }

        for (Tile t: nav.getTilesOnSide(Direction.LEFT)){
            if(t.isUnknown()) { unknownTilesOnLeft++; }
        }

        if (unknownTilesOnLeft > unknownTilesOnRight){ return heading(nav.left()); }
        else { return heading(nav.right()); }
    }

    protected Action move(String direction) {
        return new Action(Action.MOVE_TO).addParameter("direction", direction);
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


}
