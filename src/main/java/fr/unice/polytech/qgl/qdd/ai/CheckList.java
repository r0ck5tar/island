package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.List;

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
        return nav.getCurrentTile().isGround();
    }

    public boolean isCloseToBoundary(){
        switch (explorer.front()){
            case "N": return nav.getPosY() > nav.getMap().getLength() - 3;
            case "E": return nav.getPosX() > nav.getMap().getWidth() - 3;
            case "S": return nav.getPosY() < 2;
            case "W": return nav.getPosX() < 2;
        }
        return false;
    }

    public boolean foundCreek() { return nav.getMap().getCreeks().size() >0; }

    public boolean needToAbort() {
        return explorer.getBudget() < MISSION_ABORT_BUDGET_THRESHOLD;
    }

    /*
        Checks for echo
     */

    public boolean isEchoCoverageSufficient() {
        float unknownTiles = nav.getMap().getUnknownTileCount();
        float totalTiles = nav.getMap().getTotalTileCount();

        return (unknownTiles < (totalTiles*((100-ECHO_COVERAGE_QUOTA)/100)));
    }

    public boolean isTilesInFrontDiscovered(){
        return checkAllTilesDiscovered(nav.getAllTilesInDirection(Direction.FRONT));
    }

    public boolean isTilesAtLeftDiscovered(){
        return checkAllTilesDiscovered(nav.getAllTilesInDirection(Direction.LEFT));
    }

    public boolean isTilesAtRightDiscovered(){
        return checkAllTilesDiscovered(nav.getAllTilesInDirection(Direction.RIGHT));
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
            if (nav.getCurrentTile().hasResource(resource)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAllTilesDiscovered(List<Tile> tiles){
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isGround()) {
                return true;
            }
        }

        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isUnknown()) {
                return false;
            }
        }

        return true;
    }
}
