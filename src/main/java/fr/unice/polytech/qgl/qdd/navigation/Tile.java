package fr.unice.polytech.qgl.qdd.navigation;


import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
import fr.unice.polytech.qgl.qdd.enums.ResourceEnum;
import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danial on 10/12/15.
 */
public class Tile {
    private TileTypeEnum type;
    private List<BiomeEnum> biomes;
    private Map<ResourceEnum, String> resources;
    private boolean exploited;

    private List<String> creeks;
    private String condition;
    private TileListener listener;
    private int xAxis;
    private int yAxis;

    public Tile(int xAxis, int yAxis, TileListener listener) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.listener = listener;
        setType(TileTypeEnum.UNKNOWN);
        biomes = new ArrayList<>();
        creeks = new ArrayList<>();
        resources = new HashMap<>();
        exploited = false;
    }

    public boolean isUnknown() {
        return type.equals(TileTypeEnum.UNKNOWN);
    }

    public boolean isGround() {
        return type.equals(TileTypeEnum.GROUND);
    }

    public boolean isSea() { return type.equals(TileTypeEnum.SEA); }

    public boolean isUnscanned() {
        return biomes.isEmpty();
    }

    public boolean isUnscouted() { return resources.isEmpty(); }

    public boolean isUnexplored() {
        if(isUnscouted()) {
            return true;
        }
        else {
            for(ResourceEnum r: resources.keySet()) {
                if (resources.get(r).length() > 0) { return false; }
            }
        }
        return true;
    }

    public boolean hasCreek() {
        return creeks.size() > 0;
    }

    public boolean hasOnlyOceanBiomes(List<BiomeEnum> biomes){
        for (int i = 0; i<biomes.size(); i++) {
            if (!biomes.get(i).equals(BiomeEnum.OCEAN)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasResources() {
        return !resources.isEmpty();
    }

    public boolean hasResource(ResourceEnum resource) {
        return resources.containsKey(resource);
    }


    public void addResources(Map<ResourceEnum, String> resources) {
        for(ResourceEnum  resource: resources.keySet() ){
            if(this.resources.containsKey(resource)) {
                this.resources.put(resource, this.resources.get(resource) + resources.get(resource));
            }
            else{
                this.resources.put(resource, resources.get(resource));
            }
        }
    }

/*    public boolean potentiallyHasResource(ResourceEnum resource) {

    }*/


    /*
        Relative positions to other tiles
     */
    public boolean strictlyVAlignedWith(Tile tile) {
        return (xAxis == tile.xAxis);
    }

    public boolean strictlyHAlignedWith(Tile tile) {
        return (yAxis == tile.yAxis);
    }

    public boolean vAlignedWith(Tile tile) {
            return (tile.xAxis >= xAxis - 1 && tile.xAxis <= xAxis + 1);
    }

    public boolean hAlignedWith(Tile tile) {
        return (tile.yAxis >= yAxis - 1 && tile.yAxis <= yAxis + 1);

    }

    /*
        Getters and Setters
     */
    public int getxAxis(){
        return xAxis;
    }

    public int getyAxis(){
        return yAxis;
    }

    public TileTypeEnum getType() {
        return type;
    }

    public List<String> getCreeks() {
        return creeks;
    }

    public void addCreeks(List<String> creeks) {
        this.creeks.addAll(creeks);
        listener.creekDiscovered(this);
    }

    public Map<ResourceEnum, String> getResources() {
        return resources;
    }

    public void removeResource(ResourceEnum resource) {
        resources.remove(resource);
    }

    public List<BiomeEnum> getBiomes() {
        return biomes;
    }

    public void addBiomes(List<BiomeEnum> biomes) {
        this.biomes.addAll(biomes);
        if (this.isUnknown() && !this.hasOnlyOceanBiomes(biomes)){
            this.setType(TileTypeEnum.GROUND);
        }
        else if (this.hasOnlyOceanBiomes(biomes) && this.isUnknown()) {
            this.setType(TileTypeEnum.SEA);
        }
        listener.biomeDiscovered(this);
    }

    public void setType(TileTypeEnum type) {
        TileTypeEnum previousType = this.type;
        this.type = type;
        listener.typeDiscovered(this, previousType, type);
    }

    public boolean isExploited() {
        return exploited;
    }

    public void setExploited(boolean exploited) {
        this.exploited = exploited;
    }

    public String toString() {
        switch (type) {
            case UNKNOWN: return " · ";
            case SEA: return " ~ ";
            case GROUND: return " G ";
            //ᚙ✰
        }
        return type.toString();
    }
}
