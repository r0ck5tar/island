package navigation;

import fr.unice.polytech.qgl.qdd.navigation.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by hbinluqman on 15/12/2015.
 */
public class FinderTest extends FinderTester{
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setFacingDirection(Compass.NORTH);
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

    @Test
    public void testRelativeDirectionOfTileByAirFacingNorth() {
        setPosition(4, 4);
        setFacingDirection(Compass.NORTH);

        Tile x4y4 = getTile(4, 4);

        //sanity check
        Assert.assertTrue(nav.map().currentTile() == x4y4);

        /*
        Tile in front
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 17)));

        /*
        To to the right
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 13)));

        /*
        To to the left
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 5)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(1, 3)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 17)));

        /*
        cases where the tile is really behind the drone, but relativeDirection returns right. This happens when:
            - the tile is not aligned with the line of sight of the drone
            - AND the tile is located at the back but to the right of the drone.
            - AND the tile is outside the neighbourhood of the current tile (the drone occupies the square of 3 x 3 tiles
            around the current tile)

        This is so the drone can turn right, and subsequently reevaluate the relative direction of the tile when it is
        facing the new direction.
         */

        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 2)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 1)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 0)));

        /*
        Cases where the tile is really behind the drone, but relativeDirection returns left. This happens when:
            - the tile is aligned with the line of sight of the drone (directly behind the drone)
            - OR the tile is located at the back but to the left of the drone.
            - AND the tile is outside the neighbourhood of the current tile (the drone occupies the square of 3 x 3 tiles
            around the current tile)

        This is so the drone can turn left, and subsequently reevaluate the relative direction of the tile when it is
        facing the new direction.
         */

        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 2)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 2)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 1)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 2)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 0)));

        /*
        Untested: relative direction to current tile, relativeDirection to tile in the neighbourhood of current tile.
        The relative direction is undetermined when the tile is within the neighbourhood of the current tile.
        When using the relativeDirectionOfTileByAir method to decide whether to fly forward, turn left or turn right,
        the destinationTile must not be in the the neighbourhood of the current tile.
         */

        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 5))); //directly in front
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 5))); //front right
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 4))); //current tile
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 3)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 3)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 3)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingEast() {
        setPosition(10, 10);
        setFacingDirection(Compass.EAST);

        /*
        (BACK) LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 11)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(1, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 16)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));

        /*
        LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 15)));

        /*
        (BACK) RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));

        /*
        RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 5)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 4)));

        /*
        FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(16, 9)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingWest() {
        setPosition(10, 10);
        setFacingDirection(Compass.WEST);

        /*
        (BACK) LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 5)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));

        /*
        LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 3)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));

        /*
        (BACK) RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 12)));

        /*
        RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(3, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 17)));

        /*
        FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 10)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingSouth() {
        setPosition(10, 10);
        setFacingDirection(Compass.SOUTH);

        /*
        (BACK) RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 17)));

        /*
        RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 3)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 3)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 10)));


        /*
        (BACK) LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));

        /*
        LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 3)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 3)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(2, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));

        /*
        FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 3)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));
    }
}
