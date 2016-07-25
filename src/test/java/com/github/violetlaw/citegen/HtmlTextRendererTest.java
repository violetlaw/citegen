package com.github.violetlaw.citegen;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import com.github.violetlaw.citegen.Text.Style;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.github.violetlaw.citegen.Text.TextBlock.Builder;
import com.google.common.collect.Lists;
import static com.google.common.truth.Truth.assertThat;

public class HtmlTextRendererTest {
  @Test
  public void testTextRender() throws IOException {
    TextBlock.Builder builder = TextBlock.newBuilder();

    TextBlock blockB = TextBlock.newBuilder().setText("b").addStyle(Style.ITALICS).build();

    builder.addAllChildren(Lists.newArrayList(createTextBlock("a"), blockB, createTextBlock("c")));

    TextBlock testTextBlock = builder.build();

    String testOutput = renderTextBlock(testTextBlock);

    assertThat(testOutput).isEqualTo("a<i>b</i>c");
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
