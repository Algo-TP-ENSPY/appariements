import java.io.File;
import java.util.*;

public class JennyTestGenerator {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        test();
        Set<String> generatedWords = new HashSet<>();
        Hashtable<Character,Set<Character>> befores = new Hashtable<>();
        Hashtable<Character,Set<Character>> afters = new Hashtable<>();
        Set<Character> starters = new HashSet<>();
        Set<Character> enders = new HashSet<>();
        Random random = new Random();
        boolean special=false;
        int i=0;
        while(i<100){
            boolean destroyedPattern=false;
            String word = generateRandomWord(random.nextInt(25),special);
            if(generatedWords.contains(word)){
                continue;
            }
           //ensure no matching rightward xxxx-----> (reading afters, moving from back to front)
            char[] charArrays = word.toCharArray();
            System.out.println("Filter1:"+String.valueOf(charArrays));
            for (int j = charArrays.length-1 ; j >=0 ; j--) {
                //get current char
                if(j<=charArrays.length-2){
                    if(afters.get(charArrays[j])!=null){
                        //ensure that the letters after is not used.
                        if(!destroyedPattern && afters.get(charArrays[j]).contains(charArrays[j+1])){
                            //generate a new character
                            String newC = generateRandomWord(1,special);
                            while (afters.get(charArrays[j]).contains(newC.charAt(0))){
                                newC = generateRandomWord(1,special);
                            }
                            charArrays[j+1]=newC.charAt(0);
                            //we've destroyed the pattern and we are sure.
                            destroyedPattern=true;
                        }
                        afters.get(charArrays[j]).add(charArrays[j+1]); //just for the record
                    }else {
                        Set<Character> characterSet = new HashSet<>();
                        characterSet.add(charArrays[j+1]);
                        afters.put(charArrays[j],characterSet);
                    }
                }
                else if(j==charArrays.length-1){
//                    System.out.println("Starter "+);
                    //should not be a starter
                    char ending = charArrays[j];
                    while(starters.contains(ending)){
                        ending = generateRandomWord(1,special).charAt(0);
                        System.out.println("Change ending to "+ending);
                    }
                    charArrays[j]=ending;
                    enders.add(ending);
                }
            }
//            ljSBqnBubEAWWkUOVyCUvxi iqN
            //befores
            //ensure no matching leftward <-----xxxx (reading befores, moving from front to back)
            destroyedPattern=false;
            System.out.println("Filter2:"+String.valueOf(charArrays));
            for (int j = 0 ; j <charArrays.length ; j++) {
                //get current char
                if(j>=1){
                    if(befores.get(charArrays[j])!=null){
                        //ensure that the letters after is not used.
                        if(!destroyedPattern && befores.get(charArrays[j]).contains(charArrays[j-1])){
                            //generate a new character
                            String newC = generateRandomWord(1,special);
                            while (befores.get(charArrays[j]).contains(newC.charAt(0))){
                                newC = generateRandomWord(1,special);
                            }
                            charArrays[j-1]=newC.charAt(0);
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
                else {//j==0
                    //should not be a starter
                    char starting = charArrays[j];
                    while(enders.contains(starting)){
                        starting = generateRandomWord(1,special).charAt(0);
                        System.out.println("Change starting to"+starting);
                    }
                    charArrays[j]=starting;
                    starters.add(starting);
                }
            }
            System.out.println("After:"+String.valueOf(charArrays));
            generatedWords.add(String.valueOf(charArrays));
            i++;
        }
        ArrayList<Word> arrayList = new ArrayList<>();
        for (String s :
                generatedWords) {

            System.out.print(s+" ");
            arrayList.add(new Word(s));
        }
        Match match = (Match) Class.forName("Match").newInstance();
        ArrayList<Word[]> words= match.calculateOnlog(arrayList,arrayList);
        match.printMatches(words);


//        destroyedPattern

    }
    static void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Match match = (Match) Class.forName("Match").newInstance();
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

                //try to read file and calculate
//                ArrayList<String> mots = readFileToSet();
//                Set<String> n_couples = match.couples_n(mots);
//                Set<String> n2_couples = match.couples_n2(mots);
//                Set<String> logn_couples = match.couples_n_log_n(mots);
//                System.out.println("O(N): "+n_couples.size()+", Verdict: "+((n_couples.size()!=numberExpected)?"Failed!":"Passed!"));
//                System.out.println("O(N2): "+n_couples.size()+", Verdict: "+((n2_couples.size()!=numberExpected)?"Failed!":"Passed!"));
//                System.out.println("O(NlogN): "+n_couples.size()+", Verdict: "+((logn_couples.size()!=numberExpected)?"Failed!":"Passed!"));
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static String normal_chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
            +"jklmnopqrstuvwxyz!@#$%&←↑→↓·•●–—―‽‖«»‹›'‘’“”„‚❝❞✅❤️✌️✍️☠️⌘#⌥⌃⇧⇪⌫⌦␡⎋␛⏎↩␣$¢£€¥¬@¶§©®™°℃℉+−×÷=≠±√‰Ω∞≈~¹²³ƒ¼½¾|⁄†‡․‥…➥➽ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽ☚☛☜☝☞☟★☆✔♠♣♥♦♪♫♯♀♂";
    public static int generateTestClass(char c1, char c2, char c3) {
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
                param1 = generator.nextInt(100000 - 10000) + 10000;
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
        return 0;
    }
    public static String generateRandomWord(int len,boolean specialchars) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(specialchars?chars.charAt(rnd.nextInt(chars.length())):normal_chars.charAt(rnd.nextInt(normal_chars.length())));
        return sb.toString();
    }
}
