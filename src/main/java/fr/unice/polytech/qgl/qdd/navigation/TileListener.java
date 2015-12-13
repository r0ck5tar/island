package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.TileTypeEnum;

/**
 * Created by danial on 12/13/2015.
 */
public interface TileListener {
    public void typeDiscovered(Tile tile, TileTypeEnum previousType, TileTypeEnum currentType);

    public void biomeDiscovered(Tile tile);

    public void creekDiscovered(Tile tile);
}
