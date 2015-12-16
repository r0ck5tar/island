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
            ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> scan current tile");
            return scan();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.FRONT))) {
            ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> fly");
            return fly();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.RIGHT))) {
            ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> head right");
            return heading(nav.right());
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.LEFT))) {
            ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> head left");
            return  heading(nav.left());
        }
        else{ return scanNearbyUnscannedGround(); }
    }

    @Override
    public boolean completed() {
        //return !nav.map().currentTile().isUnscanned() || counter > 20;
        return counter > 20;
    }

    private boolean isUnscannedGround(Tile tile) {
        //return tile != null & !tile.isSea() && tile.isUnscanned();
        return tile.isUnscanned();
    }

    private Action scanNearbyUnscannedGround() {
        int range = 12;
        Set<Tile> nearbyTiles = new HashSet<>();
        nearbyTiles.addAll(nav.finder().allTiles(Direction.FRONT, range));
        nearbyTiles.addAll(nav.finder().allTiles(Direction.RIGHT, range));
        nearbyTiles.addAll(nav.finder().allTiles(Direction.LEFT, range));
        for(Tile t: nearbyTiles) {
            if(isUnscannedGround(t)) {
                ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> scan nearby unscanned ground");
                return new FlyToDestinationSequence(nav, checkList, t).execute();
            }
        }

        ExplorerLogger.getInstance().log("Scan Sequence " + counter + "-> fly to random nearby tile");
        return new FlyToRandomNearbyTileSequence(nav, checkList).execute();
    }
}
