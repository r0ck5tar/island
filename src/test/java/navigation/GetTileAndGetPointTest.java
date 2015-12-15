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
        getTileMethod(5, 5).setGround();

        //Set current position to (5,5)
        setCurrentPositionMethod(5, 5);

        //get current position and verify that the returned tile is ground (all other tiles are unknown by default)
        Assert.assertEquals(GROUND, currentTileMethod().getType());
    }

    @Test
    public void testGetTileAtRange() {
        createXByYMap(10, 10);

        //Set tile (9,9) as ground
        getTileMethod(9, 9).setGround();

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

    @Test
    public void testGetSurroundTiles() {
        createXByYMap(10, 10);

        //Set (1,1) as ground and the surrounding tiles as sea.
        getTileMethod(0,0).setSea();
        getTileMethod(0,1).setSea();
        getTileMethod(0,2).setSea();
        getTileMethod(1,0).setSea();
        getTileMethod(1,1).setGround();
        getTileMethod(1,2).setSea();
        getTileMethod(2,0).setSea();
        getTileMethod(2,1).setSea();
        getTileMethod(2,2).setSea();

        //Verify that the tile (1,1) is not included when getting the tiles surrounding it.
        Set<Tile> tilesSurroundingx1y1 = getSurroundingTilesMethod(1, 1);
        Assert.assertEquals(8, tilesSurroundingx1y1.size());

        for(Tile t: tilesSurroundingx1y1) {
            Assert.assertEquals(SEA, t.getType());
        }

        //Verify that (1,1) is ground
        Assert.assertEquals(GROUND, getTileMethod(1,1).getType());
    }

    @Test
    public void testGetSurroundTilesAtBorder() {
        createXByYMap(10, 10);

        //Set (0,0) as ground and the surrounding tiles as sea.
        getTileMethod(0,0).setGround();
        getTileMethod(0,1).setSea();
        getTileMethod(1,0).setSea();
        getTileMethod(1,1).setSea();


        //Verify that the tile (0,0) is not included when getting the tiles surrounding it.
        Set<Tile> tilesSurroundingx0y0 = getSurroundingTilesMethod(0, 0);
        Assert.assertEquals(3, tilesSurroundingx0y0.size());

        for(Tile t: tilesSurroundingx0y0) {
            Assert.assertEquals(SEA, t.getType());
        }

        //Verify that (1,1) is ground
        Assert.assertEquals(GROUND, getTileMethod(0,0).getType());
    }
}
