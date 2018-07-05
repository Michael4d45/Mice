package objects.shapes;

import graphics.entities.Entity;
import graphics.models.TexturedModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Rect
{
    private Entity entity;
    private static TexturedModel texture;

    private float x;
    private float y;
    private float width;
    private float height;

    public Rect(float side)
    {
        init(0, 0, side, side);
        entity.setScale(side);
    }

    public Rect(float width, float height)
    {
        init(0, 0, width, height);
    }

    public Rect(float x, float y, float w, float h)
    {
        init(x, y, w, h);
    }

    private void init(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        if (texture == null)
            texture = new TexturedModel("squares/red");
        entity = new Entity(texture, new Vector3f(0, 0, 1), 0, 0, 0, 0.1f);
    }

    public Entity getEntity()
    {
        return entity;
    }

    public void setLocation(float x, float y)
    {
        entity.setPosition(x, y);
        this.x = x;
        this.y = y;
    }

    public void setSize(float w, float h)
    {
        this.width = w;
        this.height = h;
    }

    public void setBounds(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setBounds(Rect r)
    {
        this.x = r.getX();
        this.y = r.getY();
        this.width = r.getWidth();
        this.height = r.getHeight();
    }

    public void translate(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    private boolean contains(float X, float Y)
    {
        float w = this.width;
        float h = this.height;
        if (w < 0 | h < 0)
        {
            return false;
        } else
        {
            float x = this.x;
            float y = this.y;
            if (X >= x && Y >= y)
            {
                w += x;
                h += y;
                return (w < x || w > X) && (h < y || h > Y);
            } else
            {
                return false;
            }
        }
    }

    public boolean contains(Rect r)
    {
        return this.contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    private boolean contains(float X, float Y, float W, float H)
    {
        float w = this.width;
        float h = this.height;
        if (w < 0 | h < 0 | W < 0 | H < 0)
        {
            return false;
        } else
        {
            float x = this.x;
            float y = this.y;
            if (X >= x && Y >= y)
            {
                w += x;
                W += X;
                if (W <= X)
                {
                    if (w >= x || W > w)
                    {
                        return false;
                    }
                } else if (w >= x && W > w)
                {
                    return false;
                }

                h += y;
                H += Y;
                if (H <= Y)
                {
                    return !(h >= y) && !(H > h);
                } else return !(h >= y) || !(H > h);
            } else
            {
                return false;
            }
        }
    }

    public boolean intersects(Rect r)
    {
        float tw = this.width;
        float th = this.height;
        float rw = r.getWidth();
        float rh = r.getHeight();
        if (rw > 0 && rh > 0 && tw > 0 && th > 0)
        {
            float tx = this.x;
            float ty = this.y;
            float rx = r.getX();
            float ry = r.getY();
            rw += rx;
            rh += ry;
            tw += tx;
            th += ty;
            return (rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry);
        } else
        {
            return false;
        }
    }

    public void add(float newx, float newy)
    {
        float x1 = Math.min(this.x, newx);
        float x2 = Math.max(this.x + this.width, newx);
        float y1 = Math.min(this.y, newy);
        float y2 = Math.max(this.y + this.height, newy);
        this.x = x1;
        this.y = y1;
        this.width = x2 - x1;
        this.height = y2 - y1;
    }

    private void grow(float h, float v)
    {
        entity.increaseScale(h);
        this.x -= h;
        this.y -= v;
        this.width += h * 2;
        this.height += v * 2;
    }

    public boolean isEmpty()
    {
        return this.width <= 0 || this.height <= 0;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof Rect))
        {
            return super.equals(obj);
        } else
        {
            Rect r = (Rect) obj;
            return this.x == r.x && this.y == r.y && this.width == r.width && this.height == r.height;
        }
    }

    public String toString()
    {
        return this.getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",width=" + this.width + ",height=" + this.height + "]";
    }

    private float getHeight()
    {
        return this.height;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    private float getWidth()
    {
        return this.width;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public float getX()
    {
        return this.x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    private float getY()
    {
        return this.y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void grow(float scale)
    {
        grow(scale, scale);
    }

    public boolean contains(Vector2f dot)
    {
        return contains(dot.x, dot.y);
    }
}
