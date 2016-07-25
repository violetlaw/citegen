package com.github.violetlaw.citegen;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Queue;

import com.github.violetlaw.citegen.Text.Style;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.google.common.collect.Lists;

public class HtmlTextRenderer {

  public void render(TextBlock textBlock, Writer writer) throws IOException {
    List<Style> styles = textBlock.getStyleList();

    // Write opening tags, if any.
    Queue<String> openTags = Lists.newLinkedList();
    for (Style style : styles) {
      String tag = getTagForStyle(style);
      writeOpeningTag(tag, writer);

      // Remember the tag we wrote so we can close it later.
      openTags.add(tag);
    }

    writer.write(textBlock.getText());
    for (TextBlock child : textBlock.getChildrenList()) {
      render(child, writer);
    }

    // close the tags we've open
    while (!openTags.isEmpty()) {
      String tag = openTags.remove();
      writeClosingTag(tag, writer);
    }
  }

  private void writeOpeningTag(String tag, Writer writer) throws IOException {
    writer.write("<" + tag + ">");
  }

  private void writeClosingTag(String tag, Writer writer) throws IOException {
    writer.write("</" + tag + ">");
  }

  private String getTagForStyle(Style style) {
    switch (style.getNumber()) {
      case Style.ITALICS_VALUE:
        return "i";
    }

    throw new IllegalArgumentException("No valid tag for " + style);
  }
}
