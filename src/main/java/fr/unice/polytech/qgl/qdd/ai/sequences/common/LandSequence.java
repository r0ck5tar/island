package fr.unice.polytech.qgl.qdd.ai.sequences.common;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by Hakim on 12/13/2015.
 */
public class LandSequence extends Sequence {
    private int men;
    public LandSequence(Navigator nav, CheckList checkList, int men) {
        super(nav, checkList);
        this.men = men;
    }

    @Override
    public Action execute() {
        return new Action(Action.LAND).addParameter("creek", (String) nav.map().getCreeks().toArray()[0])
                .addParameter("people", Integer.toString(men));
    }

    @Override
    public boolean completed() {
        return true;
    }
}
