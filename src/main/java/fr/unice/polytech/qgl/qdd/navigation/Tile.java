package fr.unice.polytech.qgl.qdd.navigation;


import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danial on 10/12/15.
 */
public class Tile {
    static final String UNKNOWN = "UNKNOWN", GROUND = "GROUND", SEA = "SEA";
    private String type = null;
    private boolean explored = false;
    private boolean exploited = false;
    private List<Biome> biomes = new ArrayList<>();
    private Map<Resource, String> resources = new HashMap<>();
    private List<String> creeks = new ArrayList<>();
    private String condition;
    private TileListener listener;

    public Tile(TileListener listener) {
        this.listener = listener;
        setType(Tile.UNKNOWN);
    }

    public boolean isUnknown() {
        return type.equals(Tile.UNKNOWN);
    }

    public boolean isGround() {
        return type.equals(Tile.GROUND);
    }

    public boolean isSea() { return type.equals(Tile.SEA); }

    public boolean isUnscanned() {
        return biomes.isEmpty();
    }

    public boolean isUnscouted() { return resources.isEmpty(); }

    public boolean isExplored() {
        return explored;
    }

    public boolean hasCreek() {
        return creeks.size() > 0;
    }



    public boolean hasResources() {
        return !resources.isEmpty();
    }

    public boolean hasResource(Resource resource) {
        return resources.containsKey(resource);
    }


    public void addResources(Map<Resource, String> resources) {
        for(Resource resource: resources.keySet() ){
            if(this.resources.containsKey(resource)) {
                this.resources.put(resource, this.resources.get(resource) + resources.get(resource));
            }
            else{
                this.resources.put(resource, resources.get(resource));
            }
        }
    }

    /*
    public boolean potentiallyHasResource(Resource resource) {

    }*/

    /*
        Getters and Setters
     */

    public String getType() {
        return type;
    }

    public List<String> getCreeks() {
        return creeks;
    }

    public void addCreeks(List<String> creeks) {
        this.creeks.addAll(creeks);
        listener.creekDiscovered(this);
    }

    public Map<Resource, String> getResources() {
        return resources;
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public List<Biome> getBiomes() {
        return biomes;
    }

    public void addBiomes(List<Biome> biomes) {
        this.biomes.addAll(biomes);
        if(this.isGround()) {
            //do not change the type
        }
        else if (!this.isSea() && !this.hasOnlyOceanBiomes(biomes)){
            this.setType(Tile.GROUND);
        }
        else if (this.hasOnlyOceanBiomes(biomes) && this.isUnknown()) {
            this.setType(Tile.SEA);
        }
        listener.biomeDiscovered(this);
    }

    public boolean hasOnlyOceanBiomes(List<Biome> biomes){
        for (int i = 0; i<biomes.size(); i++) {
            if (!biomes.get(i).equals(Biome.OCEAN)) {
                return false;
            }
        }
        return true;
    }

    private void setType(String type) {
        String previousType = this.type;
        this.type = type;
        listener.typeDiscovered(this, previousType, type);
    }

    public void setSea() {
        setType(SEA);
    }

    public void setGround() {
        setType(GROUND);
    }

    public boolean isExploited() {
        return exploited;
    }

    public void setExploited(boolean exploited) {
        this.exploited = exploited;
    }

    public void setExplored() {
        explored = true;
    }

    public String toString() {
        switch (type) {
            case UNKNOWN: return "·";
            case SEA: return "~";
            case GROUND: return "G";
            //ᚙ✰
        }
        return type.toString();
    }
}
