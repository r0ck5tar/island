package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.ActionEnum;
import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
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
    private IslandMap map;
    private Navigator navigator;
    //private List<Integer> rangeFromExit;

    public QddExplorer(String context){
        map = new IslandMap();
        JSONObject initialValues = new JSONObject(context);

        navigator = new Navigator(initialValues.getString("heading"));
        budget = initialValues.getInt("budget");
        men = initialValues.getInt("men");

        contract = new HashMap<>();
        JSONArray contractList = initialValues.getJSONArray("contracts");
        for (int i = 0; i < contractList.length(); i++ )
        {
            contract.put( contractList.getJSONObject(i).getString("resource"), contractList.getJSONObject(i).getInt("amount") );
        }
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

    public Navigator getNavigator() {
        return navigator;
    }

    public void echo(String direction, JSONObject result) {
        if(!map.isInitialized()) {
            //the width/length of the map is detected
            if (result.getJSONObject("extras").getInt("range") >0){
                switch (direction) {
                    case "N": map.setLength(result.getJSONObject("extras").getInt("range")); break;
                    case "E": map.setWidth(result.getJSONObject("extras").getInt("range")); break;
                    case "S":
                        map.setLength(result.getJSONObject("extras").getInt("range"));
                        map.setPosY(map.getLength()-1);
                        break;
                    case "W":
                        map.setWidth(result.getJSONObject("extras").getInt("range"));
                        map.setPosX(map.getWidth()-1);
                        break;
                }
            }
            //The edge of the map is detected immediately to the right/left of the drone
            else if(result.getJSONObject("extras").getInt("range") == 0) {
                switch (direction) {
                    case "N": map.setLength(-1); break;
                    case "E": map.setWidth(-1); break;
                    case "S": map.setLength(-1); break;
                    case "W": map.setWidth(-1); break;
                }
            }
        }
        else{
            int range = result.getJSONObject("extras").getInt("range");
            if (result.getJSONObject("extras").getString("found").equals("GROUND")) {
                map.updateMap(true, range, direction);
            }
            else {
                map.updateMap(false, range, direction);
            }
        }
    }

    public void turn(String direction){
        switch (navigator.front().concat(direction)) {
            case "NE":
                map.setPosX(map.getPosX() + 1);
                map.setPosY(map.getPosY() + 1);
                //fallthrough
            case "EN":
                map.setPosX(map.getPosX() + 1);
                map.setPosY(map.getPosY() + 1);
                break;
            case "SW":
                map.setPosX(map.getPosX() - 1);
                map.setPosY(map.getPosY() - 1);
                //fallthrough
            case "WS":
                map.setPosX(map.getPosX() - 1);
                map.setPosY(map.getPosY() - 1);
                break;
            case "NW":
                map.setPosX(map.getPosX() - 1);
                map.setPosY(map.getPosY() + 1);
                //fallthrough
            case "WN":
                map.setPosX(map.getPosX() - 1);
                map.setPosY(map.getPosY() + 1);
                break;
            case "SE":
                map.setPosX(map.getPosX() + 1);
                map.setPosY(map.getPosY() - 1);
                //fallthrough
            case "ES":
                map.setPosX(map.getPosX() + 1);
                map.setPosY(map.getPosY() - 1);
                break;
        }

        navigator.setFront(direction);
    }
    
    public void fly(){
        switch (navigator.front()){
            case "N": map.setPosY(map.getPosY() + 1);break;
            case "E": map.setPosX(map.getPosX() + 1);break;
            case "S": map.setPosY(map.getPosY() - 1);break;
            case "W": map.setPosX(map.getPosX() - 1);break;
        }
    }


    public void scan(JSONObject result) {
        JSONArray biomesJson = result.getJSONObject("extras").getJSONArray("biomes");
        List<BiomeEnum> biomes = new ArrayList<BiomeEnum>();
        for (int i=0; i<biomesJson.length(); i++) {
            biomes.add(BiomeEnum.valueOf(biomesJson.getString(i)));
        }
        map.updateMap(biomes);
    }

    public void land(String creekId, int numberOfPeople){

    }

    public void move(String direction){

    }
    
    public IslandMap getMap() {
        return map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: " + budget + "\tMen: " + men );
        sb.append("\nContract: ");
        for(String s: contract.keySet()) {
            sb.append(" " + s + " x " + contract.get(s) + "\t");
        }
        sb.append("\nFacing " + navigator.front() + "  Coordinates: (" + map.getPosX() + ", " + map.getPosY() + ")");
        return sb.toString();
    }
}
