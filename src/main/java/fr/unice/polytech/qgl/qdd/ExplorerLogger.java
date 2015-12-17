package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by hbinluqman on 16/12/2015.
 */
public class ExplorerLogger {
    private IslandMap map;
    private Navigator nav;
    private QddExplorer explorer;
    private QddSimulator simulator;
    public Logger infoLogger;
    private static ExplorerLogger instance;

    private ExplorerLogger(IslandMap map, Navigator nav, QddExplorer explorer, QddSimulator simulator) {
        this.map = map;
        this.nav = nav;
        this.explorer = explorer;
        this.simulator = simulator;
        infoLogger = Logger.getLogger("infoLogger");
        FileHandler infoLogFh;
        try {
            infoLogFh = new FileHandler("infoLog.log");
            infoLogger.addHandler(infoLogFh);
            infoLogFh.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init (QddExplorer explorer, QddSimulator simulator) {
        if(instance == null) {
            instance = new ExplorerLogger(explorer.getMap(), explorer.getNavigator(), explorer, simulator);
        }
    }

    public void log(String info) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(explorer.toString());
        sb.append("\nStep: " +simulator.actionCounter + "\t");
        sb.append(simulator.action==null?"null":simulator.action.toJSON());
        sb.append("\n" + simulator.result +"\n");
        sb.append(info + "\n\n");

        infoLogger.info(sb.toString());
    }

    public static ExplorerLogger getInstance(){
        return instance;
    }

    public void shortLog(String info) {
        StringBuilder sb = new StringBuilder();
        sb.append("******************************\n");
        sb.append("Step: " + simulator.actionCounter + "\t" + info + "\n\n");

        infoLogger.info(sb.toString());
    }
}
