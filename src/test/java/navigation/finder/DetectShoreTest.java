package navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hbinluqman on 21/12/2015.
 */
public class DetectShoreTest extends FinderTester {
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setPosition(1, 1);
        setFacingDirection(Compass.EAST);
    }

    @Test
    public void testDetectShore() {
        nav.map().updateMapThroughEcho(false, 5, Compass.NORTH);
        nav.map().updateMapThroughEcho(false, 5, Compass.EAST);

        Assert.assertFalse(nav.finder().detectShore(Direction.LEFT));
        Assert.assertFalse(nav.finder().detectShore(Direction.FRONT));

        nav.move().fly();
        nav.map().updateMapThroughEcho(true, 3, Compass.NORTH);

        nav.move().fly();
        nav.map().updateMapThroughEcho(true, 2, Compass.NORTH);

        Assert.assertTrue(nav.finder().detectShore(Direction.LEFT));

        nav.move().fly();
        nav.map().updateMapThroughEcho(true, 2, Compass.NORTH);

        Assert.assertTrue(nav.finder().detectShore(Direction.LEFT));

        nav.move().fly();
        nav.map().updateMapThroughEcho(true, 1, Compass.NORTH);

        Assert.assertTrue(nav.finder().detectShore(Direction.LEFT));

        nav.move().turn(Compass.NORTH);
        nav.map().updateMapThroughEcho(false, 5, Compass.WEST);

        Assert.assertFalse(nav.finder().detectShore(Direction.LEFT));

        nav.move().fly();
        nav.map().updateMapThroughEcho(true, 0, Compass.WEST);

        Assert.assertTrue(nav.finder().detectShore(Direction.LEFT));

        nav.move();
        nav.map().updateMapThroughEcho(true, 0, Compass.WEST);

        nav.move();
        nav.map().updateMapThroughEcho(true, 1, Compass.WEST);

        Assert.assertTrue(nav.finder().detectShore(Direction.LEFT));

    }
}