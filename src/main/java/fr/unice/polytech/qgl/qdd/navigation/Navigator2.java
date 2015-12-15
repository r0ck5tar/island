package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class Navigator2 {
    private IslandMap2 map;
    private Compass facing;

    public Navigator2(Compass facing) {
        this.facing = facing;
        map = new IslandMap2();
        Move.init(map, this);
        Finder.init(map, this);
    }

    public Move move() {
        return Move.facing(facing);
    }

    public Finder finder() {
        return Finder.getInstance();
    }

    public Compass front() {
        return facing;
    }

    public Compass back() {
        return RelativeDirection.valueOf("FACING_".concat(facing.toString())).back;
    }

    public Compass right() {
        return RelativeDirection.valueOf("FACING_".concat(facing.toString())).right;
    }

    public Compass left() {
        return RelativeDirection.valueOf("FACING_".concat(facing.toString())).left;
    }

    void setFacingDirection(Compass facing) {
        this.facing = facing;
    }

    Compass absoluteDirection(Direction relativeDirection) {
        return RelativeDirection.valueOf("FACING_".concat(facing.toString())).getDirection(relativeDirection);
    }

    Direction relativeDirection(Compass absoluteDirection) {
        switch (absoluteDirection.name().concat("_FROM_").concat(facing.name())) {
            case "NORTH_FROM_NORTH" : //fallthrough
            case "EAST_FROM_EAST"   : //fallthrough
            case "SOUTH_FROM_SOUTH" : //fallthrough
            case "WEST_FROM_WEST"   : return Direction.FRONT;

            case "EAST_FROM_NORTH" : //fallthrough
            case "SOUTH_FROM_EAST" : //fallthrough
            case "WEST_FROM_SOUTH" : //fallthrough
            case "NORTH_FROM_WEST" : return Direction.RIGHT;

            case "NORTH_FROM_EAST" : //fallthrough
            case "EAST_FROM_SOUTH" : //fallthrough
            case "SOUTH_FROM_WEST" : //fallthrough
            case "WEST_FROM_NORTH" : return Direction.LEFT;

            case "NORTH_FROM_SOUTH" : //fallthrough
            case "SOUTH_FROM_NORTH" : //fallthrough
            case "EAST_FROM_WEST"   : //fallthrough
            case "WEST_FROM_EAST"   : return Direction.BACK;

            default : return null;
        }
    }

    private enum RelativeDirection {
        FACING_NORTH(Compass.NORTH, Compass.EAST, Compass.SOUTH, Compass.WEST),
        FACING_EAST(Compass.EAST, Compass.SOUTH, Compass.WEST, Compass.NORTH),
        FACING_SOUTH(Compass.SOUTH, Compass.WEST, Compass.NORTH, Compass.EAST),
        FACING_WEST(Compass.WEST, Compass.NORTH, Compass.EAST, Compass.SOUTH);
        public Compass front, right, back, left;

        RelativeDirection(Compass front, Compass right, Compass back, Compass left){
            this.front = front;
            this.right = right;
            this.back = back;
            this.left = left;
        }

        Compass getDirection(Direction direction) {
            switch (direction) {
                case FRONT: return front;
                case RIGHT: return right;
                case LEFT: return left;
                case BACK: return back;
                default: return null;
            }
        }
    }
}
