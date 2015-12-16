package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class FinderTest {
    protected Navigator nav;
    protected IslandMap map;

    protected static final int HEIGHT = 18, WIDTH = 18;

    private Method getTileMethod;
    private Method setFacingDirectionMethod;
    private Method getXMethod;
    private Method getYMethod;
    private Field mapField;

    @Before
    public void setup() {
        nav = new Navigator(Compass.NORTH);

        try {
            setFacingDirectionMethod = Navigator.class.getDeclaredMethod("setFacingDirection", Compass.class);
            setFacingDirectionMethod.setAccessible(true);

            getTileMethod = IslandMap.class.getDeclaredMethod("getTile", int.class, int.class);
            getTileMethod.setAccessible(true);

            getXMethod = IslandMap.class.getDeclaredMethod("getX", Tile.class);
            getXMethod.setAccessible(true);

            getYMethod = IslandMap.class.getDeclaredMethod("getY", Tile.class);
            getYMethod.setAccessible(true);

            mapField = Navigator.class.getDeclaredField("map");
            mapField.setAccessible(true);

            map = (IslandMap) mapField.get(nav);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRandomNearbyTile() {
        map.initializeMapThroughEcho(Compass.NORTH, HEIGHT/3 - 1);
        map.initializeMapThroughEcho(Compass.EAST, WIDTH/3 - 1);

        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(1, getX(currentTile));
        Assert.assertEquals(1, getY(currentTile));

        Set<Tile> randomNearbyTiles = new HashSet<>();
        for(int i = 0; i < 50; i++) {
            randomNearbyTiles.add(nav.finder().getRandomNearbyTile());
        }

        for(Tile t: randomNearbyTiles) {
            Assert.assertTrue(getX(t) >= 0 && getX(t) <= 9);
            Assert.assertTrue(getY(t) >= 0 && getY(t) <= 9);
        }

        System.out.print("hello");
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

    protected void setFacingDirection(Compass direction) {
        try {
            setFacingDirectionMethod.invoke(nav, direction);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    int getX(Tile tile) {
        try {
            return (int) getXMethod.invoke(map, tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    int getY(Tile tile) {
        try {
            return (int) getYMethod.invoke(map, tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
