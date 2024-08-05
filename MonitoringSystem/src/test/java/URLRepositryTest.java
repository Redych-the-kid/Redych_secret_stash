import junit.framework.TestCase;
import ru.redych.data.URLRepository;

import java.util.HashSet;
import java.util.Set;

public class URLRepositryTest extends TestCase {
    public void testLoadFromResources() {
        URLRepository repository = new URLRepository("yesterday.txt");
        Set<String> set = new HashSet<>();
        set.add("google.com");
        set.add("4chan.org");
        set.add("example.com");

        assertEquals(set, repository.getSet());

        Integer ok = 200;
        Integer error = 500;
        assertEquals(ok, repository.getCode("google.com"));
        assertEquals(error, repository.getCode("4chan.org"));
        assertEquals(ok, repository.getCode("example.com"));
    }
    public void testLoadFromFile() {
        URLRepository repository = new URLRepository("src/test/resources/test_table.txt");
        Set<String> set = new HashSet<>();
        set.add("google.com");
        set.add("4chan.org");

        assertEquals(set, repository.getSet());

        Integer ok = 200;
        assertEquals(ok, repository.getCode("google.com"));
        assertEquals(ok, repository.getCode("4chan.org"));
    }

    public void testGetCode() {
        URLRepository repository = new URLRepository("yesterday.txt");
        Integer ok = 200;
        Integer error = 500;
        assertEquals(ok, repository.getCode("google.com"));
        assertEquals(error, repository.getCode("4chan.org"));
        assertEquals(ok, repository.getCode("example.com"));
    }

    public void testGetSet() {
        URLRepository repository = new URLRepository("yesterday.txt");
        Set<String> set = new HashSet<>();
        set.add("google.com");
        set.add("4chan.org");
        set.add("example.com");

        assertEquals(set, repository.getSet());
    }
}
