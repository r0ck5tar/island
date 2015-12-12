package fr.unice.polytech.qgl.qdd;

import eu.ace_design.island.map.resources.Biome;
import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;
import org.omg.CORBA.BAD_INV_ORDER;

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
public class IslandMap {
    private Tile[][] map;
    private int length;
    private int width;
    private int posX;
    private int posY;

    public IslandMap(){
        posX = 0;
        posY = 0;
        width = 1;
        length = 1;
        map = null;
    }

    public void updateMap(boolean isGround, int range,String direction){
        //update map with sea detected by echo
        if(!isGround) {
            switch(direction) {
                case "N":
                    for(int y = 1; y < range; y++) {
                        map[posX][posY + y].setType(TileTypeEnum.SEA);
                    }
                    break;
                case "E":
                    for(int x = 1; x < range; x++) {
                        map[posX + x][posY].setType(TileTypeEnum.SEA);
                    }
                    break;
                case "S":
                    for(int y = 1; y < range; y++) {
                        map[posX][posY - y].setType(TileTypeEnum.SEA);
                    }
                    break;
                case "W":
                    for(int x = 1; x < range; x++) {
                        map[posX - x][posY].setType(TileTypeEnum.SEA);
                    }
                    break;
            }
        }
        //update map with ground detected by echo
        else {
            // Ground detected is at range +/- 1
            updateMap(false, range+1, direction);
            switch(direction) {
                case "N": map[posX][posY + range + 1].setType(TileTypeEnum.GROUND); break;
                case "E": map[posX + range + 1][posY].setType(TileTypeEnum.GROUND); break;
                case "S": map[posX][posY - range - 1].setType(TileTypeEnum.GROUND); break;
                case "W": map[posX - range - 1][posY].setType(TileTypeEnum.GROUND); break;
            }
        }
    }

    public void updateMap(List<BiomeEnum> biomes){
        Tile scannedTile = getCurrentTile();
        scannedTile.addBiomes(biomes);
    }

    public void updateMap(Resource resource, int range){

    }

    public void updateMap(Resource resource){

    }

    public boolean isInitialized() {
        return map!=null;
    }

    public void initializeMap(){

        map = new Tile[width][length];

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < length; y++) {
                map[x][y] = new Tile(x, y);
            }
        }

        map[posX][posY].setType(TileTypeEnum.SEA);

        if(posX == 0 && posY == 0) {
            updateMap(false, length, "N");
            updateMap(false, width, "E");
        }
        else if (posX == 0) {
            updateMap(false, length, "S");
            updateMap(false, width, "E");
        }
        else if (posY == 0 ) {
            updateMap(false, length, "N");
            updateMap(false, width, "W");
        }
        else{
            updateMap(false, length, "S");
            updateMap(false, width, "W");
        }

    }

    public List<Tile> getTilesNorth(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = posY + 1; i < length ; i++){
            tiles.add(map[posX][i]);
        }
        return tiles;
    }

    public List<Tile> getTilesEast(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = posX + 1; i < width ; i++){
            tiles.add(map[i][posY]);
        }
        return tiles;
    }

    public List<Tile> getTilesSouth(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i < posY ; i++){
            tiles.add(map[posX][posY - i]);
        }
        return tiles;
    }

    public List<Tile> getTilesWest(){
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i < posX ; i++){
            tiles.add(map[posX - i][posY]);
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

    public Tile getTile(int x, int y) {
        if (x< 0 || x >= width || y < 0 || y >= length)
            return null;
        return map[x][y];
    }

    public Tile[][] getMap(){
        return map;
    }

    public Tile getCurrentTile() {
        return map[posX][posY];
    }
}
