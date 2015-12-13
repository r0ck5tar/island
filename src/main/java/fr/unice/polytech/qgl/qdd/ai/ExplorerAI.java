package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import fr.unice.polytech.qgl.qdd.enums.ActionEnum;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Hakim on 12/11/2015.
 */
public class ExplorerAI {
    private QddExplorer explorer;
    private Navigator nav;
    private CheckList checkList;
    private Tile destinationTile;
    private Logger gameLogger;

    public ExplorerAI(QddExplorer explorer){
        this.explorer = explorer;
        nav = explorer.getNavigator();
        checkList = new CheckList(nav, explorer);
        destinationTile = null ;
        initializeLoggers();
    }

    public Action computeAerialStrategy() {
        Action action = null;

        if (!nav.mapInitialized()){ action = initialDiscoverySequence(); }

        if (nav.mapInitialized()) {
            if (!checkList.isFoundGround()){
                action = echoForGroundSequence();
            }
            else if (!checkList.isAboveGround()) {
                action = flyToGroundSequence();
            }
            else if (!checkList.findCreeks()){
                //action = searchForCreeks();
                action = flyToGroundSequence();
            }
            else{
                action = stop();
            }
        }
        return action;
    }


    public Action computeTerrestrialStrategy() {
        return null;
    }

    private Action initialDiscoverySequence() {
        //First echo; echo front
        if(nav.getMap().getWidth() == 1 && nav.getMap().getLength() == 1) { return echo(explorer.front()); }

        //second echo; echo right
        else if (nav.getMap().getWidth() == 1 || nav.getMap().getLength() == 1) { return echo(explorer.right()); }

        //third echo; echo left (Only if second echo returned range = 0; i.e. the border is to the right)
        else if (nav.getMap().getWidth() == -1 || nav.getMap().getLength() == -1) { return echo(explorer.left()); }

        else { nav.initializeMap(); }

        return null;
    }

    private Action echoForGroundSequence() {
        Action action = null;
        if (!checkList.isTilesAtLeftDiscovered()){
            action = echo( explorer.left());
        }
        else if (!checkList.isTilesAtRightDiscovered()){
            action = echo( explorer.right());
        }
        else if (!checkList.isTilesInFrontDiscovered()){
            action = echo( explorer.front());
        }
        else {
            action = fly();
        }

        return action;
    }

    private Action chooseTurningDirection() {
        int unknownTilesOnRight = 0, unknownTilesOnLeft = 0;

        for (Tile t: nav.getTilesOnSide(Direction.RIGHT)){
            if(t.isUnknown()) { unknownTilesOnRight++; }
        }

        for (Tile t: nav.getTilesOnSide(Direction.LEFT)){
            if(t.isUnknown()) { unknownTilesOnLeft++; }
        }

        if (unknownTilesOnLeft > unknownTilesOnRight){ return heading(explorer.left()); }
        else { return heading(explorer.right()); }
    }

    private Action flyToGroundSequence() {
        if (destinationTile==null) {
            destinationTile = nav.findTileWithUnscannedGround();
        }

        if (destinationTile == null){
            return echoForGroundSequence();
        }

       // If we are not within the neighbouring tile of the destination
        if (!neighbouringDestinationReached()) {
            return flyToDestination();
        }
        else if (nav.getCurrentTile().isUnscanned()){
            destinationTile = null;
            return scan();
        }
        else {
            return echoForGroundSequence();
        }
    }

    private Action flyToDestination(){
        switch (nav.getRelativeDirectionOfTile(destinationTile)) {
            case FRONT: return fly();
            case RIGHT: return heading(explorer.right());
            case LEFT: return heading(explorer.left());
        }
        return fly();
    }

    private Action searchForCreeks() {
        if (checkList.isCloseToBoundary()){
            return chooseTurningDirection();
        }

        if (destinationTile == null){
            switch (explorer.getNavigator().front()) {
                case "N" :destinationTile = nav.getTile(nav.getPosX(), nav.getPosY() + 1);
                    break;
                case "E": destinationTile = nav.getTile(nav.getPosX() + 1, nav.getPosY());
                    break;
                case "S": destinationTile = nav.getTile(nav.getPosX(), nav.getPosY() - 1);
                    break;
                case "W": destinationTile = nav.getTile(nav.getPosX() - 1, nav.getPosY());
                    break;
            }
        }

        if (destinationTile == null) {
            return stop();
        }

        // If we are not within the neighbouring tile of the destination
        if (!destinationReached()) {
            return flyToDestination();
        }
        else {
            destinationTile = null;
            return new Action(ActionEnum.SCAN);
        }
    }


    private boolean neighbouringDestinationReached() {
        return nav.getNeighbouringTiles(destinationTile).contains(nav.getCurrentTile());
    }

    private boolean destinationReached(){
        return destinationTile.equals(nav.getCurrentTile());
    }

    /*
        Action-building helper methods
     */

    private Action echo(String direction){
        return new Action(ActionEnum.ECHO).addParameter("direction", direction);
    }

    private Action scan() { return new Action(ActionEnum.SCAN); }

    private Action fly() {
        return checkList.isCloseToBoundary()?  chooseTurningDirection(): new Action(ActionEnum.FLY);
    }

    private Action heading(String direction){
        return new Action(ActionEnum.HEADING).addParameter("direction", direction);
    }

    private Action stop(){
        return new Action(ActionEnum.STOP);
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
        sb.append("\n" + action.toJSON() + "\n\n");

        if(nav.mapInitialized()) {
            sb.append("\nCurrent tile type : " + nav.getCurrentTile().toString() + "\n\n");
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
