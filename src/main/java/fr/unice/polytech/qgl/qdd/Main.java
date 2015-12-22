package fr.unice.polytech.qgl.qdd;

import java.io.File;
import static eu.ace_design.island.runner.Runner.*;

/**
 * Created by danial on 27/11/15.
 */
public class Main {
    public static void main(String[] args) throws Exception {
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

                .backBefore(7500)
                .withCrew(15)
                .collecting(500, "WOOD")
                .collecting(200, "FISH")
                .collecting(200,  "QUARTZ")
                .collecting(10,   "FLOWER")
                .storingInto("./outputs")
                .fire();
    }
}
