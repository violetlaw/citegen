package com.github.violetlaw.citegen;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import com.github.violetlaw.citegen.HtmlTextRenderer.Tag;
import com.github.violetlaw.citegen.Text.Style;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import static com.google.common.truth.Truth.assertThat;

@RunWith(Enclosed.class)
public class HtmlTextRendererTest {

  public static class TagWriterTest {
    @Test
    public void testWriteOpeningTagNoAttributes() throws IOException {
      Tag testTag = Tag.create("test", ImmutableMap.<String, String>of());
      assertOpeningTag(testTag, "<test>");
    }

    @Test
    public void testWriteOpeningTagWithMultipleAttributes() throws IOException {
      Tag testTag =
          Tag.create(
              "test", ImmutableMap.<String, String>of("barKey", "barValue", "bazKey", "bazValue"));
      assertOpeningTag(testTag, "<test barKey=\"barValue\" bazKey=\"bazValue\">");
    }

    @Test
    public void testWriteOpeningQuoteEscaping() throws IOException {
      Tag testTag = Tag.create("foo", ImmutableMap.<String, String>of("quote", "abc \"def\" ghi"));
      assertOpeningTag(testTag, "<foo quote=\"abc &quot;def&quot; ghi\">");
    }

    private void assertOpeningTag(Tag tag, String output) throws IOException {
      StringWriter writer = new StringWriter();
      HtmlTextRenderer.writeOpeningTag(tag, writer);
      assertThat(writer.toString()).isEqualTo(output);
    }
  }

  public static class RenderTest {

    @Test
    public void testTextRender() throws IOException {

      TextBlock.Builder builder = TextBlock.newBuilder();

      TextBlock blockB = TextBlock.newBuilder().setText("b").addStyle(Style.ITALICS).build();

      builder.addAllChildren(
          Lists.newArrayList(createTextBlock("a"), blockB, createTextBlock("c")));

      TextBlock testTextBlock = builder.build();

      String testOutput = renderTextBlock(testTextBlock);

      assertThat(testOutput).isEqualTo("a<i>b</i>c");
    }
  }

  private static String renderTextBlock(TextBlock testTextBlock) throws IOException {
    StringWriter writer = new StringWriter();
    HtmlTextRenderer renderer = new HtmlTextRenderer();
    renderer.render(testTextBlock, writer);
    return writer.toString();
  }

  private static TextBlock createTextBlock(String text) {
    TextBlock.Builder builder = TextBlock.newBuilder();
    builder.setText(text);
    return builder.build();
  }
}
