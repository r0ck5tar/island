package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
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
    private Map<Resource, Integer> resources;
    private Map<Resource, Integer> contract;
    private Navigator nav;

    public QddExplorer(String context){
        initializeExplorer(context);
    }
    
    /*================================================================
    Updater methods called after actions are successfully acknowledged
    ================================================================*/
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
                // +1 inlcluding the drone current position
                case "N": getMap().setLength(range + 1); break;
                case "E": getMap().setWidth(range + 1); break;
                case "S": getMap().setLength(range + 1); setPosY(getMap().getLength()-1); break;
                case "W": getMap().setWidth(range + 1); setPosX(getMap().getWidth()-1); break;
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

        List<Biome> biomes = new ArrayList<Biome>();
        for (int i=0; i<biomesJson.length(); i++) {
            biomes.add(Biome.valueOf(biomesJson.getString(i)));
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
        //incomplete
        sendMen(numberOfPeople);
    }

    public void move(String direction){
        nav.setPosition(nav.getTileInAbsoluteDirection(direction), direction);
    }

    public void explore(JSONArray resourcesJSON){
        Map<Resource, String> resources = new HashMap<>();
        for(int i = 0; i < resourcesJSON.length(); i++) {
            JSONObject resource = resourcesJSON.getJSONObject(i);
            resources.put(Resource.valueOf(resource.getString("resource")), resource.getString("amount"));
        }

        getMap().updateMap(resources);
    }

    public void exploit(Resource resource, int amount) {
        getMap().updateMapAfterExploit(resource);
        if(resources.containsKey(resource)) {
            resources.put(resource, resources.get(resource) + amount);
        }

        boolean fullyExploited = true;

        for(Resource r: nav.getCurrentTile().getResources().keySet()) {
            if (contract.keySet().contains(r)) {
                fullyExploited = false;
            }
        }

        nav.getCurrentTile().setExploited(fullyExploited);
    }

    /*=================
    Relative directions
    =================*/
    public String front() {
        return nav.front();
    }

    public String right() { return nav.right(); }

    public String left() { return nav.left(); }

    /*==============
    Current position
    ==============*/
    public int getPosX() { return nav.getMap().getPosX(); }

    public int getPosY() { return nav.getMap().getPosY(); }

    public void setPosX(int x) {nav.getMap().setPosX(x);}

    public void setPosY(int y) {nav.getMap().setPosY(y);}
    
    /*======================
    Getters and decrementers
    ======================*/
    
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

    public Map<Resource, Integer> getContract() {
        return contract;
    }

    public Map<Resource, Integer> getResources() {
        return resources;
    }

    public int getResourceQuantity(Resource resource) {
        if(resources.containsKey(resource)){
            return resources.get(resource);
        }

        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: " + budget + "\tMen: " + men );
        sb.append("\nContract: ");
        for(Resource s: contract.keySet()) {
            sb.append(" " + s + " x " + contract.get(s) + "\t");
        }
        sb.append("\nFacing " + nav.front() + "  Coordinates: (" + getPosX() + ", " + getPosY() + ")" );
        return sb.toString();
    }

    /*=============
    Private methods
     ============*/

    private void initializeExplorer(String context) {
        resources = new HashMap<>();
        JSONObject initialValues = new JSONObject(context);

        nav = new Navigator(initialValues.getString("heading"));
        budget = initialValues.getInt("budget");
        men = initialValues.getInt("men");

        contract = new HashMap<>();
        JSONArray contractList = initialValues.getJSONArray("contracts");
        for (int i = 0; i < contractList.length(); i++ )
        {
            contract.put( Resource.valueOf(contractList.getJSONObject(i).getString("resource")),
                    contractList.getJSONObject(i).getInt("amount"));
        }
    }
}
