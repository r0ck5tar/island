package fr.unice.polytech.qgl.qdd.ai.sequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.enums.ActionEnum;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

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
        return new Action(ActionEnum.ECHO).addParameter("direction", direction);
    }

    protected Action scan() { return new Action(ActionEnum.SCAN); }

    protected Action fly() {
        return checkList.isCloseToBoundary()?  chooseTurningDirection(): new Action(ActionEnum.FLY);
    }

    protected Action heading(String direction){
        return new Action(ActionEnum.HEADING).addParameter("direction", direction);
    }

    protected Action stop(){
        return new Action(ActionEnum.STOP);
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
}
