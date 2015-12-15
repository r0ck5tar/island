package fr.unice.polytech.qgl.qdd.navigation;

import java.util.*;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class Finder {
    private static Finder instance;
    private IslandMap2 map;
    private Navigator2 nav;
    private Random random;

    public Tile currentTile() {
        return map.currentTile();
    }

    public Set<Tile> neighbouringTiles() {
        return map.getSurroundingTiles(map.currentTile());
    }

    public Set<Tile> getSurroundingTiles(Tile referenceTile) {
        return map.getSurroundingTiles(referenceTile);
    }

    public Set<Tile> adjacentTile(Direction direction) {
        return allTiles(direction, 1);
    }

    public Set<Tile> allTiles(Direction direction, int range) {
        return map.getTiles(nav.absoluteDirection(direction), range);
    }

    public Set<Tile> getTilesOnSide(Direction side) {
        Set<Tile> tiles = new HashSet<>();
        Compass absoluteSide = nav.absoluteDirection(side);
        if(absoluteSide == Compass.NORTH || absoluteSide == Compass.SOUTH) {
            for(int x = 0; x < map.getWidth(); x++) {
                tiles.addAll(map.getTiles(x, map.getY(), absoluteSide, map.getHeight() - map.getY()));
            }
        }
        else{

        }
        return tiles;
    }

    public Tile getRandomNearbyTile(){
        int range = 3;
        int minX = map.getX() - range < 0 ? 0 : (map.getX() - range);
        int maxX = (map.getX() + range) > map.getWidth() ? map.getHeight() : (map.getX() + range);
        int minY = map.getY() - range < 0 ? 0 : (map.getY() - range);
        int maxY = (map.getY() + range) > map.getHeight() ? map.getHeight() : (map.getY() + range);
        int randomX = 0, randomY = 0;

        do{
            randomX = minX + random.nextInt(maxX - minX);
            randomY = minY + random.nextInt(maxY - minY);
        }
        while (randomX == map.getX() && randomY == map.getY());

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
        return nav.relativeDirection(map.direction(currentTile(), tile));
    }

    /*=====================================================
    Private and static methods for singleton implementation
     ====================================================*/

    private Finder(IslandMap2 map, Navigator2 nav) {
        this.map = map;
        this.nav = nav;
        random = new Random();
    }

    public static void init(IslandMap2 map, Navigator2 nav) {
        instance = new Finder(map, nav);
    }

    static Finder getInstance() {
        return instance;
    }


}
