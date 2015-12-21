package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.*;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hbinluqman on 17/12/2015.
 */
public class FinderTester {
    protected Navigator nav;

    private static final int DEFAULT_WIDTH = 18, DEFAULT_HEIGHT = 18, DEFAULT_START_X = 1, DEFAULT_START_Y = 1;
    private static final Compass DEFAULT_START_FACING =  Compass.NORTH;

    private Method createTileMethod;
    private Method getTileMethod;
    private Method setFacingDirectionMethod;
    private Method getXMethod;
    private Method getYMethod;
    private Method setXMethod;
    private Method setYMethod;
    private Field heightField;
    private Field widthField;

    @Before
    public void setupTester() {
        try {
            setFacingDirectionMethod = Navigator.class.getDeclaredMethod("setFacingDirection", Compass.class);
            setFacingDirectionMethod.setAccessible(true);

            createTileMethod = IslandMap.class.getDeclaredMethod("createTile", int.class, int.class);
            createTileMethod.setAccessible(true);

            getTileMethod = IslandMap.class.getDeclaredMethod("getTile", int.class, int.class);
            getTileMethod.setAccessible(true);

            getXMethod = IslandMap.class.getDeclaredMethod("getX", Tile.class);
            getXMethod.setAccessible(true);

            getYMethod = IslandMap.class.getDeclaredMethod("getY", Tile.class);
            getYMethod.setAccessible(true);

            setXMethod = IslandMap.class.getDeclaredMethod("setX", int.class);
            setXMethod.setAccessible(true);

            setYMethod = IslandMap.class.getDeclaredMethod("setY", int.class);
            setYMethod.setAccessible(true);

            heightField = IslandMap.class.getDeclaredField("height");
            heightField.setAccessible(true);

            widthField = IslandMap.class.getDeclaredField("width");
            widthField.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        nav = new Navigator(DEFAULT_START_FACING);
        initializeMap(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_START_X, DEFAULT_START_Y);
    }

    protected void initializeMap(int width, int height, int startX, int startY) {
        nav = new Navigator(DEFAULT_START_FACING);
        setWidth(width);
        setHeight(height);
        setX(startX);
        setY(startY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y< width; y++) {
                createTile(x, y);
            }
        }
    }

    protected void setFacingDirection(Compass direction) {
        try {
            setFacingDirectionMethod.invoke(nav, direction);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Tile getTileMethod(int x, int y) {
        Tile tile = null;
        try {
            tile =  (Tile)getTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return tile;
    }

    protected int getX(Tile tile) {
        try {
            return (int) getXMethod.invoke(nav.map(), tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    protected int getY(Tile tile) {
        try {
            return (int) getYMethod.invoke(nav.map(), tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    protected void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    protected void setY(int y) {
        try {
            setYMethod.invoke(nav.map(), y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void setX(int x) {
        try {
            setXMethod.invoke(nav.map(), x);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Tile getTile(int x, int y) {
        try {
            return (Tile) getTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHeight(int h) {
        try {
            heightField.set(nav.map(), h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setWidth(int w) {
        try {
            widthField.set(nav.map(), w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void createTile(int x, int y) {
        try {
            createTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

