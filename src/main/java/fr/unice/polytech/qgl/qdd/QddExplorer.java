package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by danial on 04/12/15.
 */
public class QddExplorer {
    private int budget;
    private int men;
    private Map<String, Integer> resources;
    private Map<String, Integer> contract;
    private Navigator nav;

    public QddExplorer(String context){
        initializeExplorer(context);
    }
    
    /*
        Updater methods called after actions are successfully acknowledged
     */
    public void echo(String direction, JSONObject result) {
        if(!nav.mapInitialized()) {
            int range = result.getJSONObject("extras").getInt("range");
            exploratoryEcho(direction, range);
        }
        else{
            int range = result.getJSONObject("extras").getInt("range");
            if (result.getJSONObject("extras").getString("found").equals("GROUND")) {
                getMap().updateMapThroughEcho(true, range, direction);
            }
            else {
                getMap().updateMapThroughEcho(false, range, direction);
            }
        }
    }
    
    private void exploratoryEcho(String direction, int range) {
        //the width/length of the map is detected
        if (range >0){
            switch (direction) {
                case "N": getMap().setLength(range); break;
                case "E": getMap().setWidth(range); break;
                case "S": getMap().setLength(range); setPosY(getMap().getLength()-1); break;
                case "W": getMap().setWidth(range); setPosX(getMap().getWidth()-1); break;
            }
        }
        //The edge of the map is detected immediately to the right/left of the drone
        else if(range == 0) {
            switch (direction) {
                case "N": getMap().setLength(-1); break;
                case "E": getMap().setWidth(-1); break;
                case "S": getMap().setLength(-1); break;
                case "W": getMap().setWidth(-1); break;
            }
        }
    }

    public void scan(JSONObject result) {
        JSONArray biomesJson = result.getJSONObject("extras").getJSONArray("biomes");
        JSONArray creeksJson = result.getJSONObject("extras").getJSONArray("creeks");
        List<String> creeks = new ArrayList<>();
        for(int i=0; i<creeksJson.length(); i++) {
            creeks.add(creeksJson.getString(i));
        }

        List<BiomeEnum> biomes = new ArrayList<BiomeEnum>();
        for (int i=0; i<biomesJson.length(); i++) {
            biomes.add(BiomeEnum.valueOf(biomesJson.getString(i)));
        }
        getMap().updateMapThroughScan(biomes);
        getMap().updateMapWithCreeks(creeks);
    }

    public void fly(){
        switch (nav.front()){
            case "N": setPosY(getPosY() + 1);break;
            case "E": setPosX(getPosX() + 1);break;
            case "S": setPosY(getPosY() - 1);break;
            case "W": setPosX(getPosX() - 1);break;
        }
    }

    public void turn(String direction){
        switch (nav.front().concat(direction)) {
            case "NE":
                setPosX(getPosX() + 1);
                setPosY(getPosY() + 1);
                break;
            case "EN":
                setPosX(getPosX() + 1);
                setPosY(getPosY() + 1);
                break;
            case "SW":
                setPosX(getPosX() - 1);
                setPosY(getPosY() - 1);
                break;
            case "WS":
                setPosX(getPosX() - 1);
                setPosY(getPosY() - 1);
                break;
            case "NW":
                setPosX(getPosX() - 1);
                setPosY(getPosY() + 1);
                break;
            case "WN":
                setPosX(getPosX() - 1);
                setPosY(getPosY() + 1);
                break;
            case "SE":
                setPosX(getPosX() + 1);
                setPosY(getPosY() - 1);
                break;
            case "ES":
                setPosX(getPosX() + 1);
                setPosY(getPosY() - 1);
                break;
        }

        nav.setFront(direction);
    }



    public void land(String creekId, int numberOfPeople){

    }

    public void move(String direction){

    }

    private void initializeExplorer(String context) {
        JSONObject initialValues = new JSONObject(context);

        nav = new Navigator(initialValues.getString("heading"));
        budget = initialValues.getInt("budget");
        men = initialValues.getInt("men");

        contract = new HashMap<>();
        JSONArray contractList = initialValues.getJSONArray("contracts");
        for (int i = 0; i < contractList.length(); i++ )
        {
            contract.put( contractList.getJSONObject(i).getString("resource"), contractList.getJSONObject(i).getInt("amount") );
        }
    }
    /*
        Relative directions
     */
    public String front() {
        return nav.front();
    }

    public String right() { return nav.right(); }

    public String left() { return nav.left(); }

    /*
        Current position
     */
    public int getPosX() { return nav.getMap().getPosX(); }

    public int getPosY() { return nav.getMap().getPosY(); }

    public void setPosX(int x) {nav.getMap().setPosX(x);}

    public void setPosY(int y) {nav.getMap().setPosY(y);}
    
    /*
        Getters and decrementers
     */
    
    public IslandMap getMap() {
        return nav.getMap();
    }

    public Navigator getNavigator() {
        return nav;
    }

    public int getBudget() {
        return budget;
    }

    public void decreaseBudget(int budget) {
        this.budget -= budget;
    }

    public int getMen() {
        return men;
    }

    public void sendMen(int men) {
        this.men -= men;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: " + budget + "\tMen: " + men );
        sb.append("\nContract: ");
        for(String s: contract.keySet()) {
            sb.append(" " + s + " x " + contract.get(s) + "\t");
        }
        sb.append("\nFacing " + nav.front() + "  Coordinates: (" + getPosX() + ", " + getPosY() + ")" );
        return sb.toString();
    }
}
