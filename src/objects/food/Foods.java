package objects.food;

import graphics.entities.Entity;
import objects.shapes.Rect;

import java.util.Vector;

public class Foods
{
    private Vector<Cheese> food;
    private static Foods sFoods;

    private Foods()
    {
        food = new Vector<>();
    }

    public static Foods get()
    {
        if (sFoods == null)
            sFoods = new Foods();
        return sFoods;
    }

    public Vector<Cheese> getFoods()
    {
        return food;
    }

    private void add(Cheese cheese)
    {
        food.add(cheese);
    }

    public Vector<Entity> getEntities()
    {
        Vector<Entity> entities = new Vector<>();
        for (Cheese cheese : food)
            entities.add(cheese.getEntity());
        return entities;
    }

    public Vector<Entity> getBoxEntities()
    {
        Vector<Entity> entities = new Vector<>();
        for (Cheese cheese : food)
            entities.add(cheese.getBoxEntity());
        return entities;
    }

    public Vector<Rect> getBoxes()
    {
        Vector<Rect> entities = new Vector<>();
        for (Cheese cheese : food)
            entities.add(cheese.getBox());
        return entities;
    }

    public void generate(int numFoods)
    {
        for (int i = 0; i < numFoods; i++)
        {
            Cheese cheese = new Cheese();
            add(cheese);
        }
    }

    public void checksIfEaten()
    {
        int numFood = food.size();
        for (int i = 0; i < numFood; i++)
        {
            if (food.elementAt(i).isEaten())
                food.elementAt(i).move();
        }
    }
}
