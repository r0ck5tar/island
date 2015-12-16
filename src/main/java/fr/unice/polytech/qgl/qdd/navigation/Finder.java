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

    //TODO finish this method, then refactor
    public Tile adjacentTileByAir(Direction direction) {
        switch (nav.absoluteDirection(direction)) {
            case NORTH: break;
            case EAST: break;
            case SOUTH: break;
            case WEST: break;
        }
        return null;
    }

    public Set<Tile> allTiles(Direction direction, int range) {
        return map.getTiles(nav.absoluteDirection(direction), range);
    }

    public Set<Tile> allTiles(Direction direction) {
        return map.getTiles(nav.absoluteDirection(direction));
    }

    public Set<Tile> getTilesOnSide(Direction side) {
        Set<Tile> tiles = new HashSet<>();
        Compass absoluteSide = nav.absoluteDirection(side);
        if(absoluteSide == Compass.NORTH || absoluteSide == Compass.SOUTH) {
            for(int x = 0; x < map.getWidth(); x++) {
                tiles.addAll(map.getTiles(x, map.y(), absoluteSide, map.getHeight() - map.y()));
            }
        }
        else{

        }
        return tiles;
    }

    public Tile getRandomNearbyTile(){
        int range = 8;
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


}
