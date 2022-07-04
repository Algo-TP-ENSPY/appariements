import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Match match = new Match();
        Word[] words = Utils.extract("src/data.copy");
        ArrayList<Word> wordArrayList = Utils.extractList("src/data.copy");
        ArrayList<Word[]> words1 =  match.calculateOnlog(wordArrayList,wordArrayList);
        ArrayList<Word[]> words1a =  match.calculateOn(words,words);
//        Utils.Algorithm algorithm, Word[] reference_data, Word[] computational_data,int threshold
        ProcessOptimizer processOptimizer = new ProcessOptimizer(Utils.Algorithm.O_LN,words,words,100);
        ForkJoinPool pool = new ForkJoinPool();
        ArrayList<Word[]> words_optimized = pool.invoke(processOptimizer);
//        ProcessOptimizer()
//        System.out.println("hi");
//        ArrayList<Word[]> words2 =  match.calculateOn2(words,words);
//        match.printMatches(words1);
        match.printMatches(words_optimized);
//        System.out.println("words- log; "+words1.size()+" n2; "+words2.size()+" n3; "+words1a.size());

    }

}
