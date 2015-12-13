package fr.unice.polytech.qgl.qdd.enums;

import java.util.ArrayList;

public enum BiomeEnum {

	OCEAN( "OCEAN" , "FISH"),
	LAKE( "LAKE","FISH"),
	BEACH( "BEACH", "QUARTZ" ),
	GRASSLAND("GRASSLAND","FUR"),

	MANGROVE("MANGROVE","FLOWER","WOOD"),
	TROPICAL_RAIN_FOREST("TROPICAL_RAIN_FOREST","FRUITS","SUGAR_CANE","WOOD"),
	TROPICAL_SEASONAL_FOREST("TROPICAL_SEASONAL_FOREST","FRUITS","SUGAR_CANE","WOOD"),

	TEMPERATE_DECIDUOUS_FOREST("TEMPERATE_DECIDUOUS_FOREST","WOOD"),
	TEMPERATE_RAIN_FOREST("TEMPERATE_RAIN_FOREST","FUR","WOOD"),
	TEMPERATE_DESERT("TEMPERATE_DESERT","QUARTZ","ORE"),

	TAIGA("TAIGA","ORE","WOOD"),
	SNOW("SNOW","ORE"),
	TUNDRA("TUNDRA","ORE","FUR"),
	ALPINE("ALPINE","FLOWER","ORE"),
	GLACIER("GLACIER","FLOWER","ORE"),

	SHRUBLAND("SHRUBLAND","FUR"),
	SUB_TROPICAL_DESERT("SUB_TROPICAL_DESERT"),
	;

	private String type;
	private ArrayList<String> ressources;

	BiomeEnum(String type, String... args){
		this.type=type;
		for( String arg : args )
			ressources.add( arg );
	}

	public String getName(){
		return type;
	}

	public ArrayList<String> getRessources(){
		return ressources;
	}

	public boolean hasGot(String ressource){
		for( String arg : ressources){
			if(arg==ressource){
				return true;
			}
		}
		return false;
	}
}
