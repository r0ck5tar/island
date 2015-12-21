package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hbinluqman on 21/12/2015.
 */
public class RelativeDirOfTileByAirFacingEastTest extends FinderTester{
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setPosition(10, 10);
        setFacingDirection(Compass.EAST);
    }

    @Test
    public void testRelativeDirectionOfTileByAirFacingEast() {

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
}
