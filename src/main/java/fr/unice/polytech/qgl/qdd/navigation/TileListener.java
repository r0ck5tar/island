package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by danial on 12/13/2015.
 */
public interface TileListener {
    public void typeDiscovered(Tile tile, String previousType, String currentType);

    public void biomeDiscovered(Tile tile);

    public void creekDiscovered(Tile tile);

    public void tileExploited(Tile tile);
}
