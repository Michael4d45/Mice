package objects.sensors;

import graphics.entities.Entity;
import objects.food.Cheese;
import objects.food.Foods;
import objects.shapes.Dots;
import org.lwjgl.util.vector.Vector2f;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import static objects.logic.ObjectManager.ObjectId;

public class Sight
{
    private float angle;
    private float depth;
    private int densityRadius;
    private int densityLayer;

    private Vector<Dots> dots;
    private Vector<Vector2f> dotsPos;

    public Sight(float angle, float depth, int densityR, int densityL)
    {
        init(angle, depth, densityR, densityL);
    }

    public Sight()
    {
        init(75, 0.4f, 15, 10);
    }

    private void init(float angle, float depth, int densityR, int densityL)
    {
        dotsPos = new Vector<>();
        dots = new Vector<>();
        for (int i = 0; i <= densityL * densityR; i++)
        {
            dotsPos.add(new Vector2f());
            dots.add(new Dots());
        }
        this.angle = angle;
        this.depth = depth;
        this.densityRadius = densityR;
        this.densityLayer = densityL;
        setSight(0, 0, 0, 0);
    }

    public void setSight(float x, float y, float direction, float distance)
    {
        int i = 0;
        dotsPos.elementAt(i).set(x, y);
        float angleIncrement = angle / densityLayer;
        float initialAngle = direction - angle / 2;
        for (int layer = 1; layer <= densityRadius; layer++)
        {
            for (float dotAngle = 0; dotAngle < angle; dotAngle += angleIncrement)
            {
                i++;
                float dist = (distance + layer) * depth;
                float ang = initialAngle + dotAngle;

                float xPos = x + (dist) * (float) Math.cos(Math.toRadians(ang)) / 10;
                float yPos = y + (dist) * (float) Math.sin(Math.toRadians(ang)) / 10;
                dotsPos.elementAt(i).set(xPos, yPos);
            }
        }
    }

    public Vector<Entity> getEntities()
    {
        Vector<Entity> dotEntities = new Vector<>();
        for (int i = 0; i < dots.size(); i++)
        {
            Dots dot = dots.elementAt(i);
            Vector2f dotPos = dotsPos.elementAt(i);
            dot.getEntity().setPosition(dotPos);
            dotEntities.add(dot.getEntity());
        }
        return dotEntities;
    }

    public Vector<Vector2f> cheesePos()
    {
        Map<Integer, ObjectId> visionOfObjects = see();
        Vector<Vector2f> pos = new Vector<>();
        for (int i : visionOfObjects.keySet())
        {
            if (visionOfObjects.get(i) == ObjectId.cheese)
                pos.add(dotsPos.elementAt(i));
        }
        return pos;
    }

    private Map<Integer, ObjectId> see()
    {
        Map<Integer, ObjectId> visionOfObjects = new TreeMap<>();
        for (int i = 0; i < dotsPos.size(); i++)
        {

            visionOfObjects.putAll(seeCheese(i));
            //visionOfObjects.putAll(seeMouse());
        }
        return visionOfObjects;
    }

    private Map<Integer, ObjectId> seeCheese(int pos)
    {
        Map<Integer, ObjectId> visionOfObjects = new TreeMap<>();
        Foods foods = Foods.get();
        Vector<Cheese> food = foods.getFoods();
        for (Cheese cheese : food)
        {
            Vector2f dot = dotsPos.elementAt(pos);
            if (cheese.getBox().contains(dot))
                visionOfObjects.put(pos, ObjectId.cheese);
        }
        return visionOfObjects;
    }

    public int getSightNum()
    {
        return dotsPos.size();
    }

    public boolean[] getSight()
    {
        boolean[] sight = new boolean[dotsPos.size()];
        Map<Integer, ObjectId> see = see();
        for (int i = 0; i < dotsPos.size(); i++)
            sight[i] = see.containsKey(i);

        return sight;
    }
}
