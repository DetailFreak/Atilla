package com.mygdx.atilla;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class Boundary {

    private Vector3 parameters; // normal direction
    private Vector3 normal;
    private Vector2 lower_limit;
    private Vector2 upper_limit;

    public Boundary(Vector2 a, Vector2 b, boolean clockwise) {
        lower_limit = new Vector2(min(a.x,b.x), min(a.y, b.y));
        upper_limit = new Vector2(max(a.x, b.x), max(a.y, b.y));

        if(!clockwise)
            normal = new Vector3(b.y - a.y, a.x - b.x, 0);
        else
            normal = new Vector3(a.y - b.y, b.x - a.x, 0);

        normal.nor();
    }

    public Boundary(Vector3 parameters) {
        this.parameters = parameters;
        normal = new Vector3(parameters.x, parameters.y,0);
        normal.nor();
    }

    public float getDistance(Vector3 point)
    {
        return parameters.dot(point);
    }

    public Vector3 getNormal(){
        return normal.cpy();
    }

}
