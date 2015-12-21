package fr.unice.polytech.qgl.qdd.navigation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class Finder {
    private static Finder instance;
    private IslandMap map;
    private Navigator nav;
    private Random random;

    private static final int TURNING_BUFFER = Move.AIR_DISTANCE + 1;
    private static final int BORDER_BUFFER = 2 * Move.AIR_DISTANCE;

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

    public Tile getRandomNearbyTile(int range) {
        int minX = map.x() - range < 0 ? 0 : (map.x() - range);
        int maxX = (map.x() + range) >= map.getWidth() ? map.getHeight()-1 : (map.x() + range);
        int minY = map.y() - range < 0 ? 0 : (map.y() - range);
        int maxY = (map.y() + range) >= map.getHeight() ? map.getHeight()-1 : (map.y() + range);
        int randomX = 0, randomY = 0;

        do{
            randomX = minX + random.nextInt(maxX - minX);
            randomY = minY + random.nextInt(maxY - minY);
        }
        while (randomX == map.x() && randomY == map.y() || map.getTile(randomX, randomY) == null);

        return map.getTile(randomX, randomY);
    }
    public Tile getRandomNearbyTile(){
        return getRandomNearbyTile(3);
    }

    /*
    Chooses the turning direction when flying when the tile is at the BACK of the drone.
    */
    public Compass chooseTurningDirection(Tile destinationTile) {
        if(nav.front() == Compass.NORTH || nav.front() == Compass.SOUTH) {
            return nav.map().xDiff(destinationTile) < 0 ? nav.left() : nav.right();
        }
        else{
            return nav.map().yDiff(destinationTile) < 0 ? nav.left() : nav.right();
        }
    }

    //TODO: Test this method
    public Tile getNearestUnscannedGroundTile() {
        List<Tile> unscanned = new ArrayList<>();
        map.getGroundTiles().stream().filter(tile -> tile.isUnscanned()).forEach(tile -> unscanned.add(tile));
        return unscanned.stream().min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2))).orElse(getRandomNearbyTile());
    }

    public Direction relativeDirectionOfTile(Tile tile) {
        return nav.relativeDirection(map.direction(map.currentTile(), tile));
    }

    public Direction relativeDirectionOfTileByAir(Tile tile) {
        int x = map.x(), y = map.y();
        int xDiff = map.xDiff(tile), yDiff = map.yDiff(tile);
        boolean verticallyAligned = map.isVerticallyAligned(tile), horizontallyAligned = map.isHorizontallyAligned(tile);

        if (nav.front() == Compass.NORTH) {
            if (verticallyAligned && yDiff > 0 && y < map.height() - BORDER_BUFFER) {
                return Direction.FRONT;
            }
            else if (horizontallyAligned && Math.abs(xDiff) <= TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (yDiff > TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (xDiff >= 1 && x < map.width() - BORDER_BUFFER) {
                return Direction.RIGHT;
            }
            else{
                return Direction.LEFT;
            }
        }
        else if (nav.front() == Compass.EAST) {
            if (horizontallyAligned && xDiff > 0 && x < map.width() - BORDER_BUFFER) {
                return Direction.FRONT;
            }
            else if (verticallyAligned && Math.abs(yDiff) <= TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (xDiff > TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (yDiff <= -1 && y > BORDER_BUFFER) {
                return Direction.RIGHT;
            }
            else {
                return Direction.LEFT;
            }
        }
        else if (nav.front() == Compass.SOUTH) {
            if (verticallyAligned && yDiff < 0 && y > BORDER_BUFFER) {
                return Direction.FRONT;
            }
            else if (horizontallyAligned && Math.abs(xDiff) <= TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (yDiff < -TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (xDiff <= -1 && x < map.width() - BORDER_BUFFER) {
                return Direction.RIGHT;
            }
            else{
                return Direction.LEFT;
            }
        }
        else {
            if (horizontallyAligned && xDiff < 0 && x > BORDER_BUFFER) {
                return Direction.FRONT;
            }
            else if (verticallyAligned && Math.abs(yDiff) <= TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (xDiff < -TURNING_BUFFER) {
                return Direction.FRONT;
            }
            else if (yDiff >= 1 && y < map.height() - BORDER_BUFFER) {
                return Direction.RIGHT;
            }
            else{
                return Direction.LEFT;
            }
        }
    }


    public boolean detectShore(Direction direction) {
        Set<Tile> tiles = map.getTiles(nav.absoluteDirection(direction));
        Set<Tile> groundTiles = tiles.stream().filter(Tile::isGround).collect(Collectors.toSet());
        Set<Tile> seaTiles = tiles.stream().filter(Tile::isSea).collect(Collectors.toSet());
        Set<Tile> unknownTiles = tiles.stream().filter(Tile::isUnknown).collect(Collectors.toSet());
        int furthestGroundTile, nearestSeaTile;

        //We cannot be sure that there is a shore if we have not detected any ground or sea tiles.
        if(groundTiles.size() == 0 && seaTiles.size() == 0 || seaTiles.size() == tiles.size()) {
            return false;
        }

        switch(nav.absoluteDirection(direction)) {
            case NORTH:
                furthestGroundTile = groundTiles.stream().mapToInt(map::getY).max().orElse(-1);
                nearestSeaTile = seaTiles.stream().mapToInt(map::getY).min().getAsInt();

                if(furthestGroundTile < nearestSeaTile) { return true; }

                if (unknownTiles.stream().mapToInt(map::getY).
                        filter(distance -> distance > furthestGroundTile).toArray().length > 0) {
                    return true;
                }
                break;
            case EAST:
                furthestGroundTile = groundTiles.stream().mapToInt(map::getX).max().getAsInt();
                nearestSeaTile = seaTiles.stream().mapToInt(map::getX).min().getAsInt();

                if(furthestGroundTile < nearestSeaTile) { return true; }

                if (unknownTiles.stream().mapToInt(map::getX).
                        filter(distance -> distance > furthestGroundTile).toArray().length > 0) {
                    return true;
                }
                break;
            case SOUTH:
                furthestGroundTile = groundTiles.stream().mapToInt(map::getY).min().getAsInt();
                nearestSeaTile = seaTiles.stream().mapToInt(map::getY).max().getAsInt();

                if(furthestGroundTile > nearestSeaTile) { return true; }

                if (unknownTiles.stream().mapToInt(map::getY).
                        filter(distance -> distance < furthestGroundTile).toArray().length > 0) {
                    return true;
                }
                break;
            case WEST:
                furthestGroundTile = groundTiles.stream().mapToInt(map::getX).min().getAsInt();
                nearestSeaTile = seaTiles.stream().mapToInt(map::getX).max().getAsInt();

                if(furthestGroundTile > nearestSeaTile) { return true; }

                if (unknownTiles.stream().mapToInt(map::getX).
                        filter(distance -> distance < furthestGroundTile).toArray().length > 0) {
                    return true;
                }
                break;
        }
        return false;
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
            case NORTH: return map.getTile(map.x(), map.y() + 1);
            case EAST: return map.getTile(map.x() + 1, map.y());
            case SOUTH: return map.getTile(map.x(), map.y() - 1);
            case WEST:return map.getTile(map.x() - 1, map.y());
            default: return null;
        }
    }

    private Tile rightTileByAir(Compass direction) {
        switch (direction) {
            case NORTH: return map.getTile(map.x() - 1, map.y() + 1);
            case EAST: return map.getTile(map.x() + 1, map.y() + 1);
            case SOUTH: return map.getTile(map.x() + 1, map.y() - 1);
            case WEST:return map.getTile(map.x() - 1, map.y() - 1);
            default: return null;
        }
    }

    private Tile leftTileByAir(Compass direction) {
        switch (direction) {
            case NORTH: return map.getTile(map.x() + 1, map.y() + 1);
            case EAST: return map.getTile(map.x() + 1, map.y() - 1);
            case SOUTH: return map.getTile(map.x() - 1, map.y() - 1);
            case WEST:return map.getTile(map.x() - 1, map.y() + 1);
            default: return null;
        }
    }
}
