import junit.framework.TestCase;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetNewUsecase;

public class GetNewTest extends TestCase {
    public void testAverageNew() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("today.txt");
        GetNewUsecase usecase = new GetNewUsecase(yesterday, today);
        String expected = "gelbooru.com";
        assertEquals(expected, usecase.getNew());
    }
    public void testAllNew() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("src/test/resources/new_table.txt");
        GetNewUsecase usecase = new GetNewUsecase(yesterday, today);
        String expected = "yandex.ru, gorillaz.com, mail.ru";
        assertEquals(expected, usecase.getNew());
    }
    public void testNoneNew() {
        IURLRepository yesterday = new URLRepository("yesterday.txt");
        IURLRepository today = new URLRepository("yesterday.txt");
        GetNewUsecase usecase = new GetNewUsecase(yesterday, today);
        String expected = "";
        assertEquals(expected, usecase.getNew());
    }
}
