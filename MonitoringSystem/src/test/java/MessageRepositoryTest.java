import junit.framework.TestCase;
import ru.redych.data.MessageRepository;

public class MessageRepositoryTest extends TestCase {
    private MessageRepository messageRepository;

    public void testLoadFromResources() {
        messageRepository = new MessageRepository("message.txt");
        String message = messageRepository.getMessage();
        String expectedMessage = """
                Здравствуйте, дорогая $name

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: $disappeared
                Появились следующие новые страницы: $new
                Изменились следующие страницы: $changed

                С уважением,
                автоматизированная система
                мониторинга.
                """;
        assertEquals(expectedMessage, message);
    }
    public void testLoadFromFile() {
        messageRepository = new MessageRepository("src/test/resources/test_message.txt");
        String message = messageRepository.getMessage();
        String expectedMessage = "He was a boy and she was a girl\n";
        assertEquals(expectedMessage, message);
    }
    public void testGetMessage() {
        messageRepository = new MessageRepository("message.txt");
        String message = messageRepository.getMessage();
        String expectedMessage = """
                Здравствуйте, дорогая $name

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: $disappeared
                Появились следующие новые страницы: $new
                Изменились следующие страницы: $changed

                С уважением,
                автоматизированная система
                мониторинга.
                """;
        assertEquals(expectedMessage, message);
    }
}
