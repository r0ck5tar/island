package navigation.navigator;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class NavigatorRelativeDirectionTest {
    private Navigator nav;
    private Method setFacingDirectionMethod;
    private Method absoluteDirectionMethod;
    private Method relativeDirectionMethod;

    @Before
    public void Setup() {
        nav = new Navigator(Compass.NORTH);

        try {
            setFacingDirectionMethod = Navigator.class.getDeclaredMethod("setFacingDirection", Compass.class);
            setFacingDirectionMethod.setAccessible(true);

            absoluteDirectionMethod = Navigator.class.getDeclaredMethod("absoluteDirection", Direction.class);
            absoluteDirectionMethod.setAccessible(true);

            relativeDirectionMethod = Navigator.class.getDeclaredMethod("relativeDirection", Compass.class);
            relativeDirectionMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRelativeDirectionsWhenFacingNorth() {
        setFacingDirection(Compass.NORTH);

        Assert.assertEquals(Compass.NORTH, nav.front());
        Assert.assertEquals(Compass.EAST, nav.right());
        Assert.assertEquals(Compass.SOUTH, nav.back());
        Assert.assertEquals(Compass.WEST, nav.left());

        Assert.assertEquals(Direction.FRONT, relativeDirection(Compass.NORTH));
        Assert.assertEquals(Direction.RIGHT, relativeDirection(Compass.EAST));
        Assert.assertEquals(Direction.BACK, relativeDirection(Compass.SOUTH));
        Assert.assertEquals(Direction.LEFT, relativeDirection(Compass.WEST));

        Assert.assertEquals(Compass.NORTH, absoluteDirection(Direction.FRONT));
        Assert.assertEquals(Compass.EAST, absoluteDirection(Direction.RIGHT));
        Assert.assertEquals(Compass.SOUTH, absoluteDirection(Direction.BACK));
        Assert.assertEquals(Compass.WEST, absoluteDirection(Direction.LEFT));
    }

    @Test
    public void testRelativeDirectionsWhenFacingEast() {
        setFacingDirection(Compass.EAST);

        Assert.assertEquals(Compass.EAST, nav.front());
        Assert.assertEquals(Compass.SOUTH, nav.right());
        Assert.assertEquals(Compass.WEST, nav.back());
        Assert.assertEquals(Compass.NORTH, nav.left());

        Assert.assertEquals(Direction.FRONT, relativeDirection(Compass.EAST));
        Assert.assertEquals(Direction.RIGHT, relativeDirection(Compass.SOUTH));
        Assert.assertEquals(Direction.BACK, relativeDirection(Compass.WEST));
        Assert.assertEquals(Direction.LEFT, relativeDirection(Compass.NORTH));

        Assert.assertEquals(Compass.NORTH, absoluteDirection(Direction.LEFT));
        Assert.assertEquals(Compass.EAST, absoluteDirection(Direction.FRONT));
        Assert.assertEquals(Compass.SOUTH, absoluteDirection(Direction.RIGHT));
        Assert.assertEquals(Compass.WEST, absoluteDirection(Direction.BACK));
    }

    @Test
    public void testRelativeDirectionsWhenFacingSouth() {
        setFacingDirection(Compass.SOUTH);

        Assert.assertEquals(Compass.SOUTH, nav.front());
        Assert.assertEquals(Compass.WEST, nav.right());
        Assert.assertEquals(Compass.NORTH, nav.back());
        Assert.assertEquals(Compass.EAST, nav.left());

        Assert.assertEquals(Direction.FRONT, relativeDirection(Compass.SOUTH));
        Assert.assertEquals(Direction.RIGHT, relativeDirection(Compass.WEST));
        Assert.assertEquals(Direction.BACK, relativeDirection(Compass.NORTH));
        Assert.assertEquals(Direction.LEFT, relativeDirection(Compass.EAST));

        Assert.assertEquals(Compass.NORTH, absoluteDirection(Direction.BACK));
        Assert.assertEquals(Compass.EAST, absoluteDirection(Direction.LEFT));
        Assert.assertEquals(Compass.SOUTH, absoluteDirection(Direction.FRONT));
        Assert.assertEquals(Compass.WEST, absoluteDirection(Direction.RIGHT));
    }

    @Test
    public void testRelativeDirectionsWhenFacingWest() {
        setFacingDirection(Compass.WEST);

        Assert.assertEquals(Compass.WEST, nav.front());
        Assert.assertEquals(Compass.NORTH, nav.right());
        Assert.assertEquals(Compass.EAST, nav.back());
        Assert.assertEquals(Compass.SOUTH, nav.left());

        Assert.assertEquals(Direction.FRONT, relativeDirection(Compass.WEST));
        Assert.assertEquals(Direction.RIGHT, relativeDirection(Compass.NORTH));
        Assert.assertEquals(Direction.BACK, relativeDirection(Compass.EAST));
        Assert.assertEquals(Direction.LEFT, relativeDirection(Compass.SOUTH));

        Assert.assertEquals(Compass.NORTH, absoluteDirection(Direction.RIGHT));
        Assert.assertEquals(Compass.EAST, absoluteDirection(Direction.BACK));
        Assert.assertEquals(Compass.SOUTH, absoluteDirection(Direction.LEFT));
        Assert.assertEquals(Compass.WEST, absoluteDirection(Direction.FRONT));
    }

    private void setFacingDirection(Compass direction) {
        try {
            setFacingDirectionMethod.invoke(nav, direction);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Compass absoluteDirection(Direction relativeDirection) {
        try {
            return (Compass) absoluteDirectionMethod.invoke(nav, relativeDirection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Direction relativeDirection(Compass absoluteDirection) {
        try {
            return (Direction) relativeDirectionMethod.invoke(nav, absoluteDirection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
