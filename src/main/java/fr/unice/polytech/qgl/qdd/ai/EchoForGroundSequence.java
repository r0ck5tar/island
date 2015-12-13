package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by Hakim on 12/13/2015.
 */
public class EchoForGroundSequence extends Sequence{
    private int counter;
    private static int MAX_ITERATIONS = 3;

    public EchoForGroundSequence(Navigator nav, CheckList checkList){
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        Action action = null;
        if (!checkList.isTilesAtLeftDiscovered()){
            action = echo( nav.left());
        }
        else if (!checkList.isTilesAtRightDiscovered()){
            action = echo( nav.right());
        }
        else if (!checkList.isTilesInFrontDiscovered()){
            action = echo( nav.front());
        }
        else {
            action = fly();
        }

        counter++;
        return action;
    }

    @Override
    public boolean completed() {
        if(counter > MAX_ITERATIONS);
        return true;
    }
}
