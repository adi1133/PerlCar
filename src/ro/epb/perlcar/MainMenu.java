package ro.epb.perlcar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu implements Screen {

	private MainGame game;
	public MainMenu(MainGame game) {
		this.game = game;
	}

	private Stage stage;
	private Skin mainUi;
	@Override
	public void render(float delta) {
		stage.act(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		Table.drawDebug(stage);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);  
		mainUi = new Skin(Gdx.files.internal("ui/main.json"));
		TextButtonStyle style = mainUi.get(TextButtonStyle.class);
		
		
		style.font = new BitmapFont();
		style.font.setScale(3);

		Table table = new Table( mainUi );
		table.setFillParent(true);
		
		////
		table.debug();
		////
		stage.addActor( table );
        
        final TextButton[] buttons = new TextButton[]{
        		new TextButton("Start level -1", style),
        		new TextButton("Exit", style)
        };
	    
		for(TextButton button: buttons)
		{
			table.add(button);
			table.row().fillX();
		}
		table.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(actor == buttons[0])
					game.setScreen(new LevelScreen(game));
				else if(actor == buttons[1])
					Gdx.app.exit();
				
			}
			
		});

	}

	@Override
	public void hide() {
		mainUi.dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
