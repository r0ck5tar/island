package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;

import java.util.*;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class IslandMap implements TileListener{
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

    /*=============================================
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
                    tiles.addAll(getTiles(x()-1, y()+1, direction, distance));
                    tiles.addAll(getTiles(x(), y()+1, direction, distance));
                    tiles.addAll(getTiles(x()+1, y()+1, direction, distance));
                    break;
                case EAST:
                    tiles.addAll(getTiles(x()+1, y()-1, direction, distance));
                    tiles.addAll(getTiles(x()+1, y(), direction, distance));
                    tiles.addAll(getTiles(x()+1, y()+1, direction, distance));
                    break;
                case SOUTH:
                    tiles.addAll(getTiles(x()-1, y()-1, direction, distance));
                    tiles.addAll(getTiles(x(), y()-1, direction, distance));
                    tiles.addAll(getTiles(x()+1, y()-1, direction, distance));
                    break;
                case WEST:
                    tiles.addAll(getTiles(x()-1, y()-1, direction, distance));
                    tiles.addAll(getTiles(x()-1, y(), direction, distance));
                    tiles.addAll(getTiles(x()-1, y()+1, direction, distance));
                    break;
            }
            for(Tile t: tiles) { t.setSea(); }
        }
        //update map with ground detected by echo
        else{
            //tiles up to range-1 are sea
            updateMapThroughEcho(false, range, direction);
            int groundDistance = distance + 2;
            switch (direction) {
                case NORTH:
                    tiles.add(getTile(x()-1, y()+groundDistance));
                    tiles.add(getTile(x(), y()+groundDistance));
                    tiles.add(getTile(x()+1, y()+groundDistance));
                    break;
                case EAST:
                    tiles.add(getTile(x()+groundDistance, y()-1));
                    tiles.add(getTile(x()+groundDistance, y()));
                    tiles.add(getTile(x()+groundDistance, y()+1));
                    break;
                case SOUTH:
                    tiles.add(getTile(x()-1, y()-groundDistance));
                    tiles.add(getTile(x(), y()-groundDistance));
                    tiles.add(getTile(x()+1, y()-groundDistance));
                    break;
                case WEST:
                    tiles.add(getTile(x()-groundDistance, y()-1));
                    tiles.add(getTile(x()-groundDistance, y()));
                    tiles.add(getTile(x()-groundDistance, y()+1));
                    break;
            }
            for (Tile t: tiles) { t.setGround(); }
        }
    }

    public void updateMapThroughScan(List<Biome> biomes) {
        currentTile().addBiomes(biomes);
        for(Tile t : getSurroundingTiles(currentTile())) {
            t.addBiomes(biomes);
        }
    }

    public void updateMapWithCreeks(List<String> creeks) {
        currentTile().addCreeks(creeks);
    }

    public void updateMapWithResources(Map<Resource, String> resources) {
        currentTile().addResources(resources);
    }

    public void updateMapAfterExploit(Resource resource) {
        currentTile().removeResource(resource);
    }

    /*================================
     Public methods used by Checklist.
     ===============================*/

    public int getUnknownTileCount() {
        return unknownTiles.size();
    }

    public int getGroundTileCount() {
        return groundTiles.size();
    }

    public int getSeaTileCount() {
        return seaTiles.size();
    }

    public int getTotalTileCount() {
        return seaTiles.size() + unknownTiles.size() + groundTiles.size();
    }

    public Set<String> getCreeks() {
        return creeks;
    }

    public Tile currentTile() {
        return pointsToTiles.get(currentPosition);
    }

    //TODO: see if it's possible to not use the following methods outside the navigation package
    public int x(){
        return currentPosition.x;
    }

    public int y() {
        return currentPosition.y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    /*============================================================
    Package-private methods: only usable in the navigation package
    ============================================================*/

    Tile getTile(int x, int y) {
        return pointsToTiles.get(new Point(x, y));
    }

    Tile getTile(Tile referenceTile, Compass direction, int range) {
        Point reference = tilesToPoints.get(referenceTile);
        switch(direction){
            case NORTH: return getTile(reference.x, reference.y + range);
            case EAST: return getTile(reference.x + range, reference.y);
            case SOUTH: return getTile(reference.x, reference.y - range);
            case WEST: return getTile(reference.x - range, reference.y);
        }
        return null;
    }

    /**
     * @param direction The absolute direction in which to get the tiles.
     * @return A set of all the tiles in the given direction between the current tile to the edge of the map.
     */
    Set<Tile> getTiles(Compass direction) {
        return getTiles(x(), y(), direction);
    }

    /**
     * @param refX The x coordinate of the reference tile.
     * @param refY The y coordinate of the reference tile.
     * @param direction The absolute direction in which to get the tiles.
     * @return A set of all the tiles in the given direction between the tile at coordinates (refX, refY) to the edge of the map.
     */
    Set<Tile> getTiles(int refX, int refY, Compass direction) {
        int range = 0;
        switch (direction) {
            case NORTH: range = height - y() - 1; break;
            case EAST: range = width - y() - 1; break;
            case SOUTH: range = y(); break;
            case WEST: range = x(); break;
        }
        return getTiles(refX, refY, direction, range);
    }

    /**
     * @param direction The absolute direction in which to get the tiles.
     * @param range The distance of tiles to return. Since each tile has a distance of 1, the number of tiles returned
     *              is equal to the range.
     * @return A set of all the tiles in the given direction from the current tile up to the given range.
     */
    Set<Tile> getTiles(Compass direction, int range){
        return getTiles(x(), y(), direction, range);
    }

    /**
     * @param refX The x coordinate of the reference tile.
     * @param refY The y coordinate of the reference tile.
     * @param direction The absolute direction in which to get the tiles.
     * @param range The distance of tiles to return. Since each tile has a distance of 1, the number of tiles returned
     *              is equal to the range.
     * @return A set of all the tiles in the given direction from the reference tile up to the given range.
     */
    Set<Tile> getTiles(int refX, int refY, Compass direction, int range) {
        Point reference = point(refX, refY);
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

    /**
     * @param reference The tile whose surrounding adjacent tiles will be returned.
     * @return The set of tiles surrounding the reference tile (does not include the reference tile)
     */
    Set<Tile> getSurroundingTiles(Tile reference) {
        Point coordinates = tilesToPoints.get(reference);
        return getSurroundingTiles(coordinates.x, coordinates.y);
    }

    int distanceToTile(Tile destination) {
        return Math.abs(xDiff(destination)) + Math.abs(yDiff(destination));
    }

    int yDiff(Tile tile) {
        return getX(tile) - x();
    }

    int xDiff(Tile tile) {
        return getY(tile) - y();
    }

    void setX(int x) {
        currentPosition.x = x;
    }

    void setY(int y) {
        currentPosition.y = y;
    }

    int getX(Tile tile) {
        return tilesToPoints.get(tile).x;
    }

    int getY(Tile tile) {
        return tilesToPoints.get(tile).y;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    Set<Tile> getGroundTiles() {
        return groundTiles;
    }

    Compass direction(Tile reference, Tile destination) {
        int xDiff = tilesToPoints.get(destination).x - tilesToPoints.get(reference).x;
        int yDiff = tilesToPoints.get(destination).y - tilesToPoints.get(reference).y;

        if(Math.abs(yDiff) > Math.abs(xDiff)) {
            if(xDiff < 0) {
                return Compass.WEST;
            }
            else {
                return Compass.EAST;
            }
        }
        else if (yDiff < 0){
            return Compass.SOUTH;
        }
        else{
            return Compass.NORTH;
        }
    }

    /*==================
    TileListener methods
    ==================*/

    //TODO: find and fix the bug here
    @Override
    public void typeDiscovered(Tile tile, String previousType, String currentType) {
        switch (currentType){
            case Tile.SEA: seaTiles.add(tile); break;
            case Tile.GROUND: groundTiles.add(tile); break;
            case Tile.UNKNOWN: unknownTiles.add(tile); break;
        }

        if(previousType!=null){
            switch (previousType){
                case Tile.SEA: seaTiles.remove(tile); break;
                case Tile.GROUND: groundTiles.remove(tile); break;
                case Tile.UNKNOWN: unknownTiles.remove(tile); break;
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


    /*=======================================================================
    Point (private class representing coordinates) and private helper methods
    =======================================================================*/

    private static class Point {
        private int x, y;
        private Point(int x, int y) {this.x = x; this.y =y;}
        @Override
        public boolean equals(Object o) {
            if (o == null) { return false; }
            if (!(o instanceof Point)) { return false; }
            if (((Point)o).x != x) return false;
            if (((Point)o).y != y) return false;
            return true;
        }
        @Override
        public int hashCode() {
            return ((x+1) << 16) + (y+1);
        }
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

        if (y() == 1) {
            updateMapThroughEcho(false, vRangeToBorder, Compass.NORTH);
        }
        else{
            updateMapThroughEcho(false, vRangeToBorder, Compass.SOUTH);
        }

        if (x() == 1) {
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
