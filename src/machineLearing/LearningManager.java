package machineLearing;

import objects.mice.Mouse;

public class LearningManager
{
    private static LearningManager sLearningManager;
    private GenePool genePool;

    private LearningManager()
    {
        genePool = GenePool.get();
    }

    public static LearningManager get()
    {
        if (sLearningManager == null)
            sLearningManager = new LearningManager();
        return sLearningManager;
    }

    public int getMove(Mouse mouse)
    {
        Gene gene = genePool.getGene(mouse);

        return gene.compute(mouse);
    }

    public void mouseDied(Mouse mouse)
    {
        genePool.mouseDied(mouse);
    }

    public int getScore()
    {
        return genePool.getScore();
    }

    public int getDeadSize()
    {
        return genePool.getDeadSize();
    }

    public int getPoolSize()
    {
        return genePool.getPoolSize();
    }
}
