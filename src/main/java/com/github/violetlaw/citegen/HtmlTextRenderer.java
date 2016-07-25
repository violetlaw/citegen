package com.github.violetlaw.citegen;

import java.io.IOException;
import java.io.Writer;

import com.github.violetlaw.citegen.Text.TextBlock;

public class HtmlTextRenderer {

  public void render(TextBlock textBlock, Writer writer) throws IOException {
    writer.write(textBlock.getText());
    for (TextBlock child : textBlock.getChildrenList()) {
      render(child, writer);
    }
  }
}
