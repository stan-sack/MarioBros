package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class MarioBros extends Game {

	// set virtual width of game
	public static final int V_WIDTH = 400;

	// set virtual height of game
	public static final int V_HEIGHT = 208;

	// set pixel per meter scale
    public static final float PPM = 100;

	//declare sprite batch (can only have one because its memory intensive)
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		// set screen to play screen
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		//this calls game.render which then renders the current game screen
 		super.render();
	}
}
