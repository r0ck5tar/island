package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * The map consists of a list of lists which form a grid of tiles.
 * The elements of the outer list represent the x axis, or the width of the map.
 * The elements of each list contained in the outer list represent the y axis, or the length of the map.
 * To get the tile at coordinate (x,y), do the following:
 * map.get(x).get(y)
 * Created by danial on 10/12/15.
 */
public class IslandMap implements TileListener{
    private Tile[][] map = null;
    private int length = 1;
    private int width = 1;
    private int posX = 0;
    private int posY = 0;
    private List<Tile> scannedTiles = new ArrayList<>();
    private List<Tile> seaTiles = new ArrayList<>();
    private List<Tile> groundTiles = new ArrayList<>();
    private List<Tile> unknownTiles = new ArrayList<>();
    private List<String> creeks = new ArrayList<>();

    public boolean isInitialized() {
        return map!=null;
    }

    public void initializeMap(){

        map = new Tile[width][length];

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < length; y++) {
                map[x][y] = new Tile(x, y, this);
            }
        }

        map[posX][posY].setType(TileTypeEnum.SEA);

        if(posX == 0 && posY == 0) {
            updateMapThroughEcho(false, length -1, "N");
            updateMapThroughEcho(false, width -1, "E");
        }
        else if (posX == 0) {
            updateMapThroughEcho(false, length - 1, "S");
            updateMapThroughEcho(false, width - 1, "E");
        }
        else if (posY == 0 ) {
            updateMapThroughEcho(false, length - 1, "N");
            updateMapThroughEcho(false, width - 1, "W");
        }
        else{
            updateMapThroughEcho(false, length - 1, "S");
            updateMapThroughEcho(false, width - 1, "W");
        }

    }

    public void updateMapThroughEcho(boolean isGround, int range, String direction){
        //update map with sea detected by echo
        if(!isGround) {
            switch(direction) {
                case "N": for(Tile t: getTilesNorthByRange(range)) {t.setType(TileTypeEnum.SEA);} break;
                case "E": for(Tile t: getTilesEastByRange(range)) {t.setType(TileTypeEnum.SEA);} break;
                case "S": for(Tile t: getTilesSouthByRange(range)) {t.setType(TileTypeEnum.SEA);} break;
                case "W": for(Tile t: getTilesWestByRange(range)) {t.setType(TileTypeEnum.SEA);} break;
            }
        }
        //update map with ground detected by echo
        else {
            // Ground detected is at range +/- 1
            updateMapThroughEcho(false, range, direction);
            switch(direction) {
                case "N": map[posX][posY + range + 1].setType(TileTypeEnum.GROUND); break;
                case "E": map[posX + range + 1][posY].setType(TileTypeEnum.GROUND); break;
                case "S": map[posX][posY - range - 1].setType(TileTypeEnum.GROUND); break;
                case "W": map[posX - range - 1][posY].setType(TileTypeEnum.GROUND); break;
            }
        }
    }

    public void updateMapThroughScan(List<BiomeEnum> biomes){
        Tile scannedTile = getCurrentTile();
        scannedTile.addBiomes(biomes);
    }

    public void updateMapWithCreeks(List<String> creeks){
        Tile scannedTile = getCurrentTile();
        scannedTile.addCreeks(creeks);
    }

    public void updateMap(Resource resource, int range){

    }

    public void updateMap(Resource resource){

    }

    /*
        Tile retrieval methods
     */
    public List<Tile> getTilesNorth(){
        return getTilesNorthByRange(length - posY - 1);
    }

    public List<Tile> getTilesNorthByRange(int range){
        List<Tile> tiles = new ArrayList<>();
        if(posY < length - 1) {
            for (int i = posY + 1; i <= posY + range ; i++){
                tiles.add(map[posX][i]);
            }
        }
        return tiles;
    }

    public List<Tile> getTilesEast(){
        return getTilesEastByRange(width - posX - 1);
    }

    public List<Tile> getTilesEastByRange(int range){
        List<Tile> tiles = new ArrayList<>();
        if((posX < width - 1)) {
            for (int i = posX + 1; i <= posX + range ; i++){
                tiles.add(map[i][posY]);
            }
        }
        return tiles;
    }

    public List<Tile> getTilesSouth(){
        return getTilesSouthByRange(posY);
    }

    public List<Tile> getTilesSouthByRange(int range){
        List<Tile> tiles = new ArrayList<>();
        if(posY > 0){
            for (int i = 1 ; i <= range ; i++){
                tiles.add(map[posX][posY - i]);
            }
        }
        return tiles;
    }

    public List<Tile> getTilesWest(){
        return getTilesWestByRange(posX);
    }

    public List<Tile> getTilesWestByRange(int range){
        List<Tile> tiles = new ArrayList<>();
        if(posX >0 ){
            for (int i = 1; i <= range ; i++){
                tiles.add(map[posX - i][posY]);
            }
        }
        return tiles;
    }

    public List<Tile> getNeighbouringTiles(Tile tile){
        List<Tile> tiles = new ArrayList<>();

        for (int w = tile.getxAxis() - 1; w <= tile.getxAxis() + 1; w++) {
            for (int l = tile.getyAxis() - 1; l <= tile.getyAxis() + 1; l++) {
                if (w >= 0 && w < width && l >= 0 && l < length){
                    tiles.add(map[w][l]);
                }
            }
        }
        return tiles;
    }

    public Tile getCurrentTile() {
        return getTile(posX, posY);
    }

    public Tile getTile(int x, int y) {
        if (x< 0 || x >= width || y < 0 || y >= length)
            return null;
        return map[x][y];
    }

    /*
        Getters and Setters
     */

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getScannedTileCount() {
        return scannedTiles.size();
    }

    public int getSeaTileCount() {
        return seaTiles.size();
    }

    public int getUnknownTileCount() {
        return unknownTiles.size();
    }

    public int getGroundTileCount() {
        return groundTiles.size();
    }

    public int getTotalTileCount() {
        return seaTiles.size() + unknownTiles.size() + groundTiles.size();
    }

    public List<Tile> getScannedTiles() {
        return scannedTiles;
    }

    public List<Tile> getSeaTiles() {
        return seaTiles;
    }

    public List<Tile> getGroundTiles() {
        return groundTiles;
    }

    public List<Tile> getUnknownTiles() {
        return unknownTiles;
    }

    /*
            TileListener methods
        */
    @Override
    public void typeDiscovered(Tile tile, TileTypeEnum previousType, TileTypeEnum currentType) {
        switch (currentType){
            case SEA: seaTiles.add(tile); break;
            case GROUND: groundTiles.add(tile); break;
            case UNKNOWN: unknownTiles.add(tile);
        }

        if(previousType!=null){
            switch (previousType){
                case SEA: seaTiles.remove(tile); break;
                case GROUND: groundTiles.remove(tile); break;
                case UNKNOWN: unknownTiles.remove(tile); break;
            }
        }
    }

    @Override
    public void biomeDiscovered(Tile tile) {
        scannedTiles.add(tile);
    }

    @Override
    public void creekDiscovered(Tile tile) {
        creeks.addAll(tile.getCreeks());
    }
}
