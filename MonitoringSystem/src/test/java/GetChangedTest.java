import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetChangedUsecase;

public class GetChangedTest extends TestCase {
    public void testAverageChanges() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("today.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "4chan.org";
        assertEquals(expected, usecase.getChanged());
    }
    public void testAllChanges() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/changed_table.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "4chan.org, google.com, example.com";
        assertEquals(expected, usecase.getChanged());
    }
    public void testNoneChanges() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("yesterday.txt");
        GetChangedUsecase usecase = new GetChangedUsecase(yesterday, today);
        String expected = "";
        assertEquals(expected, usecase.getChanged());
    }
}
