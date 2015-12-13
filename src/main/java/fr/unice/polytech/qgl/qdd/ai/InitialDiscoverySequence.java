package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by Hakim on 12/13/2015.
 */
public class InitialDiscoverySequence extends Sequence {
    public InitialDiscoverySequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        //First echo; echo front
        if(nav.getMap().getWidth() == 1 && nav.getMap().getLength() == 1) { return echo(nav.front()); }

        //second echo; echo right
        else if (nav.getMap().getWidth() == 1 || nav.getMap().getLength() == 1) { return echo(nav.right()); }

        //third echo; echo left (Only if second echo returned range = 0; i.e. the border is to the right)
        else if (nav.getMap().getWidth() == -1 || nav.getMap().getLength() == -1) { return echo(nav.left()); }

        else { nav.initializeMap(); }

        return fly();
    }

    @Override
    public boolean completed() {
        return nav.mapInitialized();
    }
}
