package objects.food;

import graphics.entities.Entity;
import graphics.models.TexturedModel;
import objects.shapes.Rect;
import org.lwjgl.util.vector.Vector3f;

public class Cheese
{
    private static TexturedModel cheeseModel = null;
    private Entity entity;

    private Rect box;
    private boolean eaten = false;

    Cheese()
    {
        if (cheeseModel == null)
            cheeseModel = new TexturedModel("cheese/cheese");
        float scale = 0.05f;
        entity = new Entity(cheeseModel, new Vector3f(getRandomPos(), getRandomPos(), 1), 0, 0, 0, scale);
        float boxScale = 0.03f;
        box = new Rect(boxScale);
        setBox();
    }

    private void setBox()
    {
        Vector3f pos = entity.getPosition();
        box.setLocation(pos.x, pos.y);
    }

    public Rect getBox()
    {
        setBox();
        return box;
    }

    Entity getBoxEntity()
    {
        setBox();
        return box.getEntity();
    }

    public Entity getEntity()
    {
        return entity;
    }

    private float getRandomPos()
    {
        return (float) (Math.random() - 0.5f) * 2;
    }

    public float getsEaten()
    {
        if (eaten)
            return 0;
        eaten = true;
        return (float) 5;
    }

    boolean isEaten()
    {
        return eaten;
    }

    void move()
    {
        entity.setPosition(getRandomPos(), getRandomPos());
        eaten = false;
    }
}
