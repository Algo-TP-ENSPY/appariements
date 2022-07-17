import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Match match = new Match();
        Word[] words = Utils.extract("src/data.copy");
        ArrayList<Word> wordArrayList = Utils.extractList("src/data.copy");
//        ArrayList<Word[]> words1 =  match.calculateOnlog(wordArrayList,wordArrayList);
//        ArrayList<Word[]> words1a =  match.calculateOn(words,words);
//        ArrayList<Word[]> words2 =  match.calculateOn2(words,words);
//        Utils.Algorithm algorithm, Word[] reference_data, Word[] computational_data,int threshold
//        System.out.println("Doing fork-join with O(n2) algorithm");


        // one
/*        long time1 = System.currentTimeMillis();
        ProcessOptimizer processOptimizer = new ProcessOptimizer(Utils.Algorithm.O_N_2,words,words,50);
        ForkJoinPool pool = new ForkJoinPool();
        ArrayList<Word[]> words_optimized = pool.invoke(processOptimizer);
        long diff = System.currentTimeMillis() -  time1;
        System.out.println("Process carried out in "+diff+" milliseconds");*/



        //two

        long time2 = System.currentTimeMillis();
        ProcessOptimizer processOptimizer2 = new ProcessOptimizer(Utils.Algorithm.O_N,words,words,50);
        ForkJoinPool pool2 = new ForkJoinPool();
        ArrayList<Word[]> words_optimized2 = pool2.invoke(processOptimizer2);
        long diff2 = System.currentTimeMillis() -  time2;
        System.out.println("Process carried out in "+diff2+" milliseconds");



        //three

        long time3 = System.currentTimeMillis();
        ProcessOptimizer processOptimizer3 = new ProcessOptimizer(Utils.Algorithm.O_LN,words,words,50);
        ForkJoinPool pool3 = new ForkJoinPool();
        ArrayList<Word[]> words_optimized3 = pool3.invoke(processOptimizer3);
        long diff3 = System.currentTimeMillis() -  time3;
        System.out.println("Process carried out in "+diff3+" milliseconds");


//        System.out.println("Obtained "+words_optimized.size()+" matches from "+words.length+" words!");
//        ProcessOptimizer()
//        System.out.println("hi");
//        ArrayList<Word[]> words2 =  match.calculateOn2(words,words);
//        match.printMatches(words2);
//        match.printMatches(words1);
//        match.printMatches(words1a);
//        match.printMatches(words_optimized);
//        System.out.println("words- log; "+words1.size()+" n2; "+words2.size()+" n3; "+words1a.size());

    }

}
