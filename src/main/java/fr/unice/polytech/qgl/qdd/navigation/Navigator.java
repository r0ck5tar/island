package fr.unice.polytech.qgl.qdd.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by danial on 20/11/15.
 */
public class Navigator {

    private String front;
    private IslandMap map;
    private Random random;

    public Navigator(String facing){
        front = facing;
        map = new IslandMap();
        random = new Random();
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

    public Tile getCenterTile() {return map.getTile(map.getWidth()/2 +1, map.getLength()/2 +1); }

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
            case FRONT_RIGHT: return getTileInFrontRight();
            case FRONT_LEFT: return getTileInFrontLeft();
        }
        return null;
    }

    public Tile getTileInAbsoluteDirection(String direction) {
        switch(direction){
            case "N": return map.getTilesNorthByRange(1).get(0);
            case "E": return map.getTilesEastByRange(1).get(0);
            case "S": return map.getTilesSouthByRange(1).get(0);
            case "W": return map.getTilesWestByRange(1).get(0);
        }
        return null;
    }

    private Tile getTileInFrontRight(){
        switch (front){
            case "N": return map.getTile(getPosX()+1, getPosY()+1);
            case "S": return map.getTile(getPosX()-1, getPosY()-1);
            case "E": return map.getTile(getPosX()+1, getPosY()-1);
            case "W": return map.getTile(getPosX()-1, getPosY()+1);
        }
        return null;
    }

    private Tile getTileInFrontLeft(){
        switch (front){
            case "N": return map.getTile(getPosX()-1, getPosY()+1);
            case "S": return map.getTile(getPosX()+1, getPosY()-1);
            case "E": return map.getTile(getPosX()+1, getPosY()+1);
            case "W": return map.getTile(getPosX()-1, getPosY()-1);
        }
        return null;
    }

    public Tile findAdjacentTileWithUnscannedGround(){
        List<Tile> tiles = getAllTilesInDirection(Direction.LEFT);
        tiles.addAll(getAllTilesInDirection(Direction.RIGHT));
        tiles.addAll(getAllTilesInDirection(Direction.FRONT));

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

    public Tile getRandomNearbyTile(){
        int randomX =0;
        int randomY =0;

        do{
            randomX = getPosX()-3 + random.nextInt(7);
            randomY = getPosY()-3 + random.nextInt(7);
        }
        while(randomX > map.getWidth()-1 || randomX <0 || randomY > map.getLength()-1 || randomY < 0);

        return getTile(randomX, randomY);

    }

    public Tile getRandomUnscannedGroundTile() {
        List<Tile> unscanned = new ArrayList<>();
        for(Tile t: map.getGroundTiles()){
            if(t.isUnscanned()) {
                unscanned.add(t);

            }
        }

        if(unscanned.size() >0) {
            return unscanned.get(random.nextInt(unscanned.size()));
        }

        else{
            return null;
        }
    }

    public void setPosition(Tile tile, String facing) {
        map.setPosX(tile.getxAxis());
        map.setPosY(tile.getyAxis());
        setFront(facing);
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

