package com.mygdx.atilla;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.abs;

public class Ball {
    private int radius;
    private Vector3 pos;
    private Vector3 vel;
    private Vector3 acc;
    private float loss;
    private Color color;
    private boolean moving;

    public Ball(Vector3 pos, Vector3 vel, Vector3 acc, int radius){
        this.pos = pos;
        this.radius = radius;
        this.vel = vel;
        this.acc = acc;
        this.color = new Color(0.5f,0.5f ,1 ,1);
        loss = 0.0f;
        moving = true;
    }

    public void handleCollisions(Boundary bound) {

        if (bound.getDistance(this.pos) > this.radius || !moving) {
            return;
        }

        float displacement = radius - bound.getDistance(this.pos);  // calculate the displacement into the boundary
        float velocity_projection = vel.dot(bound.getNormal());     // get the projection of velocity
        float time_fraction;

        if( abs(velocity_projection) > 0.002 )
            time_fraction = abs(displacement/velocity_projection);
        else
            time_fraction = 0;

       // System.out.printf("displacement = %f, velocity_projection = %f, time_fraction = %f%n", displacement, velocity_projection, time_fraction);
        //System.out.printf("position = %f, vel = %f%n", pos.y, vel.y);
        pos.sub(vel.cpy().scl(time_fraction));                            // move to the point of collision.
        vel.sub(acc.cpy().scl(time_fraction));                            //
        //System.out.printf("position = %f, vel = %f%n", pos.y, vel.y);

        vel.sub(bound.getNormal().scl(2 * vel.dot(bound.getNormal()) ));    // reflect velocity;

        //System.out.printf("position = %f, vel = %f%n", pos.y, vel.y);

        vel.add(acc.cpy().scl(time_fraction));
        pos.add(vel.cpy().scl(time_fraction));

        vel.scl(1-loss);

        if (abs(vel.x) < 1) vel.x = 0;
        if (abs(vel.y) < 1) vel.y = 0;
    }

    public void step() {
        if(moving) {
            pos = pos.add(vel);
            vel = vel.add(acc);
        }
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(color);
        sr.circle(pos.x, pos.y, radius);
    }
}

