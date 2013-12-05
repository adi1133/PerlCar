package ro.epb.perlcar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LevelScreen implements Screen {

	private Stage stage;
	private MainGame game;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private Body groundBody;
	LevelScreen(MainGame game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugRenderer.render(world, stage.getCamera().combined);
		

        groundBody.setTransform(0, -1, (float) Math.toRadians(Gdx.input.getRoll()) );
        
		world.step(delta, 6, 6);
		Camera cam = stage.getCamera();
		cam.position.set(chassis.getPosition(), 0);
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	private void addGround()
	{
	    BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.KinematicBody;
        bodyDef.position.set(0,-1);
        
        FixtureDef fixtureDef2 = new FixtureDef();
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-20,-1,20,-1);
        
        fixtureDef2.shape = edgeShape;
        
        groundBody =  world.createBody(bodyDef);
        groundBody.createFixture(fixtureDef2);
        edgeShape.dispose();
	}
	Body chassis;
	private void addCar()
	{
		
		BodyDef bdf = new BodyDef();
		bdf.type = BodyType.DynamicBody;
		
		Body wheelA, wheelB;
		
		//add chasis
		{
			FixtureDef fd = new FixtureDef();
			PolygonShape carShape = new PolygonShape();
			carShape.set(new float[]
					{
					-1.5f, -0.5f,
					1.5f, -0.5f,
					1.5f, 0.0f,
					0.0f, 0.9f,
					-1.15f, 0.9f,
					-1.5f, 0.2f
					});
			fd.density = 0.1f;
			fd.shape = carShape;
			
			bdf.position.set(0.0f, 1.0f);
			
			chassis =  world.createBody(bdf);

			chassis.createFixture(fd);
			carShape.dispose();
		}
		
		
		////////////////////
		//add wheels
		{
			FixtureDef fd = new FixtureDef();
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(0.4f);
			fd.shape = cshape;
			fd.density = 1.0f;
			fd.friction = 0.9f;
			
			bdf.position.set(-1.0f, 0.35f);
			wheelA = world.createBody(bdf);
			wheelA.createFixture(fd);
			bdf.position.set(1.0f, 0.4f);
			wheelB = world.createBody(bdf);
			wheelB.createFixture(fd);
			
			cshape.dispose();
			
			
			
			
		} 
		
		//add wheel joins
		
		WheelJointDef jd = new WheelJointDef();
		jd.type = JointType.WheelJoint;
		jd.bodyA = chassis;
		jd.enableMotor = true;
		jd.maxMotorTorque = 0.1f;
		jd.motorSpeed = -10;
		jd.initialize(chassis, wheelA, wheelA.getPosition(), Vector2.Y);
		world.createJoint(jd);
		
		jd.initialize(chassis, wheelB, wheelB.getPosition(), Vector2.Y);
		world.createJoint(jd);
		
		
	}
	
	@Override
	public void show() {
		stage = new Stage();
		stage.setCamera(new OrthographicCamera(stage.getWidth()/100, stage.getHeight()/100));
		Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        
        
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        
        BodyDef def = new BodyDef();
        def.type = BodyType.DynamicBody;
        def.position.set(0, 1);
        def.bullet = true;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
       // fixtureDef.friction = 1;
      //  fixtureDef.restitution = 1;
        
        CircleShape circle = new CircleShape();
        circle.setRadius(1);
        fixtureDef.shape = circle;
        
        
        //world.createBody(def).createFixture(fixtureDef);
        circle.dispose();
        
        
        addGround();
        addCar();
        
    
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
