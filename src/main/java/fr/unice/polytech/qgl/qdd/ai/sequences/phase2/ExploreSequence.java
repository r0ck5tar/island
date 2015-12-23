package fr.unice.polytech.qgl.qdd.ai.sequences.phase2;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hakim on 12/13/2015.
 */
public class ExploreSequence extends MoveSequence {
    private int counter;
    private static final int MAX_ITERATIONS = 8;

    public ExploreSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList, null);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        if(isExplorable(nav.map().currentTile())) {
            destinationTile = null;
            return explore(); }

        if(destinationTile == null) {
            destinationTile = nav.finder().neighbouringTiles().stream().filter(this::isExplorable).findFirst().orElse(null);
        }
        if (destinationTile == null) {
            //destinationTile = nav.finder().getRandomNearbyTile(15);
            destinationTile = nav.finder().getNearestPotentiallyExploitableTile();
        }

        return super.execute();
    }

    @Override
    public boolean completed() {
        return !isExplorable(nav.map().currentTile()) || counter>8;
    }

    public boolean isExplorable(Tile tile) {
        if (!tile.isSea() && !tile.isExplored() && !tile.isExploited()) {
            if(tile.getBiomes().isEmpty()) {
                return true;
            }

            Set<Biome> preferredBiomes = new HashSet<>();
            checkList.getResourcesToCollect().forEach(resource -> preferredBiomes.addAll(resource.getBiomes()));
            return tile.getBiomes().stream().filter(preferredBiomes::contains).toArray().length >0;
        }
        return false;
    }
}
