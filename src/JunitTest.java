import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class JunitTest {
    @Test
    public void shouldReturnNothingWhenEmpty() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Match match = (Match) Class.forName("Match").newInstance();
        ArrayList<Word> emptyList = new ArrayList<>();
        Word[] emptywords = new Word[]{};
        assertEquals(0,match.calculateOn2(emptywords,emptywords).size());
        assertEquals(0,match.calculateOnlog(emptyList,emptyList).size());
        assertEquals(0,match.calculateOn(emptywords,emptywords).size());
    }
    @Test
    public void shouldReturnSingleCaseWhen2DataHaveTwoMatches() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Match match = (Match) Class.forName("Match").newInstance();
        ArrayList<Word> emptyList = new ArrayList<>();
        Word[] emptywords = new Word[]{};
        assertEquals(0,match.calculateOn2(emptywords,emptywords).size());
        assertEquals(0,match.calculateOnlog(emptyList,emptyList).size());
        assertEquals(0,match.calculateOn(emptywords,emptywords).size());
    }
}
