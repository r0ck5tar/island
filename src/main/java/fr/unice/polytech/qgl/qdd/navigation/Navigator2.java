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
    }

    public Move move() {
        return Move.facing(facing);
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
    }
}
