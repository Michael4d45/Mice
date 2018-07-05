package machineLearing;

import java.util.Arrays;

public class Layer
{

    public static void main(String[] args)
    {
        float[][] matrix = new float[2][2];
        float[] bias = new float[2];
        float[] input = new float[2];

/*
        matrix[0][0] = 0;
        matrix[1][0] = 1;
        matrix[0][1] = 2;
        matrix[1][1] = 3;

        bias[0] = 5;
        bias[1] = 6;
*/

        input[0] = 3;
        input[1] = 4;


        Layer layer = new Layer(matrix, bias);

        float[] vars = new float[layer.getVarNum()];

        vars[0] = 0;
        vars[1] = 1;
        vars[2] = 2;
        vars[3] = 3;
        vars[4] = 5;
        vars[5] = 6;

        layer.setVars(vars);

        float[] result = layer.compute(input);
        for (float[] array : matrix)
            System.out.println(Arrays.toString(array));
        System.out.println();
        System.out.println(Arrays.toString(bias));
        System.out.println();
        System.out.println(Arrays.toString(result));
    }

    private int row;
    private int col;
    private float[][] matrix;
    private float[] bias;

    Layer(float[][] matrix, float[] bias)
    {
        this.row = bias.length;
        this.col = matrix[0].length;
        this.matrix = matrix;
        this.bias = bias;
    }

    float[] compute(boolean[] input)
    {
        float[] result = new float[row];
        Arrays.fill(result,0f);

        for (int j = 0; j < row; j++)
        {
            float sum = 0;
            for (int i = 0; i < col; i++)
                if (input[i]) sum += matrix[j][i];

            result[j] = sig(sum + result[j] + bias[j]);
        }

        return result;
    }

    float[] compute(float[] input)
    {
        float[] result = new float[row];
        Arrays.fill(result,0f);

        for (int j = 0; j < row; j++)
        {
            float sum = 0;
            for (int i = 0; i < col; i++)
                sum += matrix[j][i] * input[i];

            result[j] = sig(sum + result[j] + bias[j]);
        }

        return result;
    }

    /**
     * 1 / (1 + e^-x)
     *
     * @param x input
     * @return result of sigmoid function
     */
    private static float sig(float x)
    {
        float e = (float) Math.E;
        float denominator = 1 + (float) Math.pow(e, (-1) * x);
        return 1 / denominator;
    }

    int getVarNum()
    {
        return row * col + row;
    }

    void setVars(float[] netInit)
    {
        int pos = 0;
        for (int j = 0; j < col; j++)
        {
            for (int i = 0; i < row; i++)
            {
                matrix[i][j] = netInit[pos];
                pos++;
            }
        }
        for (int i = 0; i < row; i++)
        {
            bias[i] = netInit[pos];
            pos++;
        }
    }
}
