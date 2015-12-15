import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danial on 12/12/2015.
 */
public class IslandMapTest {
    private IslandMap map;

    @Before
    public void setup(){
        map = new IslandMap();
        map.setLength(10);
        map.setWidth(10);
        map.setPosX(0);
        map.setPosY(9);
        map.initializeMap();
    }

    @After
    public void tearDown() {
        map = null;
    }

    @Test
    public void testFindNeighbouringTiles() {
        Tile centerTile = map.getTile(5, 5);

        List<Tile> expectedNeighbouringTiles = new ArrayList<>();
        expectedNeighbouringTiles.add(centerTile);

        expectedNeighbouringTiles.add(map.getTile(4,4));
        expectedNeighbouringTiles.add(map.getTile(4,5));
        expectedNeighbouringTiles.add(map.getTile(4,6));
        expectedNeighbouringTiles.add(map.getTile(5,4));
        expectedNeighbouringTiles.add(map.getTile(5,6));
        expectedNeighbouringTiles.add(map.getTile(6,4));
        expectedNeighbouringTiles.add(map.getTile(6,5));
        expectedNeighbouringTiles.add(map.getTile(6,6));

        Assert.assertTrue(expectedNeighbouringTiles.containsAll(map.getNeighbouringTiles(centerTile)));
        Assert.assertTrue(map.getNeighbouringTiles(centerTile).containsAll(expectedNeighbouringTiles));
    }

    @Test
    public void testFindNeighbouringTilesAtBorder() {
        Tile centerTile = map.getTile(0, 0);

        List<Tile> expectedNeighbouringTiles = new ArrayList<>();
        expectedNeighbouringTiles.add(centerTile);

        expectedNeighbouringTiles.add(map.getTile(0,1));
        expectedNeighbouringTiles.add(map.getTile(1,0));
        expectedNeighbouringTiles.add(map.getTile(1,1));

        Assert.assertTrue(expectedNeighbouringTiles.containsAll(map.getNeighbouringTiles(centerTile)));
        Assert.assertTrue(map.getNeighbouringTiles(centerTile).containsAll(expectedNeighbouringTiles));
    }

    //@Test
    public void testUpdateMap(){
        IslandMap testMap = new IslandMap();
        testMap.setLength(10);
        testMap.setWidth(10);
        testMap.setPosX(0);
        testMap.setPosY(9);
        testMap.initializeMap();

        /*for(int w = testMap.getPosX(); w < testMap.getWidth(); w++){
            testMap.getTile(w,testMap.getPosY()).setType(Tile.SEA);
        }*/
        testMap.updateMapThroughEcho(false,10,"E");
        map.updateMapThroughEcho(false, 10, "E");

        for (int w = 0; w < map.getWidth(); w++) {

        }

    }

}
