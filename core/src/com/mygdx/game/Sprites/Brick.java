package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by stan on 5/06/16.
 */
public class Brick extends InteractivaveTileObject{
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
    }
}
