package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hbinluqman on 21/12/2015.
 */
public class RelativeDirOfTileByAirFacingNorthTest extends FinderTester {
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setPosition(10, 10);
        setFacingDirection(Compass.NORTH);
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingNorth() {

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
}
