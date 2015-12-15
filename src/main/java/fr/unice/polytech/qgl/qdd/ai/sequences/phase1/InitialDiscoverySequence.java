package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import com.sun.org.apache.xml.internal.security.Init;
import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class InitialDiscoverySequence extends Sequence {
    private int counter = 1;
    private static InitialDiscoverySequence instance;

    private InitialDiscoverySequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        switch(counter) {
            case 1: return echo(nav.front()); //First echo; echo front
            case 2: return echo(nav.right()); //Second echo; echo right
            case 3: if(!nav.mapInitialized()) { return echo(nav.left()); }
        }
        return fly();
    }

    @Override
    public boolean completed() {
        return nav.mapInitialized();
    }

    public static InitialDiscoverySequence get(Navigator nav, CheckList checkList) {
        if(instance == null) {
            instance = new InitialDiscoverySequence(nav, checkList);
        }
        return instance;
    }
}
