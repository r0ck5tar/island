package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.sequences.*;
import fr.unice.polytech.qgl.qdd.ai.sequences.common.LandSequence;
import fr.unice.polytech.qgl.qdd.ai.sequences.common.StopSequence;
import fr.unice.polytech.qgl.qdd.ai.sequences.phase1.*;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.QddExplorer;

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

        return activeSequence.execute();
    }

    private Sequence chooseSequenceAerial() {
        if(checkList.needToAbort()) { return new StopSequence(nav, checkList); }
        if(activeSequence == null) { return new InitialDiscoverySequence(nav, checkList); }
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

        return activeSequence.execute();
    }

    private Sequence chooseSequenceTerrestrial() {
        if(checkList.needToAbort()) { return new StopSequence(nav, checkList); }

        else if(!activeSequence.completed()) {
            return activeSequence;
        }
        else{
            if(!checkList.contractCompleted()) {
                return new ScanSequence(nav, checkList);
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

    public void logExplorer(Action action) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(explorer.toString());
        sb.append("\n" + action.toJSON());

        if(nav.mapInitialized()) {
            sb.append("\nCurrent tile type : " + nav.getCurrentTile().toString() + "\n\n");
            int groundTiles = nav.getMap().getGroundTileCount();
            int seaTiles = nav.getMap().getSeaTileCount();
            int unknownTiles = nav.getMap().getUnknownTileCount();
            int totalTiles = groundTiles + seaTiles + unknownTiles;
            sb.append("Ground tiles: " + groundTiles + "\tSea tiles: " + seaTiles );
            sb.append("\tUnknown tiles: " + unknownTiles + "\tTotal: " + totalTiles + "\n");
            for(int y = nav.getMap().getWidth()-1; y >= 0; y--) {
                for(int x = 0; x <nav.getMap().getLength(); x++) {
                    if((x == nav.getPosX()) && (y == nav.getPosY())) {
                        switch (explorer.getNavigator().front()) {
                            case "N": sb.append(" Λ "); break;
                            case "E": sb.append(" > "); break;
                            case "S": sb.append(" V "); break;
                            case "W": sb.append(" < "); break;
                        }
                    }
                    else{
                        sb.append(nav.getTile(x, y).toString());
                    }
                }
                sb.append("\n");
            }
        }
        gameLogger.info(sb.toString() + "\n");
    }


}
