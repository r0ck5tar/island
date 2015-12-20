package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private Method getXMethod;
    private Method getYMethod;
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

        try {
            getXMethod = IslandMap.class.getDeclaredMethod("getX", Tile.class);
            getXMethod.setAccessible(true);

            getYMethod = IslandMap.class.getDeclaredMethod("getY", Tile.class);
            getYMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public static void init (QddExplorer explorer, QddSimulator simulator) {
        if(instance == null) {
            instance = new ExplorerLogger(explorer.getMap(), explorer.getNavigator(), explorer, simulator);
        }
    }

    public static void log(String info) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(instance.explorer.toString());
        sb.append("\nStep: " + instance.simulator.actionCounter + "\t");
        sb.append(instance.simulator.action==null?"null":instance.simulator.action.toJSON());
        sb.append("\n" + instance.simulator.result +"\n");
        sb.append(info + "\n\n");

        instance.infoLogger.info(sb.toString());
    }

    public static void shortLog(String info) {
        StringBuilder sb = new StringBuilder();
        sb.append("******************************\n");
        sb.append("Step: " + instance.simulator.actionCounter + "\t" + info + "\n\n");

        instance.infoLogger.info(sb.toString());
    }

    public static int getX(Tile tile) {
        try {
            return (int) instance.getXMethod.invoke(instance.map, tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getY(Tile tile) {
        try {
            return (int) instance.getYMethod.invoke(instance.map, tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
