package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class UpdateMapThroughEchoTest extends IslandMapTest {
    private static final int WIDTH = 30, HEIGHT = 30, RANGE = 9;
    private static final int SOUTH_BORDER = 1, WEST_BORDER = 1, NORTH_BORDER = 28, EAST_BORDER = 28;

    @Test
    public void testEchoSeaEastAndNorthFromSouthWestCorner() {
        createXByYMap(WIDTH, HEIGHT);
        setCurrentPositionMethod(WEST_BORDER, SOUTH_BORDER);

        /*=============
         Test Echo EAST
         ============*/

        map.updateMapThroughEcho(false, RANGE, Compass.EAST);

        Set<Tile> echoedTilesEast = new HashSet<>();

        for(int i=3; i < WIDTH; i+=3) {
            echoedTilesEast.addAll(getSurroundingTilesMethod(WEST_BORDER + i, SOUTH_BORDER));
        }

        for(Tile t: echoedTilesEast) {
            Assert.assertTrue("all 3x3 tiles east of current position should be sea", t.isSea());
        }

        /*==============
         Test Echo NORTH
         =============*/

        map.updateMapThroughEcho(false, RANGE, Compass.NORTH);

        Set<Tile> echoedTilesNorth = new HashSet<>();

        for(int i=3; i < HEIGHT; i+=3) {
            echoedTilesNorth.addAll(getSurroundingTilesMethod(WEST_BORDER, SOUTH_BORDER + i));
        }

        for(Tile t: echoedTilesNorth) {
            Assert.assertTrue("all 3x3 tiles north of current position should be sea", t.isSea());
        }
    }

    @Test
    public void testEchoSeaWestFromSouthEastCorner() {
        createXByYMap(WIDTH, HEIGHT);
        setCurrentPositionMethod(EAST_BORDER, SOUTH_BORDER);

        /*=============
         Test Echo WEST
         ============*/

        map.updateMapThroughEcho(false, RANGE, Compass.WEST);

        Set<Tile> echoedTilesWest = new HashSet<>();

        for(int i=3; i < WIDTH; i+=3) {
            echoedTilesWest.addAll(getSurroundingTilesMethod(EAST_BORDER - i, SOUTH_BORDER));
        }

        for(Tile t: echoedTilesWest) {
            Assert.assertTrue("all 3x3 tiles east of current position should be sea", t.isSea());
        }
    }

    @Test
    public void testEchoSeaSouthFromNorthEastCorner() {
        createXByYMap(WIDTH, HEIGHT);
        setCurrentPositionMethod(EAST_BORDER, NORTH_BORDER);

        /*==============
         Test Echo SOUTH
         =============*/

        map.updateMapThroughEcho(false, RANGE, Compass.SOUTH);

        Set<Tile> echoedTilesSouth = new HashSet<>();

        for(int i=3; i < HEIGHT; i+=3) {
            echoedTilesSouth.addAll(getSurroundingTilesMethod(EAST_BORDER, SOUTH_BORDER - i));
        }

        for(Tile t: echoedTilesSouth) {
            Assert.assertTrue("all 3x3 tiles east of current position should be sea", t.isSea());
        }
    }
}
