import com.sun.javafx.event.EventQueue;
import com.vividsolutions.jts.util.Assert;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by danial on 12/12/2015.
 */
public class NavigatorTest {
    private Navigator nav;
    private Tile x2y4;
    private Tile x6y9;
    private Tile x6y6;
    private Tile x5y6;
    private Tile x5y1;
    private Tile x2y5;
    private Tile x7y5;
    private Tile x7y9;
    private Tile x1y8;
    private Tile x4y5;
    private Tile x4y1;

    /*
        Initialize Navigator with map at current position (5,5), facing North.
     */
    @Before
    public void setup(){
        nav = new Navigator("N");

        IslandMap map = nav.getMap();
        map.setLength(10);
        map.setWidth(10);
        map.setPosX(0);
        map.setPosY(0);
        map.initializeMap();

        map.setPosX(5);
        map.setPosY(5);
        x2y4 = nav.getTile(2, 4);
        x6y9 = nav.getTile(6, 9);
        x6y6 = nav.getTile(6, 6);
        x5y6 = nav.getTile(5, 6);
        x5y1 = nav.getTile(5, 1);
        x2y5 = nav.getTile(2, 5);
        x7y5 = nav.getTile(7, 5);
        x7y9 = nav.getTile(7, 9);
        x1y8 = nav.getTile(1, 8);
        x4y5 = nav.getTile(4, 5);
        x4y1 = nav.getTile(4, 1);
    }

    @Test
    public void testNorthFacingRelativeDirection(){
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x5y6));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x6y9));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x7y9));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x4y5));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x2y4));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x2y5));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x1y8));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x5y1));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x4y1));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x6y6));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x7y5));
    }

    @Test
    public void testEastFacingRelativeDirection(){
        nav.setFront("E");

        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x5y6));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x6y9));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x7y9));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x4y5));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x2y4));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x2y5));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x1y8));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x5y1));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x6y6));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x7y5));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x4y1));
    }

    @Test
    public void testSouthFacingRelativeDirection(){
        nav.setFront("S");

        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x5y6));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x6y9));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x7y9));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x4y5));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x2y4));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x2y5));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x1y8));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x5y1));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x6y6));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x7y5));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x4y1));
    }

    @Test
    public void testWestFacingRelativeDirection(){
        nav.setFront("W");

        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x5y6));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x6y9));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x7y9));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x4y5));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x2y4));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x2y5));
        Assert.equals(Direction.FRONT, nav.getRelativeDirectionOfTile(x1y8));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x5y1));
        Assert.equals(Direction.RIGHT, nav.getRelativeDirectionOfTile(x6y6));
        Assert.equals(Direction.BACK, nav.getRelativeDirectionOfTile(x7y5));
        Assert.equals(Direction.LEFT, nav.getRelativeDirectionOfTile(x4y1));
    }
}
