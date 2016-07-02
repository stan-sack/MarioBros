package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Turtle;
import com.mygdx.game.Sprites.TileObjects.Brick;
import com.mygdx.game.Sprites.TileObjects.Coin;
import com.mygdx.game.Sprites.Enemies.Goomba;

/**
 * Created by stan on 5/06/16.
 */
public class B2WorldCreator {
    private Array<Goomba> goombas;
    private Array<Turtle> turtles;

    public Array<Goomba> getGoombas() {
        return goombas;
    }
    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //get(2) for the ground layer
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / MarioBros.PPM,
                    (rect.getHeight() / 2) / MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / MarioBros.PPM,
                    (rect.getHeight() / 2) / MarioBros.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MarioBros.OBJECT_BIT;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            new Coin(screen, object);

        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new Brick(screen, object);
        }

        //create all goombas
        goombas = new Array<Goomba>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Goomba(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }

        //create all turtles
        turtles = new Array<Turtle>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }
    }
}
