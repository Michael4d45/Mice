package machineLearing;

import java.util.Arrays;

public class Net
{
    private Layer[] neuralNet;

    Net()
    {
    }

    public Layer[] getNeuralNet()
    {
        return neuralNet;
    }

    /**
     * layers tells how many nodes for each layer
     *
     * @param layers
     * @return number of variables to be filled
     */
    int generate(int[] layers)
    {
        neuralNet = new Layer[layers.length - 1];
        int size = layers.length;
        int varNum = 0;
        for (int i = 0; i < size - 1; i++)
        {
            int col = layers[i];
            int row = layers[i + 1];
            neuralNet[i] = new Layer(new float[row][col], new float[row]);
            varNum += (col * row) + row;
        }
        return varNum;
    }

    void init(float[] variables)
    {
        int varPos1 = 0;
        int varPos2 = 0;
        for (Layer net : neuralNet)
        {
            varPos2 += net.getVarNum();
            float[] netInit = Arrays.copyOfRange(variables, varPos1, varPos2);
            varPos1 = varPos2;
            net.setVars(netInit);
        }
    }

    float[] compute(float[] input)
    {
        for (Layer net : neuralNet)
        {
            input = net.compute(input);
        }
        return input;
    }

    float[] compute(boolean[] input)
    {
        float[] output = new float[0];
        if(neuralNet.length > 0)
        {
            output = neuralNet[0].compute(input);
            for (int i = 1; i < neuralNet.length; i++)
            {
                output = neuralNet[i].compute(output);
            }
        }

        return output;
    }
}
