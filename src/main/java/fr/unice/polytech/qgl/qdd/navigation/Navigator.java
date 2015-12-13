package fr.unice.polytech.qgl.qdd.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danial on 20/11/15.
 */
public class Navigator {

    private String front;
    private IslandMap map;

    public Navigator(String facing){
        front = facing;
        map = new IslandMap();
    }
    /*
        Map methods
     */
    public boolean mapInitialized(){
        return map.isInitialized();
    }

    public void initializeMap() { map.initializeMap(); }

    public IslandMap getMap() { return map; }

    public List<Tile> getNeighbouringTiles(Tile tile) {return map.getNeighbouringTiles(tile);}

    public Tile getCurrentTile() {return map.getCurrentTile(); }

    public Tile getTile(int x, int y) { return  map.getTile(x, y); }

    public int getPosX() { return map.getPosX(); }

    public int getPosY() { return map.getPosY(); }

    public List<Tile> getAllTilesInDirection(Direction direction) {
        List<Tile> tiles = null;
        switch (direction) {
            case FRONT: tiles = getAllTilesInAbsoluteDirection(front()); break;
            case RIGHT: tiles = getAllTilesInAbsoluteDirection(right()); break;
            case LEFT: tiles = getAllTilesInAbsoluteDirection(left()); break;
            case BACK: tiles = getAllTilesInAbsoluteDirection(back()); break;
        }
        return tiles;
    }

    private List<Tile> getAllTilesInAbsoluteDirection(String direction) {
        List<Tile> tiles = null;
        switch (direction){
            case "N": tiles = map.getTilesNorth(); break;
            case "E": tiles = map.getTilesEast(); break;
            case "S": tiles = map.getTilesSouth(); break;
            case "W": tiles = map.getTilesWest(); break;
        }
        return tiles;
    }

    public Direction getRelativeDirectionOfTile(Tile tile){
        boolean strictlyVAligned = getCurrentTile().strictlyVAlignedWith(tile);
        boolean strictlyHAligned = getCurrentTile().strictlyHAlignedWith(tile);
        boolean vAligned = getCurrentTile().vAlignedWith(tile);
        boolean hAligned = getCurrentTile().hAlignedWith(tile);

        switch (front()) {
            case "N":
                if(hAligned && !strictlyVAligned) {
                    if(tile.getxAxis() < getPosX()) { return Direction.LEFT; }
                    else if(tile.getxAxis() > getPosX()) { return Direction.RIGHT;  }
                }
                else{
                    if(tile.getyAxis() < getPosY()) { return Direction.BACK; }
                    else if (tile.getyAxis() > getPosY()) { return Direction.FRONT; }
                }
                break;

            case "E":
                if(vAligned && !strictlyHAligned) {
                    if(tile.getyAxis() > getPosY()) { return Direction.LEFT; }
                    else if(tile.getyAxis() < getPosY()) { return Direction.RIGHT;  }
                }
                else{
                    if(tile.getxAxis() < getPosX()) { return Direction.BACK; }
                    else if (tile.getxAxis() > getPosX()) { return Direction.FRONT; }
                }
                break;

            case "S":
                if(hAligned && !strictlyVAligned) {
                    if(tile.getxAxis() > getPosX()) { return Direction.LEFT; }
                    else if(tile.getxAxis() < getPosX()) { return Direction.RIGHT;  }
                }
                else{
                    if(tile.getyAxis() > getPosY()) { return Direction.BACK; }
                    else if (tile.getyAxis() < getPosY()) { return Direction.FRONT; }
                }
                break;

            case "W":
                if(vAligned && !strictlyHAligned) {
                    if(tile.getyAxis() < getPosY()) { return Direction.LEFT; }
                    else if(tile.getyAxis() > getPosY()) { return Direction.RIGHT;  }
                }
                else{
                    if(tile.getxAxis() > getPosX()) { return Direction.BACK; }
                    else if (tile.getxAxis() < getPosX()) { return Direction.FRONT; }
                }
                break;
        }

        return Direction.FRONT;
    }

    public Tile getTileInDirection(Direction direction) {
        switch (direction){
            case FRONT: return getTileInAbsoluteDirection(front());
            case RIGHT: return getTileInAbsoluteDirection(right());
            case LEFT: return getTileInAbsoluteDirection(left());
            case BACK: return getTileInAbsoluteDirection(back());
        }
        return null;
    }

    private Tile getTileInAbsoluteDirection(String direction) {
        switch(direction){
            case "N": return map.getTilesNorthByRange(1).get(0);
            case "E": return map.getTilesEastByRange(1).get(0);
            case "S": return map.getTilesSouthByRange(1).get(0);
            case "W": return map.getTilesWestByRange(1).get(0);
        }
        return null;
    }

    public Tile findTileWithUnscannedGround(){
        List<Tile> tiles = getAllTilesInDirection(Direction.LEFT);
        tiles.addAll(getAllTilesInDirection(Direction.RIGHT));

        for (int i = 0 ; i < tiles.size(); i++) {
            if (tiles.get(i).isGround() && tiles.get(i).isUnscanned()) {
                return tiles.get(i);
            }
        }
        return null;
    }

    public List<Tile> getTilesOnSide(Direction direction){
        switch (direction){
            case LEFT: return getTilesOnAbsoluteSide(left());
            case RIGHT: return getTilesOnAbsoluteSide(right());
        }
        return null;
    }

    private List<Tile> getTilesOnAbsoluteSide(String direction) {
        List<Tile> tiles = new ArrayList<>();
        switch (direction){
            case "N":
                for (int l = getPosY() + 1; l < map.getLength(); l++ ) {
                    for (int w = 0; w < map.getWidth(); w++ ){
                        tiles.add(map.getTile(w,l));
                    }
                }
                break;
            case "E":
                for (int l = 0; l < map.getLength(); l++) {
                    for (int w = getPosX() + 1; w < map.getWidth(); w++ ) {
                        tiles.add(map.getTile(w,l));
                    }
                }
                break;
            case "S":
                for (int l = 0; l < map.getPosY(); l++ ) {
                    for (int w = 0; w < map.getWidth(); w++ ){
                        tiles.add(map.getTile(w,l));
                    }
                }
                break;
            case "W":
                for (int l = 0; l < map.getLength(); l++) {
                    for (int w = 0; w < map.getPosX(); w++ ) {
                        tiles.add(map.getTile(w,l));
                    }
                }
                break;
        }
        return tiles;
    }

    /*
        Directions
     */
    public String front(){
        return front;
    }

    public String back(){
        return Compass.valueOf(front).back;
    }

    public String right(){
        return Compass.valueOf(front).right;
    }

    public String left(){
        return Compass.valueOf(front).left;
    }

    public void setFront(String front)
    {
        this.front = front;
    }

    private enum Compass {
        N("S","W","E"),
        S("N","E","W"),
        W("E","S","N"),
        E("W","N","S"),;
        public String back;
        public String left;
        public String right;

        Compass(String back, String left, String right){
            this.back = back;
            this.left = left;
            this.right = right;
        }
    }
}

