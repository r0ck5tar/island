package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public enum Compass {
    NORTH, EAST, SOUTH, WEST;

    public static Compass fromString(String direction) {
        switch (direction) {
            case "N": return NORTH;
            case "E": return EAST;
            case "S": return SOUTH;
            case "W": return WEST;
        }
        return null;
    }

    public String toString() {
        return name().substring(0,1);
    }
}
