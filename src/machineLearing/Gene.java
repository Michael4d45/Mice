package machineLearing;

import objects.mice.Mouse;

import java.util.Arrays;

public class Gene implements Comparable
{
    private Net net;
    private int varNum;
    private float score = 0;
    private float[] DNA;
    private int[] layers;
    private int lives = 25;

    public Gene(Mouse mouse)
    {

        this.net = new Net();

        layers = new int[4];

        layers[0] = mouse.getSightNum();
        layers[1] = 10;//arbitrary
        layers[2] = 5;//arbitrary
        layers[3] = mouse.getMaxOutput();

        varNum = net.generate(layers);
    }

    public Gene(float[] DNA, int[] layers)
    {
        this.net = new Net();
        this.layers = layers;
        varNum = net.generate(layers);
        init(DNA);
    }

    public Gene(Gene mom, Gene dad)
    {
        if ((mom.DNA.length != dad.DNA.length) || !Arrays.equals(mom.layers, dad.layers))
            return;
        int randomPos = (int) (mom.DNA.length * Math.random());
        DNA = new float[mom.DNA.length];
        System.arraycopy(mom.DNA, 0, DNA, 0, randomPos);
        System.arraycopy(dad.DNA, randomPos, DNA, randomPos, dad.DNA.length - randomPos);
        layers = Arrays.copyOf(mom.layers, mom.layers.length);

        //percent of DNA that mutates
        float mutationThreshold = 0.1f;

        for (int i = 0; i < DNA.length; i++)
        {
            double random = Math.random() * 100;
            if (random < mutationThreshold)
                DNA[i] = getRandomVar(DNA[i]);
        }


        this.net = new Net();
        varNum = net.generate(layers);
        init(DNA);
    }


    public Net getNet()
    {
        return net;
    }

    public int getVarNum()
    {
        return varNum;
    }

    public void init()
    {
        float[] variables = new float[varNum];
        for (int i = 0; i < varNum; i++)
        {
            variables[i] = getRandomVar();
        }
        init(variables);
    }

    private float getRandomVar()
    {
        return (float) (Math.random() * 20) - 10;
    }

    private float getRandomVar(float cur)
    {
        return cur + (((float) Math.random() - 0.5f) / 16);
    }

    private void init(float[] DNA)
    {
        this.DNA = DNA;
        net.init(DNA);
    }

    int compute(Mouse mouse)
    {
        boolean[] sightInput = mouse.getSightInput();

        float[] out = net.compute(sightInput);
        int max = 0;
        float tempMax = out[0];

        for (int i = 1; i < out.length; i++)
            if (out[i] > tempMax)
            {
                max = i;
                tempMax = out[i];
            }

        return max;
    }

    void setScore(float score)
    {
        if (score > this.score)
            this.score = score;
    }

    @Override
    public int compareTo(Object o)
    {
        if (!(o instanceof Gene))
            return -1;

        Gene gene = (Gene) o;
        int result = Float.compare(gene.score, score);
        if(result == 0)
            result = 1;
        return result;
    }

    float getScore()
    {
        return score;
    }

    int getLives()
    {
        return lives;
    }

    void reduceLife()
    {
        lives--;
    }
}
