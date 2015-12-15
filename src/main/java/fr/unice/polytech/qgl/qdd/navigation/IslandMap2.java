package fr.unice.polytech.qgl.qdd.navigation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class IslandMap2 implements TileListener{
    private static final int INITIAL_WIDTH = 3, INITIAL_HEIGHT = 3;
    private int height = INITIAL_HEIGHT;
    private int width = INITIAL_WIDTH;
    private Point currentPosition = new Point(1 ,1);
    private Map<Point, Tile> pointsToTiles = new HashMap<>();
    private Map<Tile, Point> tilesToPoints = new HashMap<>();
    private Set<Tile> seaTiles = new HashSet<>();
    private Set<Tile> groundTiles = new HashSet<>();
    private Set<Tile> unknownTiles = new HashSet<>();
    private Set<Tile> scannedTiles = new HashSet<>();
    private Set<Tile> potentiallyExploitableTiles = new HashSet<>();
    private Set<Tile> exploitableTiles = new HashSet<>();
    private Set<String> creeks = new HashSet<>();

    /*============================================
    Public methods used in InitialDiscoverySequence
    =============================================*/

    public boolean isInitialized() {return !pointsToTiles.isEmpty();}

    public void initializeMapThroughEcho(Compass direction, int range) {
        if(range > 0) {
            /*
            When echoing, the range represents a distance of 3 tiles.
            (e.g. echo range 3 means a distance of 9 tiles).
            We add 1 to the range before multiplying because the range is counted from the tile
            adjacent to the starting position. => distance = (range + 1) * 3
            */
            int distance = (range+1)*3;
            switch(direction){
                case NORTH: height = distance; break;
                case EAST: width = distance; break;
                case SOUTH: height = distance; setY(height -2); break;
                case WEST: width = distance; setX(width - 2); break;
            }
        }

        if(height > INITIAL_HEIGHT && width > INITIAL_WIDTH) {
            initializeMap();
        }
    }

    /*=================================
     Public methods used by QddExplorer.
     =================================*/

    public void updateMapThroughEcho(boolean isGround, int range, Compass direction) {
        int distance = range*3;
        Set<Tile> tiles = new HashSet<>();
        //update map with sea detected by echo
        if(!isGround) {
            switch(direction) {
                case NORTH:
                    tiles.addAll(getTiles(point(getX()-1, getY()+1), direction, distance));
                    tiles.addAll(getTiles(point(getX(), getY()+1), direction, distance));
                    tiles.addAll(getTiles(point(getX()+1, getY()+1), direction, distance));
                    break;
                case EAST:
                    tiles.addAll(getTiles(point(getX()+1, getY()-1), direction, distance));
                    tiles.addAll(getTiles(point(getX()+1, getY()), direction, distance));
                    tiles.addAll(getTiles(point(getX()+1, getY()+1), direction, distance));
                    break;
                case SOUTH:
                    tiles.addAll(getTiles(point(getX()-1, getY()-1), direction, distance));
                    tiles.addAll(getTiles(point(getX(), getY()-1), direction, distance));
                    tiles.addAll(getTiles(point(getX()+1, getY()-1), direction, distance));
                    break;
                case WEST:
                    tiles.addAll(getTiles(point(getX()-1, getY()-1), direction, distance));
                    tiles.addAll(getTiles(point(getX()-1, getY()), direction, distance));
                    tiles.addAll(getTiles(point(getX()-1, getY()+1), direction, distance));
                    break;
            }
            for(Tile t: tiles) { t.setSea(); }
        }
        //update map with ground detected by echo
        else{
            //tiles up to range-1 are sea
            updateMapThroughEcho(false, range-1, direction);
            int groundDistance = distance-2;
            switch (direction) {
                case NORTH:
                    tiles.add(getTile(getX()+1, getY()+groundDistance));
                    tiles.add(getTile(getX(), getY()+groundDistance));
                    tiles.add(getTile(getX()+1, getY()+groundDistance));
                    break;
                case EAST:
                    tiles.add(getTile(getX()+groundDistance, getY()-1));
                    tiles.add(getTile(getX()+groundDistance, getY()));
                    tiles.add(getTile(getX()+groundDistance, getY()+1));
                    break;
                case SOUTH:
                    tiles.add(getTile(getX()-1, getY()-groundDistance));
                    tiles.add(getTile(getX(), getY()-groundDistance));
                    tiles.add(getTile(getX()+1, getY()-groundDistance));
                    break;
                case WEST:
                    tiles.add(getTile(getX()-groundDistance, getY()-1));
                    tiles.add(getTile(getX()-groundDistance, getY()));
                    tiles.add(getTile(getX()-groundDistance, getY()+1));
                    break;
            }
            for (Tile t: tiles) { t.setGround(); }
        }
    }

    /*=============================================================
    Package-private methods: only usable in IslandMap and Navigator
    =============================================================*/

    int getX(Tile tile) {
        return tilesToPoints.get(tile).x;
    }

    int getY(Tile tile) {
        return tilesToPoints.get(tile).y;
    }

    Tile currentTile() {
        return pointsToTiles.get(currentPosition);
    }

    Tile getTile(int x, int y) {
        return pointsToTiles.get(new Point(x, y));
    }

    Tile getTile(Point reference, Compass direction, int range) {
        switch(direction){
            case NORTH: return getTile(reference.x, reference.y + range);
            case EAST: return getTile(reference.x + range, reference.y);
            case SOUTH: return getTile(reference.x, reference.y - range);
            case WEST: return getTile(reference.x - range, reference.y);
        }
        return null;
    }

    Set<Tile> getTiles(Point reference, Compass direction, int range) {
        Set<Tile> tiles = new HashSet<>();
        if(range > 0) {
            switch (direction) {
                case NORTH: return getTilesNorth(reference, range);
                case EAST: return getTilesEast(reference, range);
                case SOUTH: return getTilesSouth(reference, range);
                case WEST: return getTilesWest(reference, range);
            }
        }
        return tiles;
    }

    Set<Tile> getSurroundingTiles(Tile reference) {
        Point coordinates = tilesToPoints.get(reference);
        return getSurroundingTiles(coordinates.x, coordinates.y);
    }

    void setCurrentPosition(Point position) {
        currentPosition = position;
    }

    int getX(){
        return currentPosition.x;
    }

    int getY() {
        return currentPosition.y;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }


    /*==================
    TileListener methods
    ==================*/

    @Override
    public void typeDiscovered(Tile tile, String previousType, String currentType) {

    }

    @Override
    public void biomeDiscovered(Tile tile) {

    }

    @Override
    public void creekDiscovered(Tile tile) {

    }


    /*=======================================================================
    Point (private class representing coordinates) and private helper methods
    =======================================================================*/

    private static class Point {
        public int x, y;
        private Point(int x, int y) {this.x = x; this.y =y;}
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) { return false; }
            if(((Point)o).x != x) return false;
            if(((Point)o).y != y) return false;
            return true;
        }
        @Override
        public int hashCode() {
            return ((x+1) << 16) + (y+1);
        }
    }

    private void setX(int x) {
        currentPosition.x = x;
    }

    private void setY(int y) {
        currentPosition.y = y;
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y>= height;
    }

    private Point point(int x, int y) {
        return new Point(x, y);
    }

    /*======================================
    Private helper methods for finding tiles
     ======================================*/

    private Set<Tile> getSurroundingTiles(int xCoordinate, int yCoordinate) {
        Set<Tile> tiles = new HashSet<>();
        for (int x = xCoordinate -1; x <= xCoordinate +1; x++) {
            for (int y = yCoordinate -1; y <= yCoordinate +1; y++) {
                if (!outOfBounds(x, y)) { tiles.add(getTile(x, y)); }
            }
        }
        tiles.remove(getTile(xCoordinate, yCoordinate));
        return tiles;
    }

    private Set<Tile> getTilesNorth(Point reference, int range) {
        Set<Tile> tiles = new HashSet<>();
        if(reference.y + range < height) {
            for (int y = reference.y + 1; y <= reference.y + range; y++) {
                tiles.add(getTile(reference.x, y));
            }
        }
        return tiles;
    }

    private Set<Tile> getTilesEast(Point reference, int range) {
        Set<Tile> tiles = new HashSet<>();
        if(reference.x + range < width) {
            for (int x = reference.x + 1; x <= reference.x + range; x++) {
                tiles.add(getTile(x, reference.y));
            }
        }
        return tiles;
    }

    private Set<Tile> getTilesSouth(Point reference, int range) {
        Set<Tile> tiles = new HashSet<>();
        if(reference.y - range >= 0) {
            for (int y = reference.y - 1; y >= reference.y - range; y--) {
                tiles.add(getTile(reference.x, y));
            }
        }
        return tiles;
    }

    private Set<Tile> getTilesWest(Point reference, int range) {
        Set<Tile> tiles = new HashSet<>();
        if(reference.x - range >= 0) {
            for (int x = reference.x - 1; x >= reference.x - range; x--) {
                tiles.add(getTile(x, reference.y));
            }
        }
        return tiles;
    }

    /*====================================
     Private  methods for initializing map
     ====================================*/

    private void initializeMap() {
        //create all the tiles for the map of size width * height
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                createTile(x, y);
            }
        }

        //set the tiles at the current position and surrounding the current position as sea tiles
        currentTile().setSea();
        for(Tile t: getSurroundingTiles(currentTile())) {
            t.setSea();
        }

        //set the previously echoed borders as sea tiles.
        int vRangeToBorder = (height - INITIAL_HEIGHT)/3;
        int hRangeToBorder = (width - INITIAL_WIDTH)/3;

        if (getY() == 1) {
            updateMapThroughEcho(false, vRangeToBorder, Compass.NORTH);
        }
        else{
            updateMapThroughEcho(false, vRangeToBorder, Compass.SOUTH);
        }

        if (getX() == 1) {
            updateMapThroughEcho(false, vRangeToBorder, Compass.EAST);
        }
        else{
            updateMapThroughEcho(false, vRangeToBorder, Compass.WEST);
        }
    }

    private void createTile(int x, int y){
        Point coordinates = new Point(x, y);
        Tile tile = new Tile(this);
        pointsToTiles.put(coordinates, tile);
        tilesToPoints.put(tile, coordinates);
    }
}