package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.List;
import java.util.Set;

/**
 * Created by danial on 12/12/2015.
 */
public class CheckList {
    private Navigator nav;
    private QddExplorer explorer;

    //Must discover at least 30% of map for echo coverage to be considered sufficient.
    private static final float  ECHO_COVERAGE_QUOTA = 30;
    private static final int MISSION_ABORT_BUDGET_THRESHOLD = 30;

    public CheckList(Navigator nav, QddExplorer explorer) {
        this.nav = nav;
        this.explorer = explorer;
    }

    public boolean isAboveGround(){
        return nav.map().currentTile().isGround();
    }

    public boolean isCloseToBoundary(){
        switch (nav.front()){
            case NORTH:return nav.map().y() > nav.map().height() - 3;
            case EAST: return nav.map().x() > nav.map().width() - 3;
            case SOUTH: return nav.map().y() < 2;
            case WEST: return nav.map().x() < 2;
        }
        return false;
    }

    public boolean foundCreek() { return !nav.map().getCreeks().isEmpty(); }

    public boolean needToAbort() {
        return explorer.getBudget() < MISSION_ABORT_BUDGET_THRESHOLD;
    }

    /*
        Checks for echo
     */

    public boolean  isEchoCoverageSufficient() {
        float unknownTiles = nav.map().getUnknownTileCount();
        float totalTiles = nav.map().getTotalTileCount();

        return (unknownTiles < (totalTiles*((100-ECHO_COVERAGE_QUOTA)/100)));
    }

    public boolean isTilesInFrontDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.FRONT));
    }

    public boolean isTilesAtLeftDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.LEFT));
    }

    public boolean isTilesAtRightDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.RIGHT));
    }

    public boolean contractCompleted() {
        for (Resource resource : explorer.getContract().keySet()) {
            if(explorer.getResourceQuantity(resource) < explorer.getContract().get(resource)) {
                return false;
            }
        }

        //TODO
        return false;
    }

    public boolean exploitableResourceFound() {
        for(Resource resource : explorer.getContract().keySet()) {
            if (nav.map().currentTile().hasResource(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAllTilesDiscovered(Set<Tile> tiles){
        for(Tile t: tiles) { if (t.isGround()) return true; }

        for(Tile t: tiles) { if (t.isUnknown()) return false; }

        return true;
    }
}
