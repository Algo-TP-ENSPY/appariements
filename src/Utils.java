import java.io.*;
import java.util.*;

public class Utils {
    public enum Algorithm {
        O_N,O_N_2,O_LN
    }
    /**
     * Read the file into an array
     * @param filepath String
     * @return ArrayList<String>
     * @throws IOException when the read process fails
     */
    public static Word[] extract(String filepath) throws IOException {
        // File path is passed as parameter
        File file = new File(
                filepath);
        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String st;
        StringBuilder stringBuilder = new StringBuilder();
        while ((st = br.readLine()) != null)
            stringBuilder.append(st).append("\n");
        String file_contents = Config.CASE_SENSITIVE?stringBuilder.toString().trim():stringBuilder.toString().trim().toLowerCase();
        String[] contents = String.join(" ",file_contents.split("\n")).split(" ");
        Set<String> set = new HashSet<>(Arrays.asList(contents));
        Word[] words = new Word[set.size()];
        int i=0;
        for(String str: set){
            words[i]  =new Word(str)  ;
            i++;
        }
        return words;

    }

    /**
     * Read file into an arraylist
     * @param filepath String
     * @return ArrayList<Word>
     * @throws IOException when the read process fails
     */
    public static ArrayList<Word> extractList(String filepath)  throws IOException{
        Word[] words =  extract(filepath);
        return new ArrayList<Word>(Arrays.asList(words));
    }

    /**
     * Get hash table from file data content
     * @param werdlist input
     * @return Hashtable
     */
    public static Hashtable<String, ArrayList<String>> extractTable(Word[] werdlist)  {
        Hashtable<String, ArrayList<String>> stringHashtable = new Hashtable<>();
        for (Word word :
                werdlist) {
            for (int i = 0; i< word.getText().length(); i++) {
                String key = word.getText().substring(0,i+1);
                ArrayList<String> hashContent = stringHashtable.get(key);
                if(hashContent!=null){
                    hashContent.add(word.getText());
                    stringHashtable.put(key,hashContent);
                }else{
                    ArrayList<String> content= new ArrayList<>();
                    content.add(word.getText());
                    stringHashtable.put(key,content);
                }
            }
        }
        return stringHashtable;
    }
}
