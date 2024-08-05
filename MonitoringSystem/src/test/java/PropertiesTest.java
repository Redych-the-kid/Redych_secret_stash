import junit.framework.TestCase;
import ru.redych.data.PropertiesRepository;

public class PropertiesTest extends TestCase {
    public void testLoad() {
        PropertiesRepository repository = new PropertiesRepository();
        assertEquals("Петрова Т.И", repository.getPropertyByName("name"));
    }
    public void testGetProperty() {
        PropertiesRepository repository = new PropertiesRepository();
        assertEquals("Петрова Т.И", repository.getPropertyByName("name"));
    }
}
