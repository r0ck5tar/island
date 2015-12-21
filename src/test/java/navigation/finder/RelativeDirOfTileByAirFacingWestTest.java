package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hbinluqman on 21/12/2015.
 */
public class RelativeDirOfTileByAirFacingWestTest extends FinderTester{
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setPosition(10, 10);
        setFacingDirection(Compass.WEST);
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingWest() {

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
