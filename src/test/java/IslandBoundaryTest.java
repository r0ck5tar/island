import com.vividsolutions.jts.util.Assert;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Hakim on 12/13/2015.
 */
public class IslandBoundaryTest {
    private Navigator nav;
    private IslandMap map;

    @Before
    public void setup(){
        map = new IslandMap();
        map.setLength(10);
        map.setWidth(10);
        map.setPosX(0);
        map.setPosY(0);
        map.initializeMap();

    }

    @Test
    public void testGetAllTilesNorthx0y0(){
        List<Tile> tiles = map.getTilesNorth();

        Assert.isTrue(tiles.size() == 9);
    }

    @Test
    public void testGetAllTilesNorthx0y9(){
        map.setPosY(9);
        List<Tile> tiles = map.getTilesNorth();
        Assert.isTrue(tiles.size() == 0);
    }
    @Test
    public void testGetAllTilesNorthx0y5(){
        map.setPosY(5);
        List<Tile> tiles = map.getTilesNorth();
        Assert.isTrue(tiles.size() == 4);
    }

    @Test
    public void testGetAllTilesSouthx0y0(){
        List<Tile> tiles = map.getTilesSouth();

        Assert.isTrue(tiles.size() == 0);
    }

    @Test
    public void testGetAllTilesSouthx0y9(){
        map.setPosY(9);
        List<Tile> tiles = map.getTilesSouth();

        Assert.isTrue(tiles.size() == 9);
    }
    @Test
    public void testGetAllTilesSouthx0y5(){
        map.setPosY(5);
        List<Tile> tiles = map.getTilesSouth();

        Assert.isTrue(tiles.size() == 5);
    }

    @Test
    public void testGetAllTilesEastx0y0(){
        List<Tile> tiles = map.getTilesEast();

        Assert.isTrue(tiles.size() == 9);
    }

    @Test
    public void testGetAllTilesEastx9y0(){
        map.setPosX(9);
        List<Tile> tiles = map.getTilesEast();

        Assert.isTrue(tiles.size() == 0);
    }

    @Test
    public void testGetAllTilesEastx5y0(){
        map.setPosX(5);
        List<Tile> tiles = map.getTilesEast();

        Assert.isTrue(tiles.size() == 4);
    }

    @Test
    public void testGetAllTilesWestx0y0(){
        List<Tile> tiles = map.getTilesWest();

        Assert.isTrue(tiles.size() == 0);
    }

    @Test
    public void testGetAllTilesWestx9y0(){
        map.setPosX(9);
        List<Tile> tiles = map.getTilesWest();

        Assert.isTrue(tiles.size() == 9);
    }
    @Test
    public void testGetAllTilesWestx5y0(){
        map.setPosX(5);
        List<Tile> tiles = map.getTilesWest();

        Assert.isTrue(tiles.size() == 5);
    }


}
