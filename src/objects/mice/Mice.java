package objects.mice;

import graphics.entities.Entity;

import java.util.Vector;

public class Mice
{
    private Vector<Mouse> mice;
    private static Mice sMice;

    private Mice()
    {
        mice = new Vector<>();
    }

    public static Mice get()
    {
        if (sMice == null)
            sMice = new Mice();
        return sMice;
    }

    Vector<Mouse> getMice()
    {
        return mice;
    }

    private void add(Mouse mouse)
    {
        mice.add(mouse);
    }

    public Vector<Entity> getEntities()
    {
        Vector<Entity> entities = new Vector<>();
        for (Mouse mouse : mice)
            entities.add(mouse.getEntity());
        return entities;
    }

    public Vector<Entity> getBoxes()
    {
        Vector<Entity> entities = new Vector<>();
        for (Mouse mouse : mice)
            entities.add(mouse.getHeadBoxEntity());
        return entities;
    }

    public int checkForDead()
    {
        int numMice = mice.size();
        int deadMice = 0;
        for (int i = 0; i < numMice; i++)
        {
            if (mice.elementAt(i).isDead())
            {
                deadMice++;
                mice.elementAt(i).init();
            }
        }
        return deadMice;
    }

    public void generate(int numMice)
    {
        for (int i = 0; i < numMice; i++)
        {
            Mouse mouse = new Mouse();
            add(mouse);
        }
    }

    public synchronized void moveRandom()
    {
        for (Mouse mouse : mice)
            mouse.moveRandom();
    }

    public void move()
    {
        for (Mouse mouse : mice)
            mouse.move();
    }

    public void moveLearn()
    {
        for (Mouse mouse : mice)
            mouse.moveLearn();
    }
}
