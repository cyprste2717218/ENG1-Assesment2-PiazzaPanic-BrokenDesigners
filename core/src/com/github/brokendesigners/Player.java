package com.github.brokendesigners;

import static java.lang.Math.abs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.github.brokendesigners.item.Item;
import com.github.brokendesigners.map.Kitchen;
import com.github.brokendesigners.map.KitchenCollisionObject;
import com.github.brokendesigners.map.interactable.Station;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import com.github.brokendesigners.renderer.PlayerRenderer;
import com.github.brokendesigners.textures.Animations;
import jdk.tools.jmod.Main;
import org.w3c.dom.css.Rect;

/**
 * Extended Class
 */
public class Player {
	Vector2 worldPosition; // Position of the player in world-coords

	float movementSpeed = 2 * Constants.UNIT_SCALE;  // Movement Speed of Chef differs between vertical and horizontal due to following 2 lines

	private float width; //NOTE:  NOT THE WIDTH OF CHEF SPRITE
	private float height;	//NOTE: NOT HEIGHT OF CHEF SPRITE
	private float interactionWidth; // height used for chef on chef interaction
	private float interactionHeight; // height used for chef on chef interaction

	public final float SPRITE_WIDTH;		// Width of chef when drawn
	public final float SPRITE_HEIGHT;		// Height of chef when drawn

	public Hand hand; // Items held by the chef

	Rectangle playerRectangle; // Rectangle representation of chef - Used for interactions/collisions
	Rectangle interactingPlayerRectangle; // Rectangle representation of chef - Used for chef on chef interactions
	private boolean selected; // Is Player Selected

	public Texture texture; // Texture of player if there are no animations for player
	public ArrayList<Animation<TextureRegion>> animations; // A list of animations
	public float keyFrame; // float updated every frame - used to determine which frame of animation of the player should render
	public boolean flipped; // is flipped or not -- NOT USED
	public boolean moving_disabled = false; // Is moving disabled? - Moving is disabled when interacting with most stations.
	public boolean locked = false;
	public static Sprite lockSprite;
	public Sound unlockFX;

	MainGame game;
	Kitchen kitchen;
	Match match;

	float renderOffsetX = 0;
	// ^^ Offset where the sprite will render relative to the invisible rectangle
	// which represents the players position/collision boundaries

	//A constructor made just for testing
	/**

	 Creates a Player instance with a given world position. The player's hand is instantiated for picking up items.

	 The player's sprite height and width are set to 5 units. The player's Rectangle is initialized with the default dimensions.

	 @param worldPosition the world position of the player
	 */
	public Player(Vector2 worldPosition){
		this.worldPosition = worldPosition;

		SPRITE_HEIGHT = 5; // Sets height of player to height of player's texture
		SPRITE_WIDTH = 5; // Sets width of player to width of player's texture
		hand = new Hand(); // Instantiates Hand so the player can pick items up
		boolean flipped = false;
		this.width = 18 * Constants.UNIT_SCALE; // default values for the dimensions of the Player Rectangle
		this.height = 4 * Constants.UNIT_SCALE; //
		this.renderOffsetX = -1; // Default renderOffsetX
		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.width, this.height);
	}




	/**
	 Created Constructor

	 Creates a Player instance with a texture, and adds it to the given PlayerRenderer's list of players.

	 @param renderer the renderer for the player

	 @param texture the texture of the player

	 @param worldPosition the world position of the player
	 */
	public Player(PlayerRenderer renderer, Texture texture, Vector2 worldPosition){

		this.worldPosition = worldPosition;

		SPRITE_HEIGHT = this.texture.getHeight() * Constants.UNIT_SCALE; // Sets height of player to height of player's texture
		SPRITE_WIDTH = this.texture.getWidth() * Constants.UNIT_SCALE; // Sets width of player to width of player's texture




		this.texture = texture;

		hand = new Hand(); // Instantiates Hand so the player can pick items up

		renderer.addPlayer(this); // Adds player to the renderer's "List of Players to Render every frame"

		boolean flipped = false;

		this.width = 18 * Constants.UNIT_SCALE; // default values for the dimensions of the Player Rectangle
		this.height = 4 * Constants.UNIT_SCALE; //
		this.renderOffsetX = -1; // Default renderOffsetX

		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.width, this.height);


	}
	/**

	 Instantiates player with array of animations.
	 * Array has 3 animations :
	 * 		Index 0 : Idle animation
	 * 		Index 1 : Move animation
	 * 		Index 2 : Interaction Animation
	 *
	 @param renderer the renderer for the player

	 @param animations the array of animations for the player

	 @param worldPosition the world position of the player

	 @param sprite_width the width of the player's sprite

	 @param sprite_height the height of the player's sprite

	 @param game the main game object

	 @param kitchen the kitchen object

	 @param match the match object
	 */
	public Player(PlayerRenderer renderer, ArrayList<Animation<TextureRegion>> animations, Vector2 worldPosition, float sprite_width, float sprite_height, MainGame game, Kitchen kitchen, Match match){

		this.worldPosition = worldPosition;

		SPRITE_HEIGHT = sprite_height;
		SPRITE_WIDTH = sprite_width;

		this.animations = animations;

		hand = new Hand();

		renderer.addPlayer(this);
		this.width = 18 * Constants.UNIT_SCALE;
		this.height = 4 * Constants.UNIT_SCALE;
		this.interactionWidth = 20 * Constants.UNIT_SCALE;
		this.interactionHeight = 36 * Constants.UNIT_SCALE;


		playerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.width, this.height);
		interactingPlayerRectangle = new Rectangle(worldPosition.x, worldPosition.y, this.interactionWidth, this.interactionHeight);

		this.unlockFX = Gdx.audio.newSound(Gdx.files.internal("audio/unlock.wav"));
		this.game = game;
		this.kitchen = kitchen;
		this.match = match;
	}

	/**
	* Gets player rectangle, used for calculating collisions and interactions.
	 */
	public Rectangle getPlayerRectangle() {
		return playerRectangle;
	}
	public Rectangle getInteractingPlayerRectangle()	{
		return interactingPlayerRectangle;
	}
	/**
	* updates position of rectangle - ideally size of rectangle should stay constant.
	 */
	private void updateRectangle(){
		playerRectangle.x = worldPosition.x;
		playerRectangle.y = worldPosition.y;
		interactingPlayerRectangle.x = worldPosition.x;
		interactingPlayerRectangle.y = worldPosition.y;
	}
	/**
	* returns worldPosition
	* Used by PlayerRenderer to render players in correct location.
	 */
	public Vector2 getWorldPosition(){
		return worldPosition;
	}
	/**
	* Processes movement inputs.
	* each axis has its own if statement to allow diagonal movement and sliding across collision objects without sticking.
	 */
	public void processMovement(ArrayList<KitchenCollisionObject> objects){

		if (selected && !moving_disabled) {

			if (Gdx.input.isKeyPressed(Keys.W)) {
				moveUp(objects);
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				moveDown(objects);
			}
			updateRectangle();
			if (Gdx.input.isKeyPressed(Keys.A)) {
				moveLeft(objects);
				if (!flipped){
					flipAnimations();
					flipped = true;
				}
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				moveRight(objects);
				if (flipped){
					flipAnimations();
					flipped = false;
				}

			}
			updateRectangle();
		}
	}
	/**
	* Flips animation set for the player.
	* Allows the player to look in both directions. be careful if reusing animations, the .flip() function is one way - if you flip it,
	* you have to call .flip(true, false) again to flip it back.
	 */
	public void flipAnimations(){
		for (Animation animation : animations){
			for (TextureRegion region : (TextureRegion[]) animation.getKeyFrames()){
				region.flip(true, false);
			}
		}
	}
	/**
	* Handles moving and collision detection for when you move up.
	* The move methods are separate because of special cases in the collision detection.
	* ^^ If a players position + their movement speed is inside an object, their position still needs to change,
	* so we set the players position to where they are making contact with the object they're colliding with.
	 */
	private boolean moveUp(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y += (getMovementSpeed());
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){

				this.worldPosition.y = object.getRectangle().y - this.height;

				return false;
			}
		}

		this.worldPosition.y += (getMovementSpeed());
		this.updateRectangle();
		return true;

	}
	/**

	 Moves the player down by the movement speed and checks for collision with kitchen objects.
	 If collision occurs, sets the player's world position to the top of the collided object and returns false.
	 Otherwise, updates the player's world position and returns true.
	 @param objects an ArrayList of KitchenCollisionObject instances to check for collision with
	 @return true if the player moves down without collision, false if collision occurs
	 */
	private boolean moveDown(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.y -= (getMovementSpeed());
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.y = object.getRectangle().y + object.getHEIGHT();
				return false;
			}
		}
		this.worldPosition.y -= (getMovementSpeed());
		this.updateRectangle();
		return true;

	}
	/**

	 Moves the player right by the movement speed and checks for collision with kitchen objects.
	 If collision occurs, sets the player's world position to the left of the collided object and returns false.
	 Otherwise, updates the player's world position and returns true.
	 @param objects an ArrayList of KitchenCollisionObject instances to check for collision with
	 @return true if the player moves right without collision, false if collision occurs
	 */
	private boolean moveRight(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x += (getMovementSpeed());
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x - this.width;
				return false;
			}
		}

		this.worldPosition.x += (getMovementSpeed());
		this.updateRectangle();
		return true;

	}
	/**

	 Moves the player left by the movement speed and checks for collision with kitchen objects.
	 If collision occurs, sets the player's world position to the right of the collided object and returns false.
	 Otherwise, updates the player's world position and returns true.
	 @param objects an ArrayList of KitchenCollisionObject instances to check for collision with
	 @return true if the player moves left without collision, false if collision occurs
	 */

	private boolean moveLeft(ArrayList<KitchenCollisionObject> objects){
		this.playerRectangle.x -= (getMovementSpeed());
		for (KitchenCollisionObject object : objects){
			if(Intersector.overlaps(object.getRectangle(), this.getPlayerRectangle())){
				this.worldPosition.x = object.getRectangle().x + object.getWIDTH();
				return false;
			}
		}
		this.worldPosition.x -= (getMovementSpeed());
		this.updateRectangle();
		return true;

	}
	/**

	 Picks up an item if the player is selected and intersects with a Station's interaction area.
	 If a Station is found, initiates the station's pickUp(Player player) method and returns true.
	 @param stations an ArrayList of Station instances to check for intersection with the player
	 @return true if the player picks up an item, false otherwise
	 @throws InvocationTargetException
	 @throws NoSuchMethodException
	 @throws InstantiationException
	 @throws IllegalAccessException
	 */
	public boolean pickUp(ArrayList<? extends Station> stations)
		throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					station.pickUp(this);
					return true;
				}
			}
		}
		return false;
	}
	/**

	 Handles dropping off of items.
	 Scans through an ArrayList of kitchen stations, finds if they are intersecting with the player's rectangle, and if so,
	 initiates a station.dropOff(Player player) method.
	 @param stations The ArrayList of kitchen stations to scan through.
	 @return true if a station was found and a dropOff method was initiated, false otherwise.
	 */
	public boolean dropOff(ArrayList<? extends Station> stations){
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					station.dropOff(this);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Sets whether or not player is selected. Used by the PlayerRenderer to help decide which frame to render at any
	 * given time.
	 */
	public void setSelected(boolean isSelected){
		this.selected = isSelected;
	}
	/**
	 * Returns if player is selected or not.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Handles Interacting with items.
	 * Scans through an array of kitchen objects, finds if they are intersecting with any, and if it does, it initiates
	 * a station.action(Player player) method.
	 */

	public Station getInteractingStation(ArrayList<? extends Station> stations){
		if (this.isSelected()) {
			for (Station station : stations) {
				if (Intersector.overlaps(station.getInteractionArea(), this.getPlayerRectangle())) {
					return station;
				}
			}
		}
		return null;
	}

	/**
	 * Created Method
	 * @param stations
	 * @return
	 */
	public boolean interact(ArrayList<? extends Station> stations){
		Station interactingStation = getInteractingStation(stations);
		if(interactingStation == null) return false;
		interactingStation.action(this);
		return true;
	}

	/**
	 * Created Method
	 */
	public void lockPlayer()	{
		this.locked = true;
		this.disableMovement();

	}

	/**
	 * Created Method
	 */
	public void unlockPlayer()	{
		this.locked = false;
		this.enableMovement();
		unlockFX.play();
		this.match.subtractMoney(10);
		System.out.println("Player Unlocked");
	}

	/**
	 * Created Method
	 */
	public void activateLockSprite(SpriteBatch spriteBatch, int index)  {
		lockSprite = new Sprite(new Texture(Gdx.files.internal("items/lock.png")));
		spriteBatch.begin();
		if (this.locked)	{
			spriteBatch.draw(lockSprite, (float) (kitchen.getPlayerSpawnPoints().get(index).x+0.5), (float) (kitchen.getPlayerSpawnPoints().get(index).y+0.5), 24 * Constants.UNIT_SCALE, 24 * Constants.UNIT_SCALE);
		}
		spriteBatch.end();
	}

	/**
	 * Created Method
	 *
	 * A function to find the player that is being interacted with
	 */
	private Player getInteractingPlayer(ArrayList<Player> lockedPlayerList)	{
		if (this.isSelected())	{
			for (Player player : lockedPlayerList)	{
				if (this != player)	{
					System.out.println(this.getInteractingPlayerRectangle());
					System.out.println(player.getInteractingPlayerRectangle());
					if (Intersector.overlaps(this.getInteractingPlayerRectangle(), player.getInteractingPlayerRectangle()))	{
						return player;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Created Method
	 */
	public Player playerInteract(ArrayList<Player> lockedPlayerList)	{
		Player interactingPlayer = getInteractingPlayer(lockedPlayerList);
		if (interactingPlayer == null) return null;
		if (interactingPlayer.locked && this.match.getIntMoney() >= 10)	{
				interactingPlayer.unlockPlayer();
		}
		return interactingPlayer;
	}

	/*
	 * disposes of textures in memory.
	 */
	public void dispose(){
		if (this.texture != null){
			this.texture.dispose();
		}
	}

	/**
	 * Disables movement of player - called when player starts interacting with stations - also helps PlayerRenderer
	 * decide which frame to render.
	 */
	public void disableMovement(){
		this.moving_disabled = true;
	}
	/**
	 * Enables movement
	 */
	public void enableMovement(){
		this.moving_disabled = false;
	}
	/**
	 * Sets width of player's rectangle.
	 */
	public void setWidth(float width) {
		this.width = width;
		playerRectangle.width = this.width;
	}
	/**
	 * Sets height of player's rectangle.
	 */
	public void setHeight(float height) {
		this.height = height;
		playerRectangle.height = this.height;
	}

	/**
	 * Sets renderOffsetX
	 */
	public void setRenderOffsetX(float renderOffsetX) {
		this.renderOffsetX = renderOffsetX;
	}
	/**
	 * Returns renderOffsetX - Used by PlayerRenderer to render player in the right place
	 * relative to the player's hitbox
	 */
	public float getRenderOffsetX() {
		return renderOffsetX;
	}


	public void setMovementSpeed(float speed){
		movementSpeed = speed;
	}
	public float getMovementSpeed(){
		return movementSpeed;
	}
	public Item getTopOfHand()	{
		return hand.heldItems.get(0);
	}

	public boolean isLocked()	{
		return locked;
	}
	public void setPlayerRectangle(Rectangle rectangle)	{
		playerRectangle = rectangle;
	}
	public void setMatch(Match match)	{
		this.match = match;
	}
}