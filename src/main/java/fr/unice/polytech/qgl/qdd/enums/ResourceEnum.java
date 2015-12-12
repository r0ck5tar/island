package fr.unice.polytech.qgl.qdd.enums;

import java.util.ArrayList;

public enum ResourceEnum {

	WOOD("WOOD","MANGROVE","TEMPERATE_RAIN_FOREST","TEMPERATE_DECIDUOUS_FOREST","TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	FISH("FISH","OCEAN","LAKE"),
	FLOWER("FLOWER","MANGROVE","ALPINE","GLACIER"),
	FUR("FUR","GRASSLAND","TEMPERATE_RAIN_FOREST","TUNDRA","SHRUBLAND"),
	QUARTZ("QUARTZ","BEACH","TEMPERATE_DESERT"),
	FRUITS("FRUITS","TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	SUGAR_CANE("SUGAR_CANE","TROPICAL_RAIN_FOREST","TROPICAL_SEASONAL_FOREST"),
	ORE("ORE","TEMPERATE_DESERT","ALPINE","GLACIER","SNOW","TAIGA","TUNDRA");



	private String resource;
	private ArrayList<String> biomes;

	ResourceEnum(String resource, String... args){
		this.resource=resource;
		for( String arg : args )
			biomes.add( arg );
	}

	public ArrayList<String> getBiomes(){
		return biomes;
	}
}