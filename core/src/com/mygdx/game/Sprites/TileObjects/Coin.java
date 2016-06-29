package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Mushroom;
import com.mygdx.game.Sprites.Mario;

/**
 * Created by stan on 5/06/16.
 */
public class Coin extends InteractivaveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN =  28;
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (getCell().getTile() != tileSet.getTile(BLANK_COIN)) {
            Gdx.app.log("Coin", "Collision");
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
            if(object.getProperties().containsKey("mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                        body.getPosition().y + 16 / MarioBros.PPM), Mushroom.class));
                MarioBros.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            } else {
                MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }
        } else {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }
    }
}
