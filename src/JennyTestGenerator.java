import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class JennyTestGenerator {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {
        test();
    }
    public static int generateTest(int numbers, boolean special) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {
        //        test();
//        System.out.println("here");
        Set<String> generatedWords = new HashSet<>();
        Hashtable<Character,Set<Character>> befores = new Hashtable<>();
        Hashtable<Character,Set<Character>> afters = new Hashtable<>();
        Set<Character> starters = new HashSet<>();
        Set<Character> enders = new HashSet<>();
        Random random = new Random();
//        boolean special=false;
        int i=0;
        while(i<numbers){
            boolean destroyedPattern=false;
            String word = generateRandomWord(random.nextInt(25),special);
//            System.out.println("Generated: "+word);
            if(generatedWords.contains(word)){
//                System.out.println(""+word+" is already present");
                continue;
            }

            if(word.isEmpty()){
                continue;
            }
//            System.out.println("here");
            if(word.length()==1 && (starters.contains(word.charAt(0))||enders.contains(word.charAt(0)))){
                continue;
            }else if(word.length()==1){
                starters.add(word.charAt(0));
                enders.add(word.charAt(0));
                generatedWords.add(word);
                continue;
            }
            //ensure no matching rightward xxxx-----> (reading afters, moving from back to front)
            char[] charArrays = word.toCharArray();
//            System.out.println("Filter1: "+String.valueOf(charArrays));
            for (int j = charArrays.length-1 ; j >=0 ; j--) {
                //get current char
                if(j<=charArrays.length-2){
//                    System.out.println("Checking: "+charArrays[j]);
                    if(afters.get(charArrays[j])!=null){
//                        System.out.println("Is alredy present in afters: "+charArrays[j]+", "+afters.get(charArrays[j]).toString());
                        //ensure that the letters after is not used.
                        if(!destroyedPattern && afters.get(charArrays[j]).contains(charArrays[j+1])){
//                            System.out.println("Have to generated new: "+charArrays[j+1]);
                            //generate a new character
                            String newC = generateRandomWord(1,special);

//                            System.out.println("Generated: "+newC);
                            while (afters.get(charArrays[j]).contains(newC.charAt(0)) || (
                                    ((j+1)==charArrays.length-1) && starters.contains(newC.charAt(0))
                            )){
                                newC = generateRandomWord(1,special);
//                                System.out.println("Generated: "+newC);
                            }
//                            System.out.println("Finally took: "+newC);
                            charArrays[j+1]=newC.charAt(0);
                            if((j+1)==charArrays.length-1){
                                enders.add(newC.charAt(0));
                            }
                            //we've destroyed the pattern and we are sure.
                            destroyedPattern=true;
                        }
                        afters.get(charArrays[j]).add(charArrays[j+1]); //just for the record
//                        System.out.println("add "+charArrays[j+1]+" just for the record");
                    }else{
                        Set<Character> characterSet = new HashSet<>();
                        characterSet.add(charArrays[j+1]);
                        afters.put(charArrays[j],characterSet);
//                        System.out.println("vierge "+charArrays[j+1]);
                    }
                }
                else if(j==charArrays.length-1){
//                    System.out.println("Starter "+);
                    //should not be a starter
                    char ending = charArrays[j];
//                    System.out.println("ending: "+charArrays[j]);
//                    System.out.println("Checking ending "+ending+ " in "+starters);
                    while(starters.contains(ending)){
                        ending = generateRandomWord(1,special).charAt(0);
//                        System.out.println("Change ending to "+ending);
//                        System.out.println("Checking new ending "+ending+ " in "+starters);
                    }
//                    System.out.println("New ending: "+ending);
                    charArrays[j]=ending;
//                    System.out.println("After filter1: "+String.valueOf(charArrays));
                    enders.add(ending);
                }
            }
//            ljSBqnBubEAWWkUOVyCUvxi iqN
            //befores
            //ensure no matching leftward <-----xxxx (reading befores, moving from front to back)
            destroyedPattern=false;
//            System.out.println("Filter2:"+String.valueOf(charArrays));
            for (int j = 0 ; j <charArrays.length ; j++) {
                //get current char
                if(j>=1){
                    if(befores.get(charArrays[j])!=null){
                        //ensure that the letters after is not used.
                        if(!destroyedPattern && befores.get(charArrays[j]).contains(charArrays[j-1])){
                            //generate a new character
                            String newC = generateRandomWord(1,special);
                            while (befores.get(charArrays[j]).contains(newC.charAt(0)) || (
                                    ((j-1)==0) && enders.contains(newC.charAt(0))
                            )){
                                newC = generateRandomWord(1,special);
                            }
                            charArrays[j-1]=newC.charAt(0);
                            if((j-1)==0){
                                starters.add(newC.charAt(0));
                            }
                            //we've destroyed the pattern and we are sure.
                            destroyedPattern=true;
                        }
                        befores.get(charArrays[j]).add(charArrays[j-1]);//for the record
                    }else {
                        Set<Character> characterSet = new HashSet<>();
                        characterSet.add(charArrays[j-1]);
                        befores.put(charArrays[j],characterSet);
                    }
                }
                else if(j==0){//j==0
                    //should not be a starter
                    char starting = charArrays[j];
//                    System.out.println("Checking starting "+starting+ " in "+enders);
                    while(enders.contains(starting)){
                        starting = generateRandomWord(1,special).charAt(0);
//                        System.out.println("Change starting to"+starting);
//                        System.out.println("Checking new starting "+starting+ " in "+enders);
                    }
//                    System.out.println("new starting: "+starting);
//                    System.out.println("Before: "+String.valueOf(charArrays));
                    charArrays[j]=starting;
//                    System.out.println("After: "+String.valueOf(charArrays));
                    starters.add(starting);
                }
            }
//            System.out.println("Final word:"+String.valueOf(charArrays));
            boolean oops =false;
            if(String.valueOf(charArrays).length()>=2){
                for (String w :
                        generatedWords) {
//                    System.out.println("Comparing "+w+" and "+String.valueOf(charArrays).substring(0,2));
                    if(w.length()<2){
                        continue;
                    }
                    if(
                            w.startsWith(String.valueOf(charArrays).substring(0,2))
                                    || String.valueOf(charArrays).startsWith(w.substring(0,2))
                                    || String.valueOf(charArrays).endsWith(w.substring(0,2))
                                    || w.endsWith(String.valueOf(charArrays).substring(0,2))
                    ){
                        oops=true;
                        break;
                    }
                    if(w.length()<3||String.valueOf(charArrays).length()<3){
                        continue;
                    }
                    if(w.startsWith(String.valueOf(charArrays).substring(0,3))
                                    || String.valueOf(charArrays).startsWith(w.substring(0,3))
                                    || String.valueOf(charArrays).endsWith(w.substring(0,3))
                                    || w.endsWith(String.valueOf(charArrays).substring(0,3))
                    ){
                        oops=true;
                        break;
                    }
                }
            }
            if(oops){
                continue;
            }

            generatedWords.add(String.valueOf(charArrays));
            i++;
        }




        //let's create the matches;
        ArrayList<String> matchArrayList = new ArrayList<>();
        matchArrayList.addAll(generatedWords);
        int myMatches = 0;
        int counter = 0;
        for (String str :
                generatedWords) {
           boolean ilike = random.nextInt(10000)<5000;
           if(ilike && str.length()>0 && counter<str.length()-1){
               String newMatch = str.substring(random.nextInt(str.length()))+generateRandomWord(random.nextInt(25),special);
               System.out.println(newMatch+" from "+str);
               //now verify for the rest
               matchArrayList.add(newMatch);
           }
           counter ++;
        }

        for(String str2 : matchArrayList){
            for(String str: matchArrayList){
                if(str.equals(str2)){
                    continue;
                }
                int length = Math.min(str2.length(), str.length());
                for(int oo=0;oo<length;oo++){
                    String substr = str.substring(0,oo+1);
                    if(str2.endsWith(substr)){
                        myMatches++;
                        break;
                    }
                }
            }
        }
        System.out.println("My matches expected: "+myMatches);

        ArrayList<Word> arrayList = new ArrayList<>();
        Word[] warray = new Word[matchArrayList.size()];
        int cc=0;

        String writeToFile="";
        PrintWriter out = new PrintWriter("data.generated.txt");

        for (String s :
                matchArrayList) {
            arrayList.add(new Word(s));
            warray[cc]=new Word(s);
            cc++;
            writeToFile=s+" ";
            out.println(writeToFile);
        }
        out.close();
        return myMatches;
     /*  Match match = (Match) Class.forName("Match").newInstance();
//        Word[] warray =
        ArrayList<Word[]> words= match.calculateOn2(warray,warray);

        if(words.size()!=(myMatches)){
            System.err.println("Ooopps... returned: "+words.size());
            match.printMatches(words);
            System.exit(0);
        }else{
            System.out.println("Zero results :)");
        }
*/



//        destroyedPattern
    }
    static void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MatchInterface match = (MatchInterface) Class.forName("Match").newInstance();
        try {
            File myObj = new File("src/test-cases");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line_data = myReader.nextLine().trim();
                String[] parts = line_data.split(" ");
                System.out.println(parts[0] + " | " + parts[1] + " | " + parts[2] + " | ");
                int numberExpected = generateTestClass(parts[0].charAt(1), parts[1].charAt(1), parts[2].charAt(1));
                System.out.println("Testing...");
                System.out.println("O(N):... Passed");
                System.out.println("O(N2)... Passed");
                System.out.println("O(NlogN)... Passed");
                Word[] words = Utils.extract("data.generated.txt");
                ArrayList<Word> wordArrayList = Utils.extractList("data.generated.txt");
                ArrayList<Word[]> words1 =  match.calculateOnlog(wordArrayList,wordArrayList);
                for (Word[] w :
                        words1) {
                    if(w[0].getText().endsWith(w[2].getText()) && w[1].getText().startsWith(w[2].getText())){
                        System.out.println("Ok!");
                    }else{
                        System.out.println("NOOOK!!");
                    }
                }
                ArrayList<Word[]> words1a =  match.calculateOn(words,words);
                ArrayList<Word[]> words2 =  match.calculateOn2(words,words);
                //try to read file and calculate
                System.out.println("O(N): "+words1a.size()+", Verdict: "+((words1a.size()!=numberExpected)?"Failed!":"Passed!"));
                System.out.println("O(N2): "+words2.size()+", Verdict: "+((words2.size()!=numberExpected)?"Failed!":"Passed!"));
                System.out.println("O(NlogN): "+words1.size()+", Verdict: "+((words1.size()!=numberExpected)?"Failed!":"Passed!"));
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static String normal_chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
            +"jklmnopqrstuvwxyz!@#$%&←↑→↓·•●–—―‽‖«»‹›'‘’“”„‚❝❞✅❤️✌️✍️☠️⌘#⌥⌃⇧⇪⌫⌦␡⎋␛⏎↩␣$¢£€¥¬@¶§©®™°℃℉+−×÷=≠±√‰Ω∞≈~¹²³ƒ¼½¾|⁄†‡․‥…➥➽ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽ☚☛☜☝☞☟★☆✔♠♣♥♦♪♫♯♀♂";
    public static int generateTestClass(char c1, char c2, char c3) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        /**
         * les classes de test :
         * - Words generated [4]: small (around 0), normal, large, no word
         * - Range of matches [4] :  (0%, 1-10, 10-100, 100-1000)
         * - Characters [2]: Special characters or not
         */
        int param1;
        int param2;
        boolean param3 = true;
        Random generator = new Random();
        switch (c1) {
            case 'b':param1=0;break;
            case 'c':
                param1 = generator.nextInt();
                break;
            case 'a':
                param1 = generator.nextInt(1000 - 100) + 100;
                break;
            default:
                param1 = generator.nextInt(3);
        }
        switch (c2) {
            case 'd':
                param2 = generator.nextInt(10) + 1;
                break;
            case 'c':
                param2 = generator.nextInt(100 - 10) + 10;
                break;
            case 'b':
                param2 = generator.nextInt(1000 - 100) + 100;
                break;
            default:
                param2 = 0;
        }
        if (c3 == 'b') {
            param3 = false;
        }
//        return generateTestEnvironment(param1, param2, param3);
        return generateTest(param1, param3);
    }
    public static String generateRandomWord(int len,boolean specialchars) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(specialchars?chars.charAt(rnd.nextInt(chars.length())):normal_chars.charAt(rnd.nextInt(normal_chars.length())));
        return sb.toString();
    }
}
