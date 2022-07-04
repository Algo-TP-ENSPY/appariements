import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ProcessOptimizer  extends RecursiveTask<ArrayList<Word[]>> {

    Utils.Algorithm algorithm;
    Word[] reference_data;
    Word[] computational_data;
    int threshold;

    public ProcessOptimizer(Utils.Algorithm algorithm, Word[] reference_data, Word[] computational_data,int threshold){
            this.algorithm=algorithm;
            this.reference_data=reference_data;
            this.computational_data=computational_data;
            this.threshold=threshold;
    }
    @Override
    protected ArrayList<Word[]> compute() {
        Match match = new Match();
        if (computational_data.length <= threshold) {
            System.out.println("Starting a new compute for data of length: "+computational_data.length);
            switch (algorithm) {
                case O_N:
                    return match.calculateOn(computational_data, reference_data);
                case O_N_2:
                    return match.calculateOn2(computational_data, reference_data);
                case O_LN:
                    ArrayList<Word> comp = new ArrayList<>(Arrays.asList(computational_data));
                    ArrayList<Word> ref = new ArrayList<>(Arrays.asList(reference_data));
                    return match.calculateOnlog(comp, ref);
            }
        }
        System.out.println(computational_data.length+" is greater than threshold value ("+threshold+"). Dividing...");
        // take the first one,
        ProcessOptimizer p1  = new ProcessOptimizer(algorithm,reference_data,
                Arrays.copyOfRange(computational_data,0,threshold),threshold);
        // take the  rest
        p1.fork();
        ProcessOptimizer p2  = new ProcessOptimizer(algorithm,reference_data,
                Arrays.copyOfRange(computational_data,threshold,computational_data.length),threshold);
        ArrayList<Word[]> matches = p2.compute();
        matches.addAll( p1.join());
        return matches;
    }
}
