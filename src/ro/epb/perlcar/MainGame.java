package ro.epb.perlcar;

import android.content.Context;

import com.badlogic.gdx.Game;

public class MainGame  extends Game {

	
	@Override
	public void create() {
		setScreen(new MainMenu(this));
		
	}
}