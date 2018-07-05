package objects.logic;

import objects.food.Cheese;
import objects.mice.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class ObjectManager
{
    public enum ObjectId
    {
        unknown,
        mouse,
        cheese
    }

    public static ObjectId getID(Object o)
    {
        if (o instanceof Mouse)
            return ObjectId.mouse;
        if (o instanceof Cheese)
            return ObjectId.cheese;
        return ObjectId.unknown;
    }

    public static float getDistance(Vector2f pos1, Vector2f pos2)
    {
        float xDiff = pos1.x - pos2.x;
        float yDiff = pos1.y - pos2.y;
        return (float) Math.sqrt((xDiff * xDiff + yDiff * yDiff));
    }

}
