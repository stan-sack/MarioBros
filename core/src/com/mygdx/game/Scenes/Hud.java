package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MarioBros;

/**
 * Created by stan on 4/06/16.
 */
public class Hud {
    public Stage stage;
    // hud gets its own camera and viewport so because they will stay fixed while the game world
    // moves
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;

    //constructor takes spritebach
    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table();
        //place table at the top of the stage
        table.top();
        //set table to be size of stage
        table.setFillParent(true);

        //set label text
        countdownLabel = new Label(
                String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel = new Label(
                String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        timeLabel = new Label(
                String.format("TIME"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        levelLabel = new Label(
                String.format("1-1"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        worldLabel = new Label(
                String.format("WORLD"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        marioLabel = new Label(
                String.format("MARIO"),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add label to table and set to fill screen width (10 pixels padding)
        //if multiple cells have expandX property they will share width equally
        table.add(marioLabel).expandX().pad(10);
        table.add(worldLabel).expandX().pad(10);
        table.add(timeLabel).expandX().pad(10);
        //start new row
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //stage comonents are called actors
        stage.addActor(table);
    }


}
