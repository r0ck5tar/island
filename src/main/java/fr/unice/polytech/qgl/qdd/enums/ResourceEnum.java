package fr.unice.polytech.qgl.qdd.enums;

import java.util.ArrayList;

public enum ResourceEnum {

	WOOD("MANGROVE","TEMPERATE_RAIN_FOREST","TEMPERATE_DECIDUOUS_FOREST","TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	FISH("OCEAN","LAKE"),
	FLOWER("MANGROVE","ALPINE","GLACIER"),
	FUR("GRASSLAND","TEMPERATE_RAIN_FOREST","TUNDRA","SHRUBLAND"),
	QUARTZ("BEACH","TEMPERATE_DESERT"),
	FRUITS("TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	SUGAR_CANE("TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	ORE("TEMPERATE_DESERT","ALPINE","GLACIER","SNOW","TAIGA","TUNDRA");

	private ArrayList<String> biomes = new ArrayList<>();

	ResourceEnum(String... args){
		for( String arg : args )
			biomes.add( arg );
	}

	public ArrayList<String> getBiomes(){
		return biomes;
	}
}