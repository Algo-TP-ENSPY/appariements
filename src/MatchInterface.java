import java.io.IOException;
import java.util.ArrayList;

public interface MatchInterface {
     public ArrayList<Word[]> calculateOn2(Word[] words,Word[] reference) ;
     public ArrayList<Word[]> calculateOnlog(ArrayList<Word> words,ArrayList<Word> reference) ;
     public ArrayList<Word[]> calculateOn(Word[] words,Word[] reference);
}
