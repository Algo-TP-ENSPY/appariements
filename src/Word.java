import java.util.ArrayList;
import java.util.HashMap;

public class Word implements Comparable<Word>{
    private final String text;
    public Word(String text) {
        this.text=text;
    }

    @Override
    public String toString() {
        return text;
    }
    public int length(){
        return text.length();
    }
    public String getText(){
        return text;
    }
    public String getMatchingStrings(Word word){
        //O(min(word.length(), length())) in the worst case scenario
        //0(1) in the best case scenario when the first characters match
        if(word.getText().equals(this.getText())){
            return null;
        }
        int length = Math.min(word.length(), length());
        for(int i=0;i<length;i++){
            String substr = word.getText().substring(0,i+1);
            if(this.getText().endsWith(substr)){
                return substr;
            }
        }
        return null;
    }
    public HashMap<String,String> binaryMatch(Word[] arr){
        HashMap<String,String> bm = new HashMap<>();
        for (int y = this.length()-1; y >=0 ; y--) {
            Word prefix =  new Word(this.getText().substring(y));
            //O(log n)
            int l = 0, r = arr.length - 1;
            while (l <= r) {
                int m = l + (r - l) / 2;

                int res = arr[m].startsWith(prefix) ? 0 : prefix.compareTo(arr[m]);
                // Check if the prefix is present at mid
                if (res == 0){
                    if(!getText().equals(arr[m].getText())) {
                        //they are not same so we can push
                        //in the case of explorer - rerum, we have matches for r,r and rer,rer . so we need to replace in that case
                        //hashmap contains "t1-t2" as key
                        String key = this.getText()+" "+arr[m].getText();//we know that they are words and can't have a separator " "
                        if(bm.get(key)!=null){
                            bm.replace(key,prefix.toString());
                        }else{
                            bm.put(key,prefix.toString());
                        }
                    }
                    //check around mid <==========|m|==========>
                    //|m==============>
                    if(m!= arr.length-1){
                        for(int i=m+1;i<arr.length;i++){
                            if(arr[i].startsWith(prefix)){
                                if(!getText().equals(arr[i].getText())) {
                                    //they are not same so we can push
                                    String key = this.getText()+" "+arr[i].getText(); //we know that they are words and can't have a separator " "
                                    bm.put(key,prefix.toString());
                                }
                            }else{
                                break; //nobody else will be favorable
                            }
                        }
                    }
                    //<=============m|
                    if(m!=0){
                        for(int i=m-1;i>=0;i--){
                            if(arr[i].startsWith(prefix)){
                                if(!getText().equals(arr[i].getText())) {
                                    //they are not same so we can push
                                    String key = this.getText()+" "+arr[i].getText(); //we know that they are words and can't have a separator " "
                                    bm.put(key,prefix.toString());
                                }
                            }else{
                                break; //nobody else will be favorable
                            }
                        }
                    }
                    break;
                }
                // If x greater, ignore left half
                if (res > 0)
                    l = m + 1;
                    // If x is smaller, ignore right half
                else
                    r = m - 1;
            }

        }
//        System.out.println("Here");
//        System.out.println(binaryMatch);
        return  bm;
    }

//    /**
//     * Does a prefix searches in 0(log n)
//     * @param arr Word[], the list to search from
//     * @param prefix Word, the prefix
//     * @return ArrayList<Word[]> (the word, its couple, the string that got them to match up)
//     */
//    public ArrayList<Word[]> binarySearch(Word[] arr, Word prefix) {
//
////        return binaryMatch;
//    }



    @Override
    public int compareTo(Word o) {
        return Config.CASE_SENSITIVE?this.getText().compareTo(o.getText()):this.getText().compareToIgnoreCase(o.getText());
    }
    public boolean startsWith(Word o){
        return getText().startsWith(o.getText());
    }
}
