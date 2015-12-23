package fr.unice.polytech.qgl.qdd.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public enum Resource {

	WOOD(Biome.MANGROVE,Biome.TEMPERATE_RAIN_FOREST,Biome.TEMPERATE_DECIDUOUS_FOREST,Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
	FISH(Biome.OCEAN, Biome.LAKE),
	FLOWER(Biome.MANGROVE, Biome.ALPINE,Biome.GLACIER),
	FUR(Biome.GRASSLAND, Biome.TEMPERATE_RAIN_FOREST, Biome.TUNDRA, Biome.SHRUBLAND),
	QUARTZ(Biome.BEACH, Biome.TEMPERATE_DESERT),
	FRUITS(Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
	SUGAR_CANE(Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
	ORE(Biome.TEMPERATE_DESERT, Biome.ALPINE, Biome.GLACIER, Biome.SNOW, Biome.TAIGA, Biome.TUNDRA);

	private Set<Biome> biomes = new HashSet<>();

	Resource(Biome ... args){
		for( Biome arg : args )
			biomes.add( arg );
	}

	public Set<Biome> getBiomes(){
		return biomes;
	}
}