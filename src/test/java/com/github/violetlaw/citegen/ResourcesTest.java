import static org.junit.Assert.assertEquals;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * Test that verifies Maven provides files from src/test/resources/
 */
public class ResourcesTest {

  @Test
  public void fetchTestResource() throws IOException {
    URL testUrl = Resources.getResource("test.txt");
    String content = Resources.toString(testUrl, StandardCharsets.UTF_8);
    assertEquals("test", content.trim());
  }
}
