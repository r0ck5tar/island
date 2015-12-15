package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap2;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class IslandMapTest {
    protected IslandMap2 map;

    /*
    IslandMap private methods and fields
     */
    protected Method createTileMethod;
    protected Method getTileMethod;
    protected Method getRangedTileMethod;
    protected Method getSurroundingTilesMethod;
    protected Method getTilesNorthMethod;
    protected Method getTilesEastMethod;
    protected Method getTilesSouthMethod;
    protected Method getTilesWestMethod;
    protected Method currentTileMethod;
    protected Method setCurrentPositionMethod;
    protected Field heightField;
    protected Field widthField;

    /*
    Point private class
     */
    protected Class pointClass;
    protected Constructor pointConstructor;

    /*
    constants
    */
    protected static final String GROUND = "GROUND", UNKNOWN = "UNKNOWN", SEA = "SEA";
    protected static final String NORTH = "NORTH", EAST = "EAST", SOUTH = "SOUTH", WEST = "WEST";


    @Before
    public void setup() {
        map = new IslandMap2();

        for(Class c : IslandMap2.class.getDeclaredClasses()) {
            if (c.getSimpleName().equals("Point")) {
                pointClass = c;
            }
        }
        try {
            createTileMethod = IslandMap2.class.getDeclaredMethod("createTile", int.class, int.class);
            createTileMethod.setAccessible(true);

            getSurroundingTilesMethod = IslandMap2.class.getDeclaredMethod("getSurroundingTiles", int.class, int.class);
            getSurroundingTilesMethod.setAccessible(true);

            getTileMethod = IslandMap2.class.getDeclaredMethod("getTile", int.class, int.class);
            getTileMethod.setAccessible(true);

            getRangedTileMethod = IslandMap2.class.getDeclaredMethod("getTile", pointClass, Compass.class, int.class);
            getRangedTileMethod.setAccessible(true);

            getTilesNorthMethod = IslandMap2.class.getDeclaredMethod("getTilesNorth", pointClass, int.class);
            getTilesNorthMethod.setAccessible(true);

            getTilesEastMethod = IslandMap2.class.getDeclaredMethod("getTilesEast", pointClass, int.class);
            getTilesEastMethod.setAccessible(true);

            getTilesSouthMethod = IslandMap2.class.getDeclaredMethod("getTilesSouth", pointClass, int.class);
            getTilesSouthMethod.setAccessible(true);

            getTilesWestMethod = IslandMap2.class.getDeclaredMethod("getTilesWest", pointClass, int.class);
            getTilesWestMethod.setAccessible(true);

            setCurrentPositionMethod = IslandMap2.class.getDeclaredMethod("setCurrentPosition", pointClass);
            setCurrentPositionMethod.setAccessible(true);

            currentTileMethod = IslandMap2.class.getDeclaredMethod("currentTile");
            currentTileMethod.setAccessible(true);

            heightField = IslandMap2.class.getDeclaredField("height");
            heightField.setAccessible(true);

            widthField = IslandMap2.class.getDeclaredField("width");
            widthField.setAccessible(true);

            pointConstructor = pointClass.getDeclaredConstructor(int.class, int.class);
            pointConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    protected void createXByYMap(int x, int y) {
        try {
            for(int i = 0; i < x; i++) {
                for(int j = 0; j < y; j++) {
                    createTileMethod.invoke(map, i, j);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Tile getTileMethod(int x, int y) {
        Tile tile = null;
        try {
            tile =  (Tile)getTileMethod.invoke(map, x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return tile;
    }

    protected Tile getRangedTileMethod(int refX, int refY, Compass direction, int range) {
        Tile tile = null;
        try {
            tile =  (Tile)getRangedTileMethod.invoke(map, point(refX, refY), direction, range);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return tile;
    }

    protected Object point(int x, int y) {
        Object point = null;
        try {
            return pointConstructor.newInstance(x, y);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return point;
    }

    protected void setCurrentPositionMethod(int x, int y) {
        try {
            setCurrentPositionMethod.invoke(map, point(x,y));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Tile currentTileMethod() {
        Tile tile = null;
        try {
            tile = (Tile)currentTileMethod.invoke(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return tile;
    }

    protected Set<Tile> getTilesInDirectionMethod(String direction, int x, int y, int range) {
        Set<Tile> tiles = null;
        try {
            switch (direction) {
                case "NORTH": tiles = (Set<Tile>) getTilesNorthMethod.invoke(map, point(x, y), range); break;
                case "EAST": tiles = (Set<Tile>) getTilesEastMethod.invoke(map, point(x, y), range); break;
                case "SOUTH": tiles = (Set<Tile>) getTilesSouthMethod.invoke(map, point(x, y), range); break;
                case "WEST": tiles = (Set<Tile>) getTilesWestMethod.invoke(map, point(x, y), range); break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return tiles;
    }


    protected void setHeightAndWidth(int h, int w) {
        try {
            heightField.set(map, h);
            widthField.set(map, w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
