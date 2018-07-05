package graphics.entities;

import graphics.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Entity
{
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
    {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz)
    {
        position.x += dx;
        position.y += dy;
        position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz)
    {
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }

    public void increaseScale(float value)
    {
        scale += value;
    }

    public TexturedModel getModel()
    {
        return model;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getRotX()
    {
        return rotX;
    }

    public float getRotY()
    {
        return rotY;
    }

    public float getRotZ()
    {
        return rotZ;
    }

    public float getScale()
    {
        return scale;
    }

    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
    }

    public void setScale(float side)
    {
        this.scale = side;
    }

    public void setPosition(Vector2f pos)
    {
        position.x = pos.x;
        position.y = pos.y;
    }
}
