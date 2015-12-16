package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Before;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class IslandMapTest {
    protected IslandMap map;

    /*
    IslandMap private methods and fields
     */
    private Method createTileMethod;
    private Method getTileMethod;
    private Method getRangedTileMethod;
    private Method getSurroundingTilesMethod;
    private Method getTilesNorthMethod;
    private Method getTilesEastMethod;
    private Method getTilesSouthMethod;
    private Method getTilesWestMethod;
    private Method currentTileMethod;
    private Method setXMethod;
    private Method setYMethod;
    private Field heightField;
    private Field widthField;

    /*
    Point private class
     */
    private Class pointClass;
    private Constructor pointConstructor;

    /*
    constants
    */
    protected static final String GROUND = "GROUND", UNKNOWN = "UNKNOWN", SEA = "SEA";
    protected static final String NORTH = "NORTH", EAST = "EAST", SOUTH = "SOUTH", WEST = "WEST";


    @Before
    public void setup() {
        map = new IslandMap();

        for(Class c : IslandMap.class.getDeclaredClasses()) {
            if (c.getSimpleName().equals("Point")) {
                pointClass = c;
            }
        }
        try {
            createTileMethod = IslandMap.class.getDeclaredMethod("createTile", int.class, int.class);
            createTileMethod.setAccessible(true);

            getSurroundingTilesMethod = IslandMap.class.getDeclaredMethod("getSurroundingTiles", int.class, int.class);
            getSurroundingTilesMethod.setAccessible(true);

            getTileMethod = IslandMap.class.getDeclaredMethod("getTile", int.class, int.class);
            getTileMethod.setAccessible(true);

            getRangedTileMethod = IslandMap.class.getDeclaredMethod("getTile", Tile.class, Compass.class, int.class);
            getRangedTileMethod.setAccessible(true);

            getTilesNorthMethod = IslandMap.class.getDeclaredMethod("getTilesNorth", pointClass, int.class);
            getTilesNorthMethod.setAccessible(true);

            getTilesEastMethod = IslandMap.class.getDeclaredMethod("getTilesEast", pointClass, int.class);
            getTilesEastMethod.setAccessible(true);

            getTilesSouthMethod = IslandMap.class.getDeclaredMethod("getTilesSouth", pointClass, int.class);
            getTilesSouthMethod.setAccessible(true);

            getTilesWestMethod = IslandMap.class.getDeclaredMethod("getTilesWest", pointClass, int.class);
            getTilesWestMethod.setAccessible(true);

            setXMethod = IslandMap.class.getDeclaredMethod("setX", int.class);
            setXMethod.setAccessible(true);

            setYMethod = IslandMap.class.getDeclaredMethod("setY", int.class);
            setYMethod.setAccessible(true);

            currentTileMethod = IslandMap.class.getDeclaredMethod("currentTile");
            currentTileMethod.setAccessible(true);

            heightField = IslandMap.class.getDeclaredField("height");
            heightField.setAccessible(true);

            widthField = IslandMap.class.getDeclaredField("width");
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
            setHeightAndWidth(x, y);
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

    protected Set<Tile> getSurroundingTilesMethod (int x, int y) {
        Set<Tile> tiles = null;
        try {
            tiles = (Set<Tile>) getSurroundingTilesMethod.invoke(map, x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    protected Tile getRangedTileMethod(Tile referenceTile, Compass direction, int range) {
        Tile tile = null;
        try {
            tile =  (Tile)getRangedTileMethod.invoke(map, referenceTile, direction, range);
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
            setXMethod.invoke(map, x);
            setYMethod.invoke(map, y);
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

    private void setHeightAndWidth(int h, int w) {
        try {
            heightField.set(map, h);
            widthField.set(map, w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
