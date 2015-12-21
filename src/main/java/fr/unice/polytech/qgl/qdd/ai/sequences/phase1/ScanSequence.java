package fr.unice.polytech.qgl.qdd.ai.sequences.phase1;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ExplorerLogger;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequence;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hakim on 12/13/2015.
 */
public class ScanSequence extends Sequence {
    private int counter;

    public ScanSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        //TODO don't call adjacentTile, instead call adjacentTileByAir.
        if(isUnscannedGround(nav.map().currentTile())) {
            ExplorerLogger.shortLog("Scan Sequence " + counter + "-> scan current tile");
            return scan();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.FRONT))) {
            ExplorerLogger.shortLog("Scan Sequence " + counter + "-> fly");
            return fly();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.RIGHT))) {
            ExplorerLogger.shortLog("Scan Sequence " + counter + "-> head right");
            return heading(nav.right());
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.LEFT))) {
            ExplorerLogger.shortLog("Scan Sequence " + counter + "-> head left");
            return  heading(nav.left());
        }
        else{ return new FlyToUnscannedGroundSequence(nav, checkList).execute(); }
    }


    @Override
    public boolean completed() {
        return !nav.map().currentTile().isUnscanned() || counter > 20;
        //return counter > 20;
    }

    private boolean isUnscannedGround(Tile tile) {
        //return tile != null & !tile.isSea() && tile.isUnscanned();
        return tile.isUnscanned();
    }
}
