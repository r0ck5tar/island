package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by danial on 12/12/2015.
 */
public class CheckList {
    private Navigator nav;
    private QddExplorer explorer;

    //Must discover at least 30% of map for echo coverage to be considered sufficient.
    private float ECHO_COVERAGE_QUOTA = 30;

    public CheckList(Navigator nav, QddExplorer explorer) {
        this.nav = nav;
        this.explorer = explorer;
    }

    public boolean isEchoCoverageSufficient() {
        float unknownTiles = nav.getMap().getUnknownTileCount();
        float totalTiles = nav.getMap().getTotalTileCount();

        return (unknownTiles < (totalTiles*((100-ECHO_COVERAGE_QUOTA)/100)));
    }

    public boolean isAboveGround(){
        return nav.getCurrentTile().isGround();
    }

    public boolean noGroundAround() {
        List<Tile> tilesAround = new ArrayList<>();
        tilesAround.addAll(nav.getAllTilesInDirection(Direction.FRONT));
        tilesAround.addAll(nav.getAllTilesInDirection(Direction.RIGHT));
        tilesAround.addAll(nav.getAllTilesInDirection(Direction.LEFT));

        for(Tile t: tilesAround) {
            if(t.isGround()) { return false; }
        }

        return true;
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

    public boolean isTilesInFrontDiscovered(){
        return checkAllTileDiscovered(nav.getAllTilesInDirection(Direction.FRONT));
    }

    public boolean isTilesAtLeftDiscovered(){
        return checkAllTileDiscovered(nav.getAllTilesInDirection(Direction.LEFT));
    }

    public boolean isTilesAtRightDiscovered(){
        return checkAllTileDiscovered(nav.getAllTilesInDirection(Direction.RIGHT));
    }

    private boolean checkAllTileDiscovered(List<Tile> tiles){
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

    public boolean findCreeks() {
        for (int w = 0; w < nav.getMap().getWidth(); w++) {
            for (int l = 0; l < nav.getMap().getLength(); l++) {
                if (nav.getMap().getTile(w,l).hasCreek()) return true;
            }
        }

        return false;
    }

}
