package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class Move {
    private static Move instance;
    private IslandMap2 map;
    private Navigator2 nav;
    private Compass facing;

    private static final int AIR_DISTANCE = 3, LAND_DISTANCE = 1;

    /*======================================
    Air movements using the drone in Phase 1
    ======================================*/

    public void fly() {
        travel(AIR_DISTANCE);
    }

    public void turn(Compass direction) {
        switch(facing.name().concat("_").concat(direction.name())) {
            case "NORTH_EAST" : //fallthrough
            case "EAST_NORTH" : setX(x() + AIR_DISTANCE); setY(y() + AIR_DISTANCE); break;

            case "SOUTH_WEST" : //fallthrough
            case "WEST_SOUTH" : setX(x() - AIR_DISTANCE); setY(y() - AIR_DISTANCE); break;

            case "NORTH_WEST" : //fallthrough
            case "WEST_NORTH" : setX(x() - AIR_DISTANCE); setY(y() + AIR_DISTANCE); break;

            case "SOUTH_EAST" : //fallthrough
            case "EAST_SOUTH" : setX(x() + AIR_DISTANCE); setY(y() - AIR_DISTANCE); break;
        }
        nav.setFacingDirection(direction);
    }

    /*======================
    Land movement in Phase 2
    =======================*/

    public void walk() {
        travel(LAND_DISTANCE);
    }

    private void travel(int distance) {
        switch (facing) {
            case NORTH: setY(y() + distance); break;
            case EAST: setX(x() + distance); break;
            case SOUTH: setY(y() - distance); break;
            case WEST: setX(x() - distance); break;
        }
    }


    /*=====================================================
    Private and static methods for singleton implementation
     ====================================================*/

    private Move(IslandMap2 map, Navigator2 nav) {
        this.map = map;
        this.nav = nav;
    }

    static void init(IslandMap2 map, Navigator2 nav) {
        instance = new Move(map, nav);
    }

    static Move facing(Compass facing) {
        instance.facing = facing;
        return instance;
    }

    private int x() {
        return map.getX();
    }

    private int y() {
        return map.getY();
    }

    private void setX(int x) {
        map.setX(x);
    }

    private void setY(int y) {
        map.setY(y);
    }
}
