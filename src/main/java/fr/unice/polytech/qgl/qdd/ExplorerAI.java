package fr.unice.polytech.qgl.qdd;

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
    private IslandMap map;
    private CheckList checkList;
    private Tile destinationTile;
    private Logger gameLogger;

    public ExplorerAI(QddExplorer explorer){
        this.explorer = explorer;
        map = explorer.getMap();
        checkList = new CheckList(map, explorer);
        destinationTile = null ;
        initializeLoggers();
    }

    private void initializeLoggers() {
        gameLogger = Logger.getLogger("gameLogger");
        FileHandler mapLogFh;
        FileHandler gameLogFh;
        try {
            // This block configure the logger with handler and formatter
            gameLogFh = new FileHandler("gameLog.log");
            gameLogger.addHandler(gameLogFh);
            gameLogFh.setFormatter(new SimpleFormatter());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Action computeAerialStrategy() {
        Action action = null;

        if (!map.isInitialized()){
            action = initialDiscoverySequence();
        }

        if (map.isInitialized()) {
            if (!checkList.isFoundGround()){
                action = echoForGroundSequence();
            }
            else if (!checkList.isAboveGround()) {
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
        Action action = new Action(ActionEnum.ECHO);

        //First echo; echo front
        if(map.getWidth() == 1 && map.getLength() == 1) {
            action.addParameter("direction", explorer.getNavigator().front());
        }
        //second echo; echo right
        else if (map.getWidth() == 1) {
            action.addParameter("direction", explorer.getNavigator().right());
        }
        //third echo; echo left (Only if second echo returned range = 0; i.e. the border is to the right)
        else if (map.getWidth() == -1) {
            action.addParameter("direction", explorer.getNavigator().left());
        }
        //second echo; echo right
        else if (map.getLength() == 1) {
            action.addParameter("direction", explorer.getNavigator().right());
        }
        //third echo; echo left (Only if second echo returned range = 0; i.e. the border is to the right)
        else if (map.getLength() == -1) {
            action.addParameter("direction", explorer.getNavigator().left());
        }

        else {
            map.initializeMap();
        }

        return action;
    }

    private Action echoForGroundSequence() {
        Action action = new Action(ActionEnum.ECHO);
        if (!checkList.isTilesInFrontDiscovered()){
            action.addParameter("direction", explorer.getNavigator().front());
        }
        else if (!checkList.isTilesAtLeftDiscovered()){
            action.addParameter("direction", explorer.getNavigator().left());
        }
        else if (!checkList.isTilesAtRightDiscovered()){
            action.addParameter("direction", explorer.getNavigator().right());
        }
        else {
            action = new Action(ActionEnum.FLY);
        }

        return action;
    }

   private Action flyToGroundSequence() {
        if (destinationTile==null) {
            destinationTile = checkList.findTileWithGround();
        }

       List<Tile> destinationTiles = map.getNeighbouringTiles(destinationTile);
       // If we are not within the neighbouring tile of the destination
        if (!destinationReached()) {
            return flyToDestination(destinationTiles);
        }
        else {
            destinationTile = null;
            return new Action(ActionEnum.SCAN);
        }
    }

    private Action flyToDestination(List<Tile> destinationTiles){
        Action action = orientedToTiles(destinationTiles, explorer.getNavigator().front());
        return action;
    }

    private boolean destinationReached() {
        return map.getNeighbouringTiles(destinationTile).contains(map.getCurrentTile());
    }

    public Action orientedToTiles(List<Tile> tiles, String facing){
        Action action  = new Action(ActionEnum.FLY);
        for (int i = 0; i < tiles.size(); i++){
            String actionString = orientedToTile(tiles.get(i), facing);
            switch (actionString){
                case "front":
                    return action = new Action(ActionEnum.FLY);
                case "left":
                    action = new Action(ActionEnum.HEADING);
                    action.addParameter("direction", explorer.getNavigator().left());
                    return action;
                case "right":
                    action = new Action(ActionEnum.HEADING);
                    action.addParameter("direction", explorer.getNavigator().right());
                    return action;
            }
        }
        return action;
    }
    public String orientedToTile(Tile tile, String facing){
        if(facing.equals("N") || facing.equals("S")) {
            if(tile.getxAxis() == map.getPosX()) {
                if(facing.equals("N") && tile.getyAxis() > map.getPosY()){
                    return "front";
                }
                else if (facing.equals("S") && tile.getyAxis() < map.getPosY()){
                    return "front";
                }
            }
            else if(tile.getyAxis() == map.getPosY()) {
                if(facing.equals("N") && tile.getxAxis() < map.getPosX()
                        || facing.equals("S") && tile.getxAxis() > map.getPosX()) {
                    return "left";
                }
                else {
                    return "right";
                }
            }
        }
        else {
            if(tile.getyAxis() == map.getPosY()) {
                if(facing.equals("E") && tile.getxAxis() > map.getPosX()){
                    return "front";
                }
                else if (facing.equals("W") && tile.getxAxis() < map.getPosX()){
                    return "front";
                }
            }
            else if(tile.getxAxis() == map.getPosX()) {
                if(facing.equals("W") && tile.getyAxis() < map.getPosY()
                        || facing.equals("E") && tile.getyAxis() > map.getPosY()) {
                    return "left";
                }
                else {
                    return "right";
                }
            }
        }

        return "";
    }
    private Action stop(){
        return new Action(ActionEnum.STOP);
    }

    public void logExplorer(Action action) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(explorer.toString());
        sb.append("\n" + action.toJSON() + "\n\n");

        if(map.isInitialized()) {
            for(int y = map.getWidth()-1; y >= 0; y--) {
                for(int x = 0; x <map.getLength(); x++) {
                    if((x == map.getPosX()) && (y == map.getPosY())) {
                        switch (explorer.getNavigator().front()) {
                            case "N": sb.append(" Λ "); break;
                            case "E": sb.append(" > "); break;
                            case "S": sb.append(" V "); break;
                            case "W": sb.append(" < "); break;
                        }
                    }
                    else{
                        sb.append(map.getTile(x, y).toString());
                    }
                }
                sb.append("\n");
            }
        }
        gameLogger.info(sb.toString() + "\n");
    }
}
