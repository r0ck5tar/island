package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class GetTilesInDirectionTest extends IslandMapTest {
    @Test
    public void testGetTilesInDirectionNorth() {
        createXByYMap(10, 10);
        setHeightAndWidth(10, 10);

        /*******************************
         Testing getTilesNorth from (0,0)
         *******************************/

        //Set the tiles north of (0,0) as ground
        for(int i=1; i<10; i++) {
            getTileMethod(0, i).setType(GROUND);
        }

        //Get tiles north of (0,0)
        Set<Tile> tilesToNorthOfx0y0 = getTilesInDirectionMethod(NORTH, 0, 0, 9);

        Assert.assertEquals("there should be 9 tiles north of (0,0)", 9, tilesToNorthOfx0y0.size());

        //Verify that the tiles north of (0,0) are all ground
        for (Tile t : tilesToNorthOfx0y0) {
            Assert.assertEquals("tiles north of (0,0) should all be ground", GROUND, t.getType());
        }

        //Verify that the tile (0,0) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(0, 0).getType());

        /*******************************
         Testing getTilesNorth from (5,5)
         *******************************/

        //Set the tiles north of (5,5) as ground
        for(int i=6; i<10; i++) {
            getTileMethod(5, i).setType(SEA);
        }

        //Get tiles north of (5,5)
        Set<Tile> tilesToNorthOfx5y5 = getTilesInDirectionMethod(NORTH, 5, 5, 4);

        Assert.assertEquals("there should be 9 tiles north of (5,5)", 4, tilesToNorthOfx5y5.size());

        //Verify that the tiles north of (5,5) are all ground
        for (Tile t : tilesToNorthOfx5y5) {
            Assert.assertEquals("tiles north of (5,5) should all be sea", SEA, t.getType());
        }

        //Verify that the tile (5,5) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(5, 5).getType());

        /*******************************
         Testing getTilesNorth from (6,9)
         *******************************/

        //Get tiles north of (6,9)
        Set<Tile> tilesToNorthOfx6y9 = getTilesInDirectionMethod(NORTH, 6, 9, 4);

        Assert.assertEquals("there should be 0 tiles north of (6,9)", 0, tilesToNorthOfx6y9.size());

        //Verify that the tile (6,9) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(6, 9).getType());
    }

    @Test
    public void testGetTilesInDirectionSouth() {
        createXByYMap(10, 10);
        setHeightAndWidth(10, 10);

        /*******************************
         Testing getTilesSouth from (0,9)
         *******************************/

        //Set the tiles south of (0,9) as ground
        for(int i=1; i<10; i++) {
            getTileMethod(0, 9 - i).setType(GROUND);
        }

        //Get tiles south of (0,9)
        Set<Tile> tilesToSouthOfx0y9 = getTilesInDirectionMethod(SOUTH, 0, 9, 9);

        Assert.assertEquals("there should be 9 tiles south of (0,9)", 9, tilesToSouthOfx0y9.size());

        //Verify that the tiles north of (0,9) are all ground
        for (Tile t : tilesToSouthOfx0y9) {
            Assert.assertEquals("tiles south of (0,9) should all be ground", GROUND, t.getType());
        }

        //Verify that the tile (0,9) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(0, 9).getType());

        /*******************************
         Testing getTilesSouth from (5,5)
         *******************************/

        //Set the tiles south of (5,5) as ground
        for(int i=1; i<6; i++) {
            getTileMethod(5, 5-i).setType(SEA);
        }

        //Get tiles south of (5,5)
        Set<Tile> tilesToSouthOfx5y5 = getTilesInDirectionMethod(SOUTH, 5, 5, 5);

        Assert.assertEquals("there should be 5 tiles south of (5,5)", 5, tilesToSouthOfx5y5.size());

        //Verify that the tiles south of (5,5) are all ground
        for (Tile t : tilesToSouthOfx5y5) {
            Assert.assertEquals("tiles south of (5,5) should all be sea", SEA, t.getType());
        }

        //Verify that the tile (5,5) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(5, 5).getType());

        /*******************************
         Testing getTilesSouth from (6,0)
         *******************************/

        //Get tiles south of (6,0)
        Set<Tile> tilesToSouthOfx6y0 = getTilesInDirectionMethod(SOUTH, 6, 0, 4);

        Assert.assertEquals("there should be 0 tiles south of (6,0)", 0, tilesToSouthOfx6y0.size());

        //Verify that the tile (6,0) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(6, 0).getType());
    }

    @Test
    public void testGetTilesInDirectionEast() {
        createXByYMap(10, 10);
        setHeightAndWidth(10, 10);

        /*******************************
         Testing getTilesEast from (0,0)
         *******************************/

        //Set the tiles east of (0,0) as ground
        for(int i=1; i<10; i++) {
            getTileMethod(i, 0).setType(GROUND);
        }

        //Get tiles east of (0,0)
        Set<Tile> tilesToEastOfx0y0 = getTilesInDirectionMethod(EAST, 0, 0, 9);

        Assert.assertEquals("there should be 9 tiles east of (0,0)", 9, tilesToEastOfx0y0.size());

        //Verify that the tiles east of (0,0) are all ground
        for (Tile t : tilesToEastOfx0y0) {
            Assert.assertEquals("tiles east of (0,0) should all be ground", GROUND, t.getType());
        }

        //Verify that the tile (0,0) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(0, 0).getType());

        /*******************************
         Testing getTilesEast from (5,5)
         *******************************/

        //Set the tiles east of (5,5) as ground
        for(int i=1; i<5; i++) {
            getTileMethod(5 + i, 5).setType(SEA);
        }

        //Get tiles east of (5,5)
        Set<Tile> tilesToEastOfx5y5 = getTilesInDirectionMethod(EAST, 5, 5, 4);

        Assert.assertEquals("there should be 4 tiles east of (5,5)", 4, tilesToEastOfx5y5.size());

        //Verify that the tiles east of (5,5) are all ground
        for (Tile t : tilesToEastOfx5y5) {
            Assert.assertEquals("tiles east of (5,5) should all be sea", SEA, t.getType());
        }

        //Verify that the tile (5,5) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(5, 5).getType());

        /*******************************
         Testing getTilesEast from (9,6)
         *******************************/

        //Get tiles east of (9,6)
        Set<Tile> tilesToEastOfx9y6 = getTilesInDirectionMethod(EAST, 9, 6, 4);

        Assert.assertEquals("there should be 0 tiles east of (9,6)", 0, tilesToEastOfx9y6.size());

        //Verify that the tile (9,6) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(9, 6).getType());
    }

    @Test
    public void testGetTilesInDirectionWest() {
        createXByYMap(10, 10);
        setHeightAndWidth(10, 10);

        /*******************************
         Testing getTilesWest from (9,0)
         *******************************/

        //Set the tiles west of (9,0) as ground
        for(int i=1; i<10; i++) {
            getTileMethod(9 - i, 0).setType(GROUND);
        }

        //Get tiles west of (9,0)
        Set<Tile> tilesToWestOfx9y0 = getTilesInDirectionMethod(WEST, 9, 0, 9);

        Assert.assertEquals("there should be 9 tiles west of (9,0)", 9, tilesToWestOfx9y0.size());

        //Verify that the tiles west of (9,0) are all ground
        for (Tile t : tilesToWestOfx9y0) {
            Assert.assertEquals("tiles west of (9,0) should all be ground", GROUND, t.getType());
        }

        //Verify that the tile (9,0) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(9, 0).getType());

        /*******************************
         Testing getTilesWest from (5,5)
         *******************************/

        //Set the tiles west of (5,5) as ground
        for(int i=1; i<6; i++) {
            getTileMethod(5 - i, 5).setType(SEA);
        }

        //Get tiles west of (5,5)
        Set<Tile> tilesToWestOfx5y5 = getTilesInDirectionMethod(WEST, 5, 5, 5);

        Assert.assertEquals("there should be 5 tiles west of (5,5)", 5, tilesToWestOfx5y5.size());

        //Verify that the tiles west of (5,5) are all ground
        for (Tile t : tilesToWestOfx5y5) {
            Assert.assertEquals("tiles west of (5,5) should all be sea", SEA, t.getType());
        }

        //Verify that the tile (5,5) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(5, 5).getType());

        /*******************************
         Testing getTilesWest from (0,6)
         *******************************/

        //Get tiles west of (0,6)
        Set<Tile> tilesToWestOfx0y6 = getTilesInDirectionMethod(WEST, 0, 6, 4);

        Assert.assertEquals("there should be 0 tiles west of (0,6)", 0, tilesToWestOfx0y6.size());

        //Verify that the tile (0,6) is unknown
        Assert.assertEquals(UNKNOWN, getTileMethod(0, 6).getType());
    }
}
