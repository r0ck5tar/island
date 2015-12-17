package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Direction;
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
        if(!nav.map().isInitialized()) {
            int range = result.getJSONObject("extras").getInt("range");
            getMap().initializeMapThroughEcho(Compass.fromString(direction), range);
        }
        else{
            int range = result.getJSONObject("extras").getInt("range");
            if (result.getJSONObject("extras").getString("found").equals("GROUND")) {
                getMap().updateMapThroughEcho(true, range, Compass.fromString(direction));
            }
            else {
                getMap().updateMapThroughEcho(false, range, Compass.fromString(direction));
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
        nav.move().fly();
    }

    public void turn(String direction){
        nav.move().turn(Compass.fromString(direction));
    }

    public void land(String creekId, int numberOfPeople){
        //incomplete
        sendMen(numberOfPeople);
    }

    public void move(String direction){
        nav.move().walk(Compass.fromString(direction));
    }

    public void explore(JSONArray resourcesJSON){
        Map<Resource, String> resources = new HashMap<>();
        for(int i = 0; i < resourcesJSON.length(); i++) {
            JSONObject resource = resourcesJSON.getJSONObject(i);
            resources.put(Resource.valueOf(resource.getString("resource")), resource.getString("amount"));
        }

        getMap().updateMapWithResources(resources);
    }

    public void exploit(Resource resource, int amount) {
        getMap().updateMapAfterExploit(resource);
        resources.put(resource, resources.containsKey(resource)? resources.get(resource) + amount : amount);

        boolean fullyExploited = true;

        for(Resource r: getMap().currentTile().getResources().keySet()) {
            if (contract.keySet().contains(r)) {
                fullyExploited = false;
            }
        }

        getMap().currentTile().setExploited(fullyExploited);
    }
    
    /*======================
    Getters and decrementers
    ======================*/
    
    public IslandMap getMap() {
        return nav.map();
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
        return resources.containsKey(resource)? resources.get(resource) : 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: " + budget + "\tMen: " + men );
        sb.append("\t\tContract: ");
        contract.forEach((resource, quantity) -> sb.append(resource + ": " + quantity + "\t"));
        sb.append("\nResources: ");
        resources.forEach((resource, quantity) -> sb.append(resource + ": " + quantity + "\t"));
        sb.append("\nFacing " + nav.front() + "  Coordinates: (" + getMap().x() + ", " + getMap().y() + ")" );
        sb.append(nav.map().isInitialized()? "\tCurrent tile type : " + nav.map().currentTile().getType() + "\n" : "\n");
        return sb.toString();
    }

    /*=============
    Private methods
     ============*/

    private void initializeExplorer(String context) {
        resources = new HashMap<>();
        JSONObject initialValues = new JSONObject(context);

        nav = new Navigator(Compass.fromString(initialValues.getString("heading")));
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
