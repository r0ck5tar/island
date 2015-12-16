package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class InitializeMapThroughEchoTest {
    private IslandMap map;
    private static final int INITIAL_WIDTH = 3, INITIAL_HEIGHT = 3;
    private static final int WIDTH = 30, HEIGHT = 30, RANGE = 9, OUT_OF_BOUND_RANGE_ZERO = 0;
    private static final int SOUTH_BORDER = 1, WEST_BORDER = 1, NORTH_BORDER = 28, EAST_BORDER = 28;

    @Before
    public void setup(){
        map = new IslandMap();

        try {
            getHeightMethod = IslandMap.class.getDeclaredMethod("getHeight");
            getHeightMethod.setAccessible(true);

            getWidthMethod = IslandMap.class.getDeclaredMethod("getWidth");
            getWidthMethod.setAccessible(true);

            getXMethod = IslandMap.class.getDeclaredMethod("x");
            getXMethod.setAccessible(true);

            getYMethod = IslandMap.class.getDeclaredMethod("y");
            getYMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInitialDimensions() {
        /*=================
         Before initializing
         =================*/
        //Verify that the initial height is 3 and the initial width is 3
        Assert.assertEquals(INITIAL_HEIGHT, getHeight());
        Assert.assertEquals(INITIAL_WIDTH, getWidth());
    }

    @Test
    public void testStartingAtSouthWestCorner() {
        /*===================
        Echo north, range > 0
        ====================*/
        map.initializeMapThroughEcho(Compass.NORTH, RANGE);

        //Verify that the height is updated correctly after echoing north
        Assert.assertEquals("Height should be " + HEIGHT + " after initializing", HEIGHT, getHeight());
        //Verify that the width is unchanged after echoing north
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*=================
         Echo west, range 0
         =================*/
        map.initializeMapThroughEcho(Compass.WEST, OUT_OF_BOUND_RANGE_ZERO);
        //Verify that the width is unchanged after echoing west
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*==================
        Echo east, range > 0
         =================*/
        map.initializeMapThroughEcho(Compass.EAST, RANGE);

        //Verify that the width is updated correctly after echoing east
        Assert.assertEquals("Width should be " + WIDTH + " after initializing", WIDTH, getWidth());

        //Verify that the initial position is correctly identified as (1,1)
        Assert.assertEquals(WEST_BORDER, getCurrentX());
        Assert.assertEquals(SOUTH_BORDER, getCurrentY());
    }

    @Test
    public void testStartingAtSouthEastCorner() {
        /*====================
         Echo north, range > 0
         ===================*/
        map.initializeMapThroughEcho(Compass.NORTH, RANGE);

        //Verify that the height is updated correctly after echoing north
        Assert.assertEquals("Height should be " + HEIGHT + " after initializing", HEIGHT, getHeight());
        //Verify that the width is unchanged after echoing north
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*=================
         Echo west, range 0
         =================*/
        map.initializeMapThroughEcho(Compass.EAST, OUT_OF_BOUND_RANGE_ZERO);
        //Verify that the width is unchanged after echoing east
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*===================
         Echo east, range > 0
         ==================*/
        map.initializeMapThroughEcho(Compass.WEST, RANGE);

        //Verify that the width is updated correctly after echoing west
        Assert.assertEquals("Width should be " + WIDTH + " after initializing", WIDTH, getWidth());

        //Verify that the initial position is correctly identified as (28,1)
        Assert.assertEquals(EAST_BORDER, getCurrentX());
        Assert.assertEquals(SOUTH_BORDER, getCurrentY());
    }

    @Test
    public void testStartingAtNorthWestCorner() {
        /*====================
         Echo north, range = 0
         ===================*/
        map.initializeMapThroughEcho(Compass.NORTH, OUT_OF_BOUND_RANGE_ZERO);

        //Verify that the height is unchanged after echoing north
        Assert.assertEquals(INITIAL_HEIGHT, getHeight());

        /*====================
         Echo south, range > 0
         ===================*/
        map.initializeMapThroughEcho(Compass.SOUTH, RANGE);

        //Verify that the height is updated correctly after echoing south
        Assert.assertEquals("Height should be " + HEIGHT + " after initializing", HEIGHT, getHeight());
        //Verify that the width is unchanged after echoing south
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*===================
         Echo east, range > 0
         ==================*/
        map.initializeMapThroughEcho(Compass.EAST, RANGE);

        //Verify that the width is updated correctly after echoing east
        Assert.assertEquals("Width should be " + WIDTH + " after initializing", WIDTH, getWidth());

        //Verify that the initial position is correctly identified as (0,28)
        Assert.assertEquals(WEST_BORDER, getCurrentX());
        Assert.assertEquals(NORTH_BORDER, getCurrentY());
    }

    @Test
    public void testStartingAtNorthEastCorner() {
        /*====================
         Echo south, range > 0
         ====================*/
        map.initializeMapThroughEcho(Compass.SOUTH, RANGE);

        //Verify that the height is updated correctly after echoing south
        Assert.assertEquals("Height should be " + HEIGHT + " after initializing", HEIGHT, getHeight());
        //Verify that the width is unchanged after echoing south
        Assert.assertEquals(INITIAL_WIDTH, getWidth());

        /*===================
         Echo west, range > 0
         ==================*/
        map.initializeMapThroughEcho(Compass.WEST, RANGE);

        //Verify that the width is updated correctly after echoing west
        Assert.assertEquals("Width should be " + WIDTH + " after initializing", WIDTH, getWidth());

        //Verify that the initial position is correctly identified as (28,28)
        Assert.assertEquals(EAST_BORDER, getCurrentX());
        Assert.assertEquals(NORTH_BORDER, getCurrentY());
    }


    /*========================================
    getHeight, getWidth, x and y methods
    ==========================================*/
    private Method getHeightMethod;
    private Method getWidthMethod;
    private Method getXMethod;
    private Method getYMethod;
    private int getHeight() {
        try {
            return (int) getHeightMethod.invoke(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int getWidth() {
        try {
            return (int) getWidthMethod.invoke(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int getCurrentX() {
        try {
            return (int) getXMethod.invoke(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private int getCurrentY() {
        try {
            return (int) getYMethod.invoke(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
