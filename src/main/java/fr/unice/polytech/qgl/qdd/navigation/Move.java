package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class Move {
    private static Move instance;
    private IslandMap map;
    private Navigator nav;
    private Compass facing;

    static final int AIR_DISTANCE = 3, LAND_DISTANCE = 1;

    /*======================================
    Air movements using the drone in Phase 1
    ======================================*/

    public void fly() {
        switch (facing) {
            case NORTH: setY(map.y() + AIR_DISTANCE); break;
            case EAST: setX(map.x() + AIR_DISTANCE); break;
            case SOUTH: setY(map.y() - AIR_DISTANCE); break;
            case WEST: setX(map.x() - AIR_DISTANCE); break;
        }
    }

    public void turn(Compass direction) {
        switch(facing.name().concat("_").concat(direction.name())) {
            case "NORTH_EAST" : //fallthrough
            case "EAST_NORTH" : setX(map.x() + AIR_DISTANCE); setY(map.y() + AIR_DISTANCE); break;

            case "SOUTH_WEST" : //fallthrough
            case "WEST_SOUTH" : setX(map.x() - AIR_DISTANCE); setY(map.y() - AIR_DISTANCE); break;

            case "NORTH_WEST" : //fallthrough
            case "WEST_NORTH" : setX(map.x() - AIR_DISTANCE); setY(map.y() + AIR_DISTANCE); break;

            case "SOUTH_EAST" : //fallthrough
            case "EAST_SOUTH" : setX(map.x() + AIR_DISTANCE); setY(map.y() - AIR_DISTANCE); break;
        }
        nav.setFacingDirection(direction);
    }

    /*======================
    Land movement in Phase 2
    =======================*/

    public void walk(Compass direction) {
        switch (direction) {
            case NORTH: setY(map.y() + LAND_DISTANCE); break;
            case EAST: setX(map.x() + LAND_DISTANCE); break;
            case SOUTH: setY(map.y() - LAND_DISTANCE); break;
            case WEST: setX(map.x() - LAND_DISTANCE); break;
        }
        nav.setFacingDirection(direction);
    }

    /*=====================================================
    Private and static methods for singleton implementation
     ====================================================*/

    private Move(IslandMap map, Navigator nav) {
        this.map = map;
        this.nav = nav;
    }

    static void init(IslandMap map, Navigator nav) {
        instance = new Move(map, nav);
    }

    static Move facing(Compass facing) {
        instance.facing = facing;
        return instance;
    }
    

    private void setX(int x) {
        map.setX(x);
    }

    private void setY(int y) {
        map.setY(y);
    }
}
