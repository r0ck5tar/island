package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Hakim on 12/12/2015.
 */
public class CheckList {
    private IslandMap map;
    private QddExplorer explorer;

    private boolean foundGround;
    private boolean foundCreek;
    private boolean sufficientBudget;

    public CheckList(IslandMap map, QddExplorer explorer) {
        this.map = map;
        this.explorer = explorer;

        foundGround = false;
        foundCreek = false;
        sufficientBudget = true;
    }

    public boolean isFoundGround(){
        if (!foundGround){
            for(int w = 0; w<map.getWidth(); w++){
                for (int l = 0; l<map.getLength(); l++){
                    if (map.getTile(w,l) != null && map.getTile(w,l).isGround()) {
                        foundGround = true;
                    }
                }
            }
        }
        return foundGround;
    }

    public boolean isAboveGround(){
        return map.getCurrentTile().isGround();
    }

    public boolean isTilesInFrontDiscovered(){
        List<Tile> tiles = getTilesInDirection(explorer.getNavigator().front());
        return checkAllTileDiscovered(tiles);
    }

    public boolean isTilesAtLeftDiscovered(){
        List<Tile> tiles = getTilesInDirection(explorer.getNavigator().left());
        return checkAllTileDiscovered(tiles);
    }

    public boolean isTilesAtRightDiscovered(){
        List<Tile> tiles = getTilesInDirection(explorer.getNavigator().right());
        return checkAllTileDiscovered(tiles);
    }


    private List<Tile> getTilesInDirection(String direction) {
        List<Tile> tiles = null;
        switch (direction){
            case "N": tiles = map.getTilesNorth(); break;
            case "E": tiles = map.getTilesEast(); break;
            case "S": tiles = map.getTilesSouth(); break;
            case "W": tiles = map.getTilesWest(); break;
        }
        return tiles;
    }

    private boolean checkAllTileDiscovered(List<Tile> tiles){
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isUnknown()) {
                return false;
            }
        }
        return true;
    }

    public Tile findTileWithGround(){
        List<Tile> tiles = getTilesInDirection(explorer.getNavigator().left());
        tiles.addAll(getTilesInDirection(explorer.getNavigator().right()));

        for (int i = 0 ; i < tiles.size(); i++) {
            if (tiles.get(i).isGround()) {
                return tiles.get(i);
            }
        }
        return null;
    }

    public boolean findCreeks() {
        for (int w = 0; w < map.getWidth(); w++) {
            for (int l = 0; l < map.getLength(); l++) {
                if (map.getTile(w,l).hasCreek()) return true;
            }
        }

        return false;
    }

    public Tile makeAuTurn() {
        return null;
    }

   /* public List<Tile> findNearbyUnscannedTile(){
        List<Tile> tiles = map.getNeighbouringTiles()

    }*/
}
