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
    public void sanityCheck() {
        setPosition(4, 4);
        setFacingDirection(Compass.NORTH);

        Tile x4y4 = getTile(4, 4);

        //sanity check
        Assert.assertTrue(nav.map().currentTile() == x4y4);
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingNorth() {
        setPosition(10, 10);
        setFacingDirection(Compass.NORTH);

        /*
        Tile in front and vertically aligned => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 16)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 17)));

        /*
        Tile in front => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 15)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 15)));

        /*
        Tile to the left, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));

        /*
        Tile to the right, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 11)));

        /*
        Tile to to the right, horizontally aligned => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 10)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 10)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 11)));

        /*
        Tile in front, but within the turning buffer and to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 13)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 13)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 13)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 14)));

        /*
        Tile is at the back, but to the right => RIGHT
        Cases where the tile is really behind the drone, but relativeDirection returns right. This happens when:
            - the tile is not aligned with the line of sight of the drone
            - AND the tile is located at the back but to the right of the drone.
            - AND the tile is outside the neighbourhood of the current tile (the drone occupies the square of 3 x 3 tiles
            around the current tile)

        This is so the drone can turn right, and subsequently reevaluate the relative direction of the tile when it is
        facing the new direction.
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 8)));

        /*
        To to the left, horizontally aligned => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 11)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 11)));

        /*
        Tile in front, but within the turning buffer and to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 13)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 13)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 13)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 14)));

        /*
        Tile is at the back, but to the left => LEFT
        Cases where the tile is really behind the drone, but relativeDirection returns left. This happens when:
            - the tile is aligned with the line of sight of the drone (directly behind the drone)
            - OR the tile is located at the back but to the left of the drone.
            - AND the tile is outside the neighbourhood of the current tile (the drone occupies the square of 3 x 3 tiles
            around the current tile)

        This is so the drone can turn left, and subsequently reevaluate the relative direction of the tile when it is
        facing the new direction.
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));


        /*
        Undefined: relative direction to current tile and relativeDirection to tile in the neighbourhood of current tile.
        The relative direction is undetermined when the tile is within the neighbourhood of the current tile.
        When using the relativeDirectionOfTileByAir method to decide whether to fly forward, turn left or turn right,
        the destinationTile must not be in the the neighbourhood of the current tile.
         */

        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 10))); //directly in front
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 11))); //front right
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 10))); //current tile
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 11)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingEast() {
        setPosition(10, 10);
        setFacingDirection(Compass.EAST);

        /*
        Tile in front and vertically aligned => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(16, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 11)));

        /*
        Tile in front => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 0)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 0)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 17)));

        /*
        Tile to the left, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 14)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 14)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 14)));

        /*
        Tile to the right, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));

        /*
        Tile to to the right, horizontally aligned => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 5)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 5)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 5)));

        /*
        Tile in front, but within the turning buffer and to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 8)));

        /*
        Tile is at the back, but to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 0)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 4)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 5)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 7)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));

        /*
        To to the left, horizontally aligned => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 17)));

        /*
        Tile in front, but within the turning buffer and to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 17)));

        /*
        Tile is at the back, but to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 15)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 13)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 15)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingSouth() {
        setPosition(10, 10);
        setFacingDirection(Compass.SOUTH);

        /*
        Tile in front and vertically aligned => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 2)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));

        /*
        Tile in front => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 5)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 1)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 5)));

        /*
        Tile to the left, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 11)));

        /*
        Tile to the right, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));

        /*
        Tile to to the right, horizontally aligned => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 10)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 9)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 10)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 11)));

        /*
        Tile in front, but within the turning buffer and to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 7)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 7)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 8)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 6)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 7)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));

        /*
        Tile is at the back, but to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(4, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 17)));

        /*
        To to the left, horizontally aligned => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 11)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 11)));

        /*
        Tile in front, but within the turning buffer and to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 7)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 7)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 7)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 8)));

        /*
        Tile is at the back, but to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(13, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(14, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(16, 14)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(16, 17)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 12)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 17)));
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingWest() {
        setPosition(10, 10);
        setFacingDirection(Compass.WEST);

        /*
        Tile in front and vertically aligned => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(1, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 11)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 9)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 10)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 11)));

        /*
        Tile in front => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 0)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(0, 17)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 0)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(5, 17)));

        /*
        Tile to the left, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 8)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 6)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 7)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 8)));

        /*
        Tile to the right, but within the turning buffer => FRONT
         */
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 14)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 14)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 12)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 13)));
        Assert.assertEquals(Direction.FRONT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 14)));

        /*
        Tile to to the right, horizontally aligned => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 17)));

        /*
        Tile in front, but within the turning buffer and to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 17)));

        /*
        Tile is at the back, but to the right => RIGHT
         */
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 12)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 13)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 14)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 17)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 11)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 15)));
        Assert.assertEquals(Direction.RIGHT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 17)));

        /*
        To to the left, horizontally aligned => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(9, 5)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(10, 5)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(11, 5)));

        /*
        Tile in front, but within the turning buffer and to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(6, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(7, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(8, 8)));

        /*
        Tile is at the back, but to the left => LEFT
         */
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 5)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 6)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 7)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 8)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 9)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(12, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(15, 10)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 0)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 4)));
        Assert.assertEquals(Direction.LEFT, nav.finder().relativeDirectionOfTileByAir(getTile(17, 10)));
    }


}
