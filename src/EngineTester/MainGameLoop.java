package EngineTester;

import Engine.DisplayManager;
import Engine.Loader;
import Engine.RawModel;
import Engine.Renderer;
import org.lwjgl.opengl.Display;

public class MainGameLoop
{

    public static void main(String args[])
    {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                //left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                //top right triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        while (!Display.isCloseRequested())
        {
            renderer.prepare();
            //gameLogic
            renderer.rener(model);
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
