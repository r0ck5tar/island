package navigation;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Navigator2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hbinluqman on 14/12/2015.
 */
public class NavigatorRelativeDirectionTest {
    private Navigator2 nav;
    private Method setFacingDirectionMethod;

    @Before
    public void Setup() {
        nav = new Navigator2(Compass.NORTH);

        try {
            setFacingDirectionMethod = Navigator2.class.getDeclaredMethod("setFacingDirection", Compass.class);
            setFacingDirectionMethod.setAccessible(true);
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
    }

    @Test
    public void testRelativeDirectionsWhenFacingEast() {
        setFacingDirection(Compass.EAST);

        Assert.assertEquals(Compass.EAST, nav.front());
        Assert.assertEquals(Compass.SOUTH, nav.right());
        Assert.assertEquals(Compass.WEST, nav.back());
        Assert.assertEquals(Compass.NORTH, nav.left());
    }

    @Test
    public void testRelativeDirectionsWhenFacingSouth() {
        setFacingDirection(Compass.SOUTH);

        Assert.assertEquals(Compass.SOUTH, nav.front());
        Assert.assertEquals(Compass.WEST, nav.right());
        Assert.assertEquals(Compass.NORTH, nav.back());
        Assert.assertEquals(Compass.EAST, nav.left());
    }

    @Test
    public void testRelativeDirectionsWhenFacingWest() {
        setFacingDirection(Compass.WEST);

        Assert.assertEquals(Compass.WEST, nav.front());
        Assert.assertEquals(Compass.NORTH, nav.right());
        Assert.assertEquals(Compass.EAST, nav.back());
        Assert.assertEquals(Compass.SOUTH, nav.left());
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
}
