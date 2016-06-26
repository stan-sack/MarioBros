package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MarioBros;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Mushroom;
import com.mygdx.game.Sprites.Mario;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

import java.util.PriorityQueue;

/**
 * Created by stan on 4/06/16.
 */
public class PlayScreen implements Screen {

    private MarioBros game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private FitViewport gameport;
    private Hud hud;

    //tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //mario
    private Mario player;

    //music
    private Music music;

    private Array<Item> items;
    private PriorityQueue<ItemDef> itemsToSpawn;




    public PlayScreen(MarioBros game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        //create camera
        gamecam = new OrthographicCamera();
        //create viewport
        gameport = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM,
                                   MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);
        //create hud
        hud = new Hud(game.batch);

        //load and render map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

        // set camera centred on centre of screen
        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        //create world
        creator = new B2WorldCreator(this);

        //create the player mario
        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.play();

        items = new Array<Item>();
        itemsToSpawn = new PriorityQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if (!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if (idef.type == Mushroom.class){
                items.add(new Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }

    public void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.jump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0),
                                             player.b2body.getWorldCenter(),
                                             true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0),
                                             player.b2body.getWorldCenter(),
                                             true);
        }
    }

    public void update(float dt){
        handleInput(dt);
        handleSpawningItems();
        world.step(1/60f, 6, 2);
        player.update(dt);
        for(Enemy enemy: creator.getGoombas()){
            enemy.update(dt);
        }

        for (Item item : items){
            item.update(dt);
        }
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
        hud.update(dt);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render the game map
        renderer.render();

        // render the bod2ddebug lines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy: creator.getGoombas()){
            enemy.draw(game.batch);
            if (enemy.getX() < player.getX() + 224 / MarioBros.PPM)
                enemy.b2body.setActive(true);
        }
        for (Item item : items){
            item.draw(game.batch);
        }
        game.batch.end();

        //render the hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    public World getWorld(){
        return world;
    }

    public TiledMap getMap(){
        return map;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
