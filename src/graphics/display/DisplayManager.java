package graphics.display;

import graphics.renderEngine.Loader;
import graphics.renderEngine.Renderer;
import graphics.shaders.ShaderProgram;
import graphics.shaders.StaticShader;
import machineLearing.LearningManager;
import objects.food.Foods;
import objects.mice.Mice;
import objects.mice.MyMouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.util.Date;

import static org.lwjgl.opengl.GL11.*;

public class DisplayManager implements Runnable
{
    private String title = "new display";

    private boolean render = false;


    public DisplayManager(String title)
    {
        this.title = title;
        start();
    }

    private void start()
    {
        Thread thread = new Thread(this, "Display");
        thread.start();
    }

    @Override
    public void run()
    {
        try
        {
            int width = 1280;
            int height = 720;

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            ContextAttribs context = new ContextAttribs(3, 3);

            Display.create(new PixelFormat(), context.withProfileCore(true));
        } catch (LWJGLException e)
        {
            e.printStackTrace();
        }

        System.out.println(glGetString(GL_VERSION));
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        ShaderProgram.loadAll();

        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        Loader loader = Loader.get();

        Mice mice = Mice.get();
        mice.generate(9);
        Foods foods = Foods.get();
        foods.generate(25);

        //MyMouse myMouse = MyMouse.get();
        //myMouse.setEntity(new Dots(0,0).getEntity());

        //ObjectManager objectManager = new ObjectManager();

        int totalDeadMice = 0;

        Date date1 = new Date();
        Date date2 = new Date();
        int count = 0;
        int totalCount = 0;
        int MAX_FPS = 4000;

        //objectManager.start();
        while (!Display.isCloseRequested())
        {
            //if (count % 10 == 0)
            //    myMouse.getKeyMove();

            int dead = mice.checkForDead();
            foods.checksIfEaten();

            mice.moveLearn();

            eventCheck();
            //myMouse.move();

            if (render)
            {
                renderer.prepare();
                shader.start();
                renderer.render(foods.getEntities(), shader);
                renderer.render(mice.getEntities(), shader);
                //renderer.render(foods.getBoxEntities(), shader);
                //renderer.render(mice.getBoxes(), shader);

                //renderer.render(myMouse.getEntity(), shader);
                //renderer.render(myMouse.getMouse().getHeadBoxEntity(), shader);
                //renderer.render(myMouse.getMouse().getSightEntities(), shader);
                //renderer.render(mice.getMice().firstElement().getSightEntities(), shader);

                shader.stop();

                //Display.sync(MAX_FPS);
            }
            Display.update();

            //get actual FPS
            count++;
            totalDeadMice += dead;
            totalCount++;
            if (count == MAX_FPS)
            {
                date2 = date1;
                date1 = new Date();
                float diff = (float) (date1.getTime() - date2.getTime()) / 1000;

                System.out.println("fps: " + (int) (MAX_FPS / diff));
                //System.out.println(mice.getMice().firstElement().getLife());
                //System.out.println(mice.getMice().firstElement().getSpeed());
                System.out.println("dead Mice " + totalDeadMice);
                //System.out.println("death score " + totalCount / (1+totalDeadMice));
                System.out.println("life score " + LearningManager.get().getScore());
                System.out.println("deadPool " + LearningManager.get().getDeadSize());
                System.out.println("genePool " + LearningManager.get().getPoolSize());
                System.out.println();

                count = 0;
            }

        }

        shader.cleanUp();
        loader.cleanUp();
        Display.destroy();
        //objectManager.stop();
    }

    private void eventCheck()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_1))
            render = true;
        if (Keyboard.isKeyDown(Keyboard.KEY_2))
            render = false;
    }
}
