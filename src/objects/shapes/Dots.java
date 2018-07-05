package objects.shapes;

import graphics.entities.Entity;
import graphics.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Dots
{
    private Entity entity;
    private static TexturedModel texture;

    public Dots(float x, float y)
    {
        init(x, y);
    }

    public Dots(Vector2f vector)
    {
        init(vector.x, vector.y);
    }

    public Dots()
    {
        init(0, 0);
    }

    private void init(float x, float y)
    {
        if (texture == null)
            texture = new TexturedModel("dots/red");
        entity = new Entity(texture, new Vector3f(x, y, 1), 0, 0, 0, 0.01f);
    }

    public Entity getEntity()
    {
        return entity;
    }
}
