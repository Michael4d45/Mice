package objects.mice;

import graphics.entities.Entity;
import graphics.models.TexturedModel;
import machineLearing.LearningManager;
import objects.food.Cheese;
import objects.food.Foods;
import objects.sensors.Sight;
import objects.shapes.Rect;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Vector;

public class Mouse
{
    private static final float MAX_SPEED = 10;
    private static final float STARTING_LIFE = 100;
    private static TexturedModel mouseModel = null;
    private Entity entity;
    private Sight sight;
    private float scale = 0.1f;

    private Rect headBox;
    private float life = STARTING_LIFE;
    private boolean alive = true;

    Mouse()
    {

        if (mouseModel == null)
            mouseModel = new TexturedModel("mice/mouse3");
        entity = new Entity(mouseModel, new Vector3f(0, 0, 1), 0, 0, 0, scale);
        float headScale = 0.03f;
        headBox = new Rect(headScale);
        sight = new Sight();
        setHeadBox();
        init();
    }

    void init()
    {
        alive = true;
        life = STARTING_LIFE;
        entity.setPosition(getRandomPos(), getRandomPos());
    }

    private void setHeadBox()
    {
        Vector3f pos = entity.getPosition();
        float headPos = 0.04f;
        headBox.setLocation(pos.x + getXDirection() * headPos, pos.y + getYDirection() * headPos);
    }

    Entity getHeadBoxEntity()
    {
        if (!alive)
            return null;
        setHeadBox();
        return headBox.getEntity();
    }

    private Rect getHeadBox()
    {
        setHeadBox();
        return headBox;
    }

    public Entity getEntity()
    {
        if (!alive)
            return null;
        return entity;
    }

    private float getRandomPos()
    {
        return (float) (Math.random() - 0.5f) * 2;
    }

    void moveRandom()
    {
        getRandomMove();
        move();
    }

    private void getRandomMove()
    {
        int random = (int) (Math.random() * 4);
        getMove(random);
    }

    private void getMove(int move)
    {
        switch (move)
        {
            case 0:
                decelerate();
                break;
            case 1:
                accelerate();
                break;
            case 2:
                turnRight();
                break;
            case 3:
                turnLeft();
                break;
        }
    }

    private static final int MAX_OUTPUT_ITER = 2;
    private static final int MAX_OUTPUT = 4;

    public int getMaxOutput()
    {
        return (int) Math.pow(MAX_OUTPUT, MAX_OUTPUT_ITER);
    }

    void moveLearn()
    {
        if (isDead())
            return;
        LearningManager learn = LearningManager.get();
        int move = learn.getMove(this);
        for (int i = MAX_OUTPUT_ITER - 1; i >= 0; i--)
        {
            int power = (int) Math.pow(MAX_OUTPUT, i);
            int moveMod = move / power;
            getMove(moveMod);
            move -= power * moveMod;
        }
        move();
    }


    private static float max_angle = 0;

    private float acceleration = 1f;
    private float turn = 2f;
    private float speed = 0;

    void accelerate()
    {
        if (speed > MAX_SPEED)
            return;
        speed += acceleration;
    }

    void decelerate()
    {
        if (speed < -1 * MAX_SPEED)
            return;
        speed -= acceleration;
    }

    private void floor()
    {
        float friction = 0.05f;
        if (Math.abs(speed) <= friction)
            speed = 0;
        else
        {
            if (speed > 0)
                speed -= friction;
            else
                speed += friction;
        }
    }

    void move()
    {
        if (!alive)
            return;
        entity.increasePosition(getXSpeed(), getYSpeed(), 0);


        eatFood();
        floor();
        boundCheck();

        if (life <= 0)
            die();
        else
        {
            life -= 0.1;
        }
        //life -= speed;
    }

    void turnRight()
    {
        if (!alive)
            return;
        entity.increaseRotation(0, 0, -1 * turn);

        boundCheck();
    }

    void turnLeft()
    {
        if (!alive)
            return;
        entity.increaseRotation(0, 0, turn);

        boundCheck();
    }

    private void boundCheck()
    {
        Vector3f pos = entity.getPosition();
        if (pos.x > 1)
            entity.increasePosition(-2, 0, 0);
        if (pos.x < -1)
            entity.increasePosition(2, 0, 0);
        if (pos.y > 1)
            entity.increasePosition(0, -2, 0);
        if (pos.y < -1)
            entity.increasePosition(0, 2, 0);
    }

    private float getSpeed()
    {
        return speed / 10000;
    }

    private float getXSpeed()
    {
        return getSpeed() * getXDirection();
    }

    private float getYSpeed()
    {
        return getSpeed() * getYDirection();
    }

    private float getXDirection()
    {
        return (float) Math.cos(Math.toRadians(entity.getRotZ()));
    }

    private float getYDirection()
    {
        return (float) Math.sin(Math.toRadians(entity.getRotZ()));
    }


    private void die()
    {
        alive = false;
        LearningManager learn = LearningManager.get();
        learn.mouseDied(this);
    }

    private boolean isAlive()
    {
        return alive;
    }

    private void eatFood()
    {
        if (!alive)
            return;
        Foods foods = Foods.get();
        Vector<Cheese> food = foods.getFoods();
        for (Cheese cheese : food)
        {
            if (cheese.getBox().intersects(getHeadBox()))
            {
                eat(cheese.getsEaten());
            }
        }
/*
        Mice mices = Mice.get();
        Vector<Mouse> mice = mices.getMice();
        for (Mouse mouse : mice)
        {
            if (mouse.getHeadBox().intersects(getHeadBox()) &&
                    mouse.scale < scale / 2)
            {
                eat(mouse.getsEaten());
            }
        }*/
    }

    private float getsEaten()
    {
        if (!alive)
            return 0;
        die();
        return 5.0f;
    }

    private void eat(float eaten)
    {
        //grow(eaten);
        life += eaten;
        if (life > maxLife)
            maxLife = life;
    }

    private void grow(float eaten)
    {
        eaten /= 500;

        scale += eaten;
        entity.increaseScale(eaten);
        headBox.grow(eaten);
        if (scale > 2)
            die();
    }

    public float getAcceleration()
    {
        return acceleration;
    }

    public void setAcceleration(float acceleration)
    {
        this.acceleration = acceleration;
    }

    public float getTurn()
    {
        return turn;
    }

    public void setTurn(float turn)
    {
        this.turn = turn;
    }

    public float getLife()
    {
        return life;
    }

    public boolean isDead()
    {
        return !isAlive();
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public Vector<Entity> getSightEntities()
    {
        see();
        return sight.getEntities();
    }

    private void see()
    {
        if (!alive)
            return;
        Vector3f pos = entity.getPosition();
        sight.setSight(pos.x, pos.y, entity.getRotZ(), entity.getScale() / 2);
    }

    private Vector<Vector2f> seeCheese()
    {
        return sight.cheesePos();
    }

    public int getSightNum()
    {
        return sight.getSightNum();
    }

    private float maxLife = 0;

    public float getScore()
    {
        return maxLife;
    }

    void setLife(int i)
    {
        life = i;
    }

    public boolean[] getSightInput()
    {
        return sight.getSight();
    }

    public void setAlive()
    {
        init();
    }
}
