package objects.mice;

import graphics.entities.Entity;
import org.lwjgl.input.Keyboard;

public class MyMouse
{
    private Mouse mouse;
    private static MyMouse sMyMouse;

    private MyMouse()
    {
        this.mouse = new Mouse();
        //mouse.getEntity().increaseScale(1f);
    }

    public static MyMouse get()
    {
        if (sMyMouse == null)
            sMyMouse = new MyMouse();
        return sMyMouse;
    }

    public Mouse getMouse()
    {
        return mouse;
    }

    public void setMouse(Mouse mouse)
    {
        this.mouse = mouse;
    }

    public Entity getEntity()
    {
        return mouse.getEntity();
    }

    public void getKeyMove()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            mouse.accelerate();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            mouse.decelerate();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            mouse.turnLeft();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            mouse.turnRight();
        }
    }

    public int getLife()
    {
        return (int) mouse.getLife();
    }

    public void setEntity(Entity entity)
    {
        mouse.setEntity(entity);
    }

    public void move()
    {
        mouse.setLife(100);
        mouse.move();
    }
}
