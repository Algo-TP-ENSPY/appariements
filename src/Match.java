import java.io.IOException;
import java.util.*;

public class Match implements MatchInterface {
    public ArrayList<Word[]> calculateOn2(Word[] words,Word[] reference) {
        ArrayList<Word[]> list = new ArrayList<>();
        long start_time = System.currentTimeMillis();
        for (Word value : words) {
            for (Word word : reference) {
                //the method is_matching already ignores the case of equality
                String match = value.getMatchingStrings(word);
                if (match != null) {
                    Word[] matches = new Word[]{value, word, new Word(match)};
//                    printSingleMatch(matches);
                    list.add(matches);
                }
            }
        }
        long difference = System.currentTimeMillis() - start_time;

        System.out.println("Computed "+reference.length+" words to get "+list.size()+" matches in "+difference+" milliseconds!");
        return list;
    }

    public ArrayList<Word[]> calculateOn(Word[] words,Word[] reference) {
        long start_time = System.currentTimeMillis();
        Hashtable<String, ArrayList<String>> wordPrefixTable = Utils.extractTable(reference); //reference our data
        ArrayList<Word[]> arrayList =  new ArrayList<>();
        //we will like to keep track of couples found in order not to put twice
        Hashtable<String,Boolean> combinationFound= new Hashtable<>();
        for (Word word :
                words) {
            for (int y = 0; y <word.length(); y++) {
                String prefix = (word.getText().substring(y));
                if(wordPrefixTable.get(prefix)!=null){
                    for (String match :
                            wordPrefixTable.get(prefix)) {
                        if(match.equals(word.getText())){
                            continue;
                        }
                        String mapkey=word.getText()+"__"+match;
                        if(combinationFound.get(mapkey)==null){
                            combinationFound.put(mapkey,true);
                            arrayList.add(new Word[]{word, new Word(match), new Word(prefix)});
                        }
                    }
                }
            }
        }
        long difference = System.currentTimeMillis() - start_time;

        System.out.println("Computed "+reference.length+" words to get "+arrayList.size()+" matches in "+difference+" milliseconds!");

        return  arrayList;
    }

    public ArrayList<Word[]> calculateOnlog(ArrayList<Word> words,ArrayList<Word> reference) {
        ArrayList<Word[]> returns = new ArrayList<>();
        HashMap<String, String> result = new HashMap<>();
        Set<Word> wordSorted = new TreeSet<>(reference); //with respect to our reference
        long start_time = System.currentTimeMillis();
        for (Word word : words) {
            result.putAll(word.binaryMatch(wordSorted.toArray(new Word[0])));
        }
        long difference = System.currentTimeMillis() - start_time;
        System.out.println("Computed "+reference.size()+" in "+difference+" milliseconds!");


        // OUTSIDE
        // send it to
        Set<String> rs = result.keySet();
        for (String r :
                rs) {
//            System.out.println(r.split(" ")[0]+"------"+r);
            returns.add(new Word[]{new Word(r.split(" ")[0]), new Word(r.split(" ")[1]), new Word(result.get(r))});
        }
        return returns;
    }

//    public Set<String> pack(ArrayList<Word[]> result){
//        Set<String> stringSet = new HashSet<>();
//        for (Word[] worda:
//                result) {
//            String t1 = (worda[0].getText()+"|").replace(worda[2].toString()+"|","\033[4m"+worda[2]+"\033[0m");
//            String t2 = ("|"+worda[1].getText()).replace("|"+worda[2].toString(),"\033[4m"+worda[2]+"\033[0m");
//            stringSet.add(t1+" - "+t2);
//        }
//        return stringSet;
//    }
//    public int count_results(ArrayList<Word[]> results){
//        return pack(results).size();
//    }

    public void printMatches(ArrayList<Word[]> arrayList) {
//        Set<String> stringSet = pack(arrayList);
        System.out.println("=============== Match Results ===============");
        System.out.println("Occurrences: " + arrayList.size());
        System.out.println("Results:");
        for (Word[] worda :
                arrayList) {
            printSingleMatch(worda);
        }
    }

    public void printSingleMatch(Word[] worda) {
//        the " " symbol is just to make the printing select only at the end and start respectively
//        e.g when you have a word with 2 'm's , the underline will underline both 'm's e.g mnitadoma
        String t1 = (worda[0].getText()+" " ).replace(worda[2].toString() +" ", "\033[4m" + worda[2] + "\033[0m");
        String t2 = ( " "+worda[1].getText()).replace( " "+worda[2].toString(), "\033[4m" + worda[2] + "\033[0m");
        System.out.println(t1 + " - " + t2);
    }
}
