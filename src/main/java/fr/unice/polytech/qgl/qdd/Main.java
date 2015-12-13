package fr.unice.polytech.qgl.qdd;

import java.io.File;
import static eu.ace_design.island.runner.Runner.*;

/**
 * Created by danial on 27/11/15.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        /*

            ##############################################################################################

                                                  NOTE TO YASOI

            ##############################################################################################
            All the combinations of corner starting positions (4 corners x 4 initial facing direction = 16.
            The first 8 combinations should be supported by the Explorer, since they follow out assumptions
            about starting positions.

            After running each one, the logs can be seen in gameLog.log

            I modified how the map works slightly: Now all tiles are created when the map is initialized (instead
            of leaving undiscovered tiles as null like we did previously). Now we can know if the tile has been
            discovered by using the tile.isDiscovered() method which returns a boolean. I created a TileTypeEnum
            for this. I also added a getCurrentTile method to IslandMap.

            I've managed to get scan to work. Now in the CheckList, aboveGround should be true most of the time.
            I guess one strategy is to switch between setting a destinationTile and flying to it (or within its
            neighbourhood) and then scanning again (try not to overlap the scans -- this can be used as a criteria
            for deciding where to set the next destinationTile. If you look in the method
            ExplorerAI.flyToGroundSequence, you will see that it calls a method destinationReached(). If it's true,
            it does a scan, and also sets the destinationTile back to null.

            You could probably rename the method flyToGroundSequence to flyToDestinationSequence, then reuse it for
            flying around to nearby unscanned places to scan them.

            For the cases that fail below, I've provided my guess about why they fail.
         */

        run(Explorer.class)
                .exploring(new File("map2.json"))
                .withSeed(0L)

                //.startingAt(159, 159, "NORTH") //works
                //.startingAt(159, 159, "WEST") //works
                //.startingAt(1, 1, "SOUTH") //works
                //.startingAt(1, 1, "EAST") //works
                //.startingAt(159, 1, "WEST") //works
                //.startingAt(159, 1, "SOUTH") //works
                .startingAt(1, 159, "NORTH") //works
                //.startingAt(1, 159, "EAST") //works

                /*Our explorer is not designed to handle the following eight starting positions. It is also not
                  designed to work with any starting position that is not shown here */
                //.startingAt(159, 159, "SOUTH") //fails (unsupported starting position)
                //.startingAt(159, 159, "EAST")  //fails (unsupported starting position)
                //.startingAt(1, 159, "SOUTH")   //fails (unsupported starting position)
                //.startingAt(1, 159, "WEST")    //fails (unsupported starting position)
                //.startingAt(159, 1, "NORTH")   //fails (unsupported starting position)
                //.startingAt(159, 1, "EAST")    //fails (unsupported starting position)
                //.startingAt(1, 1, "NORTH")     //fails (unsupported starting position)
                //.startingAt(1, 1, "WEST")      //fails (unsupported starting position)

                .backBefore(7000)
                .withCrew(15)
                .collecting(1000, "WOOD")
                .collecting(300,  "QUARTZ")
                .collecting(10,   "FLOWER")
                .storingInto("./outputs")
                .fire();
    }
}
