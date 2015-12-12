package fr.unice.polytech.qgl.qdd;


import fr.unice.polytech.qgl.qdd.enums.BiomeEnum;
import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;
import org.omg.CORBA.UNKNOWN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danial on 10/12/15.
 */
public class Tile {
    //private boolean isGround;
    private TileTypeEnum type;
    private List<BiomeEnum> biomes;
    private List<Resource> resources;
    private List<String> creeks;
    private String condition;
    private int xAxis;
    private int yAxis;

    public Tile(int xAxis, int yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        type = TileTypeEnum.UNKNOWN;
        biomes = new ArrayList<>();
        resources = new ArrayList<>();
        creeks = new ArrayList<>();
    }

    public String toString() {
        switch (type) {
            case UNKNOWN: return " · ";
            case SEA: return " ~ ";
            case GROUND: return " ᚙ ";
            //ᚙ✰
        }
        return type.toString();
    }

    public int getxAxis(){
        return xAxis;
    }

    public int getyAxis(){
        return yAxis;
    }

    public TileTypeEnum getType() {
        return type;
    }

    public void setType(TileTypeEnum type) {
        this.type = type;
    }

    public boolean isGround() {
        return type.equals(TileTypeEnum.GROUND);
    }

    public boolean isUnknown() {
        return type.equals(TileTypeEnum.UNKNOWN);
    }

    public void addBiomes(List<BiomeEnum> biomes) {
        this.biomes.addAll(biomes);
        if (this.isUnknown()){
            this.setType(TileTypeEnum.GROUND);
        }
    }
}
