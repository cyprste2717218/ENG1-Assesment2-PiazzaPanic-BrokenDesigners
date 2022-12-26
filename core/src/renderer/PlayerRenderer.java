package renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.brokendesigners.Player;
import java.util.ArrayList;

public class PlayerRenderer {

	ArrayList<Chef> players;


	public PlayerRenderer(SpriteBatch batch){

	}

	public void addPlayer(Player player, Texture texture){

	}

	public void addPlayerWithAnimation(Player player, TextureAtlas textureAtlas, float timeBetweenFrames, String frameIdentifiers){

	}

	public void render(SpriteBatch batch){


	}


	public class Chef{
		public Player player;
		public float depth;
		public Texture texture;
		public float stateTime = 0;
		public Animation animation;
		public Vector3 position;

		public void updatePosition(){
			this.position = this.player.getWorldPosition();
			this.depth = this.position.y;
		}

		public void renderChef(SpriteBatch batch){
			this.stateTime += Gdx.graphics.getDeltaTime();

			batch.begin();



		}

	}
}
