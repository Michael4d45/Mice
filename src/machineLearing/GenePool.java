package machineLearing;

import objects.mice.Mouse;

import java.util.*;

public class GenePool
{
    private static GenePool sGenePool;
    private Map<String, Gene> nets;
    private Set<Gene> deadPool;
    private Vector<Gene> genePool;
    private int score = 0;

    private GenePool()
    {
        nets = new TreeMap<>();
        deadPool = new TreeSet<>();
        genePool = new Vector<>();
    }

    public static GenePool get()
    {
        if (sGenePool == null)
            sGenePool = new GenePool();

        return sGenePool;
    }

    public Gene getGene(Mouse mouse)
    {
        String key = mouse.toString();
        if (!nets.containsKey(key))
        {
            Gene gene;
            if (deadPool.size() > 50 || !genePool.isEmpty())
                gene = mutation();
            else
            {
                gene = new Gene(mouse);
                gene.init();
            }
            nets.put(key, gene);
        }

        return nets.get(key);
    }

    private Gene mutation()
    {
        if (genePool.isEmpty())
            createGenePool();
        return (genePool.remove(0));
    }

    private void createGenePool()
    {
        if (deadPool.isEmpty())
            return;

        int i = 0;
        for (Gene gene:deadPool)
        {
            genePool.add(gene);
            if(i > 50)//strictness
                break;
            i++;
        }

        float totalScore = 0;
        for (Gene gene : genePool)
            totalScore += gene.getScore();

        float averageScore = totalScore / genePool.size();

        //if (averageScore > score)
            score = (int) averageScore;

        deadPool.clear();

        Vector<Gene> mutateTheseGenes = new Vector<>(genePool);
        for (int j = 0; j < 3; j++)
            addMutations(mutateTheseGenes);
    }

    int getScore()
    {
        return score;
    }

    private void addMutations(Vector<Gene> mutateTheseGenes)
    {
        for (int i = 0; i < mutateTheseGenes.size(); i++)
            for (int j = 0; j < mutateTheseGenes.size(); j++)
                genePool.add(getPairMutations(mutateTheseGenes.elementAt(i), mutateTheseGenes.elementAt(j)));
    }

    private Gene getPairMutations(Gene mom, Gene dad)
    {
        return new Gene(mom, dad);
    }

    void mouseDied(Mouse mouse)
    {
        String key = mouse.toString();
        if (nets.containsKey(key))
        {
            Gene gene = nets.get(key);
            gene.setScore(mouse.getScore());
            if (gene.getLives() <= 0)
            {
                nets.remove(key);
                if (gene.getScore() > 105)
                    deadPool.add(gene);
            } else
                mouse.setAlive();
            gene.reduceLife();
        }
    }

    int getDeadSize()
    {
        return deadPool.size();
    }

    int getPoolSize()
    {
        return genePool.size();
    }
}
