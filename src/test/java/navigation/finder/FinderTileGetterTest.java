package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class FinderTileGetterTest extends FinderTester{
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setFacingDirection(Compass.NORTH);
    }

    @Test
    public void sanityCheck() {
        setPosition(4, 4);
        setFacingDirection(Compass.NORTH);

        Tile x4y4 = getTile(4, 4);

        //sanity check
        Assert.assertTrue(nav.map().currentTile() == x4y4);
    }

    @Test
    public void testGetRandomNearbyTile() {

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
    }

    /*
    to test: getNearestUnscannedGroundTile, getRandomNearbyTile,  getSurroundingTiles,
             adjacentTileByAir, adjacentTile, getTilesOnSide
     */












}
