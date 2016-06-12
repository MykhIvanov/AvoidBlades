package ua.kyiv.mykhailoivanov.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.kyiv.mykhailoivanov.AvoidBladesGame;
import ua.kyiv.mykhailoivanov.Button;

/**
 * Created by MykhailoIvanov on 5/20/2016.
 */
public class IntroScreen implements Screen {

    private float posX;
    private float posY;
    private AvoidBladesGame game;

    public IntroScreen(AvoidBladesGame gameIn)
    {
        game = gameIn;
        infoTexture = new Texture(Gdx.files.internal("intro.png"));
        backgroundTexture = new Texture (Gdx.files.internal("background.png"));
    }

    private Texture infoTexture;
    private Texture backgroundTexture;
    private float width;
    private float height;

    @Override
    public void show()
    {
        posX = 0;
        posY = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        height = width * infoTexture.getHeight() / infoTexture.getWidth();
        timeCount = 0;
    }

    private float timeCount;
    public void render(float delta)
    {
        game.batch.begin();
        game.batch.draw(backgroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(infoTexture, posX, posY, width, height);
        game.batch.end();
        if (posY > Gdx.graphics.getHeight() * 3 / 5)
            posY -= Button.ANIMATION_SPEED;
        else if (timeCount < 1)
        {
            timeCount += delta;
            posY -= Gdx.graphics.getHeight() / 300;
        }
        else if (posY + height >= 0)
        {
            posY -= Button.ANIMATION_SPEED * 2;
        }
        else
        {
            game.getMainMenuScreen().startAnimationFromGameMenu = true;
            game.setScreen(game.getMainMenuScreen());
        }

    }

    @Override
    public void resize(int width, int height) {

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
        backgroundTexture.dispose();
        infoTexture.dispose();
    }
}
