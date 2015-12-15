package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap2;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class GetTileAndGetPointTest extends IslandMapTest {

    @Test
    public void testCreateTile() {
        try {
            createTileMethod.invoke(map, 1, 1);
            Tile tile = (Tile) getTileMethod.invoke(map, 1, 1);

            Assert.assertEquals("UNKNOWN", tile.getType());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTileAndGetTileOnTenByTenMap() {
        createXByYMap(10, 10);

        Tile cornerTile1 = getTileMethod(0, 0);
        Tile cornerTile2 = getTileMethod(0, 9);
        Tile cornerTile3 = getTileMethod(9, 9);
        Tile cornerTile4 = getTileMethod(9, 0);

        Assert.assertNotNull(cornerTile1);
        Assert.assertEquals("UNKNOWN", cornerTile1.getType());

        Assert.assertNotNull(cornerTile2);
        Assert.assertEquals("UNKNOWN", cornerTile2.getType());

        Assert.assertNotNull(cornerTile3);
        Assert.assertEquals("UNKNOWN", cornerTile3.getType());

        Assert.assertNotNull(cornerTile4);
        Assert.assertEquals("UNKNOWN", cornerTile4.getType());
    }

    @Test
    public void testGetCurrentTile() {
        createXByYMap(10, 10);

        //Set tile (5,5) as ground
        getTileMethod(5, 5).setType(GROUND);

        //Set current position to (5,5)
        setCurrentPositionMethod(5, 5);

        //get current position and verify that the returned tile is ground (all other tiles are unknown by default)
        Assert.assertEquals(GROUND, currentTileMethod().getType());
    }

    @Test
    public void testGetTileAtRange() {
        createXByYMap(10, 10);

        //Set tile (9,9) as ground
        getTileMethod(9, 9).setType(GROUND);

        //Get the tile which is 9 tiles north og (9,0)
        Tile tileAtRange9ToNorthOfx9y0 = getRangedTileMethod(9, 0, Compass.NORTH, 9);

        //Verify that tileAtRange9ToNorthOfx9y0 is ground
        Assert.assertEquals(GROUND, tileAtRange9ToNorthOfx9y0.getType());
    }

    @Test
    public void testMapBoundaries() {
        createXByYMap(10, 10);

        Assert.assertNull(getTileMethod(-1, 0));
        Assert.assertNull(getTileMethod(0, 10));
        Assert.assertNull(getTileMethod(10, 0));
    }
}
