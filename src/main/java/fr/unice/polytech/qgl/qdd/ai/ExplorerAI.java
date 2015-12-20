package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ExplorerLogger;
import fr.unice.polytech.qgl.qdd.ai.sequences.*;
import fr.unice.polytech.qgl.qdd.ai.sequences.common.LandSequence;
import fr.unice.polytech.qgl.qdd.ai.sequences.common.StopSequence;
import fr.unice.polytech.qgl.qdd.ai.sequences.phase1.*;
import fr.unice.polytech.qgl.qdd.ai.sequences.phase2.ExploitSequence;
import fr.unice.polytech.qgl.qdd.ai.sequences.phase2.ExploreSequence;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by danial on 12/11/2015.
 */
public class ExplorerAI {
    private QddExplorer explorer;
    private Navigator nav;
    private CheckList checkList;
    private Logger gameLogger;
    private Sequence activeSequence;

    public ExplorerAI(QddExplorer explorer){
        this.explorer = explorer;
        nav = explorer.getNavigator();
        checkList = new CheckList(nav, explorer);
        initializeLoggers();
        activeSequence = null;
    }

    public Action computeAerialStrategy() {
        activeSequence = chooseSequenceAerial();
        ExplorerLogger.log("Active sequence: " + activeSequence);
        return activeSequence.execute();
    }

    private Sequence chooseSequenceAerial() {
        if(checkList.needToAbort()) { return new StopSequence(nav, checkList); }
        if(activeSequence == null) { return InitialDiscoverySequence.instance(nav, checkList); }
        else if(!activeSequence.completed()) {
            return activeSequence;
        }
        else{
            if(!checkList.isEchoCoverageSufficient()) {
                return new EchoForGroundSequence(nav, checkList);
            }
            else if(!checkList.isAboveGround()) {
                return new FlyToUnscannedGroundSequence(nav, checkList);
            }
            else if(!checkList.foundCreek()) {
                return new ScanSequence(nav, checkList);
            }
            else{

                if(checkList.foundCreek()) {
                    return new LandSequence(nav, checkList, explorer.getMen()-1);
                }
                //return new FlyToRandomNearbyTileSequence(nav, checkList);
                return new StopSequence(nav, checkList);
            }
        }
    }

    public Action computeTerrestrialStrategy() {
        activeSequence = chooseSequenceTerrestrial();
        ExplorerLogger.log("Active sequence: " + activeSequence);
        return activeSequence.execute();
    }

    private Sequence chooseSequenceTerrestrial() {
        if(checkList.needToAbort()) { return new StopSequence(nav, checkList); }

        else if(!activeSequence.completed()) {
            return activeSequence;
        }
        else{
            if(!checkList.contractCompleted()) {
                if(checkList.exploitableResourceFound()) {
                    return new ExploitSequence(nav, checkList, explorer.getContract(), explorer.getResources());
                }
                return new ExploreSequence(nav, checkList);
            }
            else{
                return new StopSequence(nav, checkList);
            }
        }

    }

    /*
        Logging
     */

    private void initializeLoggers() {
        gameLogger = Logger.getLogger("gameLogger");

        FileHandler gameLogFh;
        try {
            //configure the logger with handler and formatter
            gameLogFh = new FileHandler("gameLog.log");
            gameLogger.addHandler(gameLogFh);
            gameLogFh.setFormatter(new SimpleFormatter());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logExplorer(Action action, int step) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(explorer.toString());
        sb.append("\nStep: " + step + "\t" + action.toJSON());

        if(nav.map().isInitialized()) {
            sb.append("\nCurrent tile type : " + nav.map().currentTile().toString() + "\n\n");
            int groundTiles = nav.map().getGroundTileCount();
            int seaTiles = nav.map().getSeaTileCount();
            int unknownTiles = nav.map().getUnknownTileCount();
            int totalTiles = groundTiles + seaTiles + unknownTiles;
            sb.append("Ground tiles: " + groundTiles + "\tSea tiles: " + seaTiles );
            sb.append("\tUnknown tiles: " + unknownTiles + "\tTotal: " + totalTiles + "\n");
            for(int y = nav.map().width()-1; y >= 0; y--) {
                for(int x = 0; x <nav.map().height(); x++) {
                    if((x == nav.map().x()) && (y == nav.map().y())) {
                        switch (explorer.getNavigator().front()) {
                            case NORTH: sb.append("Λ"); break;
                            case EAST: sb.append(">"); break;
                            case SOUTH: sb.append("V"); break;
                            case WEST: sb.append("<"); break;
                        }
                    }
                    else{
                        sb.append(nav.finder().getTile(x, y).toString());
                    }
                }
                sb.append("\n");
            }
        }
        gameLogger.info(sb.toString() + "\n");
    }
}
