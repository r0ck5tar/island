package fr.unice.polytech.qgl.qdd.navigation;

import java.util.*;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class Finder {
    private static Finder instance;
    private IslandMap map;
    private Navigator nav;
    private Random random;

    public Set<Tile> neighbouringTiles() {
        return map.getSurroundingTiles(map.currentTile());
    }

    public Set<Tile> getSurroundingTiles(Tile referenceTile) {
        return map.getSurroundingTiles(referenceTile);
    }

    public Tile getTile(int x, int y) {
        return map.getTile(x, y);
    }

    public Tile adjacentTile(Direction direction) {
        return (Tile) allTiles(direction, 1).toArray()[0];
    }

    //TODO test this method
    public Tile adjacentTileByAir(Direction direction) {
        switch (direction) {
            case FRONT: return frontTileByAir(nav.absoluteDirection(direction));
            case RIGHT: return rightTileByAir(nav.absoluteDirection(direction));
            case LEFT: return leftTileByAir(nav.absoluteDirection(direction));
        }
        return null;
    }

    public Set<Tile> allTiles(Direction direction, int range) {
        return map.getTiles(nav.absoluteDirection(direction), range);
    }

    public Set<Tile> allTiles(Direction direction) {
        return map.getTiles(nav.absoluteDirection(direction));
    }

    //TODO: test this method
    public Set<Tile> getTilesOnSide(Direction side) {
        Set<Tile> tiles = new HashSet<>();
        Compass absoluteSide = nav.absoluteDirection(side);
        if(absoluteSide == Compass.NORTH || absoluteSide == Compass.SOUTH) {
            for(int x = 0; x < map.getWidth(); x++) {
                tiles.addAll(map.getTiles(x, map.y(), absoluteSide));
            }
        }
        else{
            for(int y = 0; y < map.getHeight(); y++) {
                tiles.addAll(map.getTiles(map.x(), y, absoluteSide));
            }
        }
        return tiles;
    }

    public Tile getRandomNearbyTile(){
        int range = 3;
        int minX = map.x() - range < 0 ? 0 : (map.x() - range);
        int maxX = (map.x() + range) >= map.getWidth() ? map.getHeight()-1 : (map.x() + range);
        int minY = map.y() - range < 0 ? 0 : (map.y() - range);
        int maxY = (map.y() + range) >= map.getHeight() ? map.getHeight()-1 : (map.y() + range);
        int randomX = 0, randomY = 0;

        do{
            randomX = minX + random.nextInt(maxX - minX);
            randomY = minY + random.nextInt(maxY - minY);
        }
        while (randomX == map.x() && randomY == map.y());

        return map.getTile(randomX, randomY);
    }

    //Make this not random
    public Tile getRandomUnscannedGroundTile() {
        List<Tile> unscanned = new ArrayList<>();
        for(Tile t: map.getGroundTiles()) {
            if(t.isUnscanned()) {
                unscanned.add(t);
            }
        }
        return unscanned.size()> 0 ? unscanned.get(random.nextInt(unscanned.size())) : null;
    }

    public Direction relativeDirectionOfTile(Tile tile) {
        return nav.relativeDirection(map.direction(map.currentTile(), tile));
    }

    /*=====================================================
    Private and static methods for singleton implementation
     ====================================================*/

    private Finder(IslandMap map, Navigator nav) {
        this.map = map;
        this.nav = nav;
        random = new Random();
    }

    public static void init(IslandMap map, Navigator nav) {
        instance = new Finder(map, nav);
    }

    static Finder getInstance() {
        return instance;
    }

    /*=============
    Private methods
    *============*/

    private Tile frontTileByAir(Compass direction) {
        switch (direction) {
            case NORTH: return map.getTile(map.x(), map.y() + Move.AIR_DISTANCE);
            case EAST: return map.getTile(map.x() + Move.AIR_DISTANCE, map.y());
            case SOUTH: return map.getTile(map.x(), map.y() - Move.AIR_DISTANCE);
            case WEST:return map.getTile(map.x() - Move.AIR_DISTANCE, map.y());
            default: return null;
        }
    }

    private Tile rightTileByAir(Compass direction) {
        switch (direction) {
            case NORTH: return map.getTile(map.x() - Move.AIR_DISTANCE, map.y() + Move.AIR_DISTANCE);
            case EAST: return map.getTile(map.x() + Move.AIR_DISTANCE, map.y() + Move.AIR_DISTANCE);
            case SOUTH: return map.getTile(map.x() + Move.AIR_DISTANCE, map.y() - Move.AIR_DISTANCE);
            case WEST:return map.getTile(map.x() - Move.AIR_DISTANCE, map.y() - Move.AIR_DISTANCE);
            default: return null;
        }
    }

    private Tile leftTileByAir(Compass direction) {
        switch (direction) {
            case NORTH: return map.getTile(map.x() + Move.AIR_DISTANCE, map.y() + Move.AIR_DISTANCE);
            case EAST: return map.getTile(map.x() + Move.AIR_DISTANCE, map.y() - Move.AIR_DISTANCE);
            case SOUTH: return map.getTile(map.x() - Move.AIR_DISTANCE, map.y() - Move.AIR_DISTANCE);
            case WEST:return map.getTile(map.x() - Move.AIR_DISTANCE, map.y() + Move.AIR_DISTANCE);
            default: return null;
        }
    }
}
