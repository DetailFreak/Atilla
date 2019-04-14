package com.mygdx.atilla;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

	private OrthographicCamera camera;
	private ShapeRenderer shape_renderer;
	private Ball balls[];
	private Vector3 gravity;
	private Boundary top, bottom, left, right, diag;
	private int width;
	private int height;
	private int numballs;

/*	private Vector3 position;
	private boolean touched = false;
	private Vector3 velocity;
	private Vector3 gravity;
	private Vector3 ratio;
*/
	public void create(){
		shape_renderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		gravity = new Vector3(0,-0.2f,0);
		numballs = 1;
		balls = new Ball[numballs];

		for(int i = 0;i < numballs; ++i) {
			Random random = new Random();
			float x = random.nextInt( (int) (width - 40)) + 20;
			float y = random.nextInt( (int) (height - 40)) + 20;
			//balls[i] = new Ball(new Vector3(x, y, 1), new Vector3(5*(x/width), 5*(y/height), 0), gravity, 10);
			balls[i] = new Ball(new Vector3(100, 100, 1), new Vector3(3, 3, 0), gravity, 10);

		}
		bottom = new Boundary(new Vector3(0,1, 0));
		right = new Boundary(new Vector3(-1,0, width));
		top = new Boundary(new Vector3(0,-1, height));
		left = new Boundary(new Vector3(1, 0, 0));
		//diag = new Boundary(new Vector2(3*width/4.0f, 0), new Vector2(width, width/4.0f), true);

	}

	public void render() {
		// Logic

		for(int i = 0; i< numballs; ++i) {
			balls[i].step();
			balls[i].handleCollisions(top);
			balls[i].handleCollisions(bottom);
			balls[i].handleCollisions(right);
			balls[i].handleCollisions(left);
			//balls[i].handleCollisions(diag);
		}
		// Drawing
		Gdx.gl.glClearColor(0, 0, 0,0 );
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

		shape_renderer.setAutoShapeType(true);
		shape_renderer.begin(ShapeRenderer.ShapeType.Filled);
		shape_renderer.setColor(Color.WHITE);
		shape_renderer.line(new Vector2(3*width/4.0f, 0), new Vector2(width, width/4.0f));

		for (int i = 0 ; i<numballs; ++i)
			balls[i].draw(shape_renderer);

		shape_renderer.end();
	}

	public void dispose() {
		shape_renderer.dispose();
	}
}
