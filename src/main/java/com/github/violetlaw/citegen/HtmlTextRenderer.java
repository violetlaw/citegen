package com.github.violetlaw.citegen;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import com.github.violetlaw.citegen.Text.Style;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.html.HtmlEscapers;

public class HtmlTextRenderer {

  public void render(TextBlock textBlock, Writer writer) throws IOException {
    List<Style> styles = textBlock.getStyleList();

    // Write opening tags, if any.
    Queue<Tag> openTags = Lists.newLinkedList();
    for (Style style : styles) {
      Tag tag = getTagForStyle(style);
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
      Tag tag = openTags.remove();
      writeClosingTag(tag, writer);
    }
  }

  @VisibleForTesting
  static void writeOpeningTag(Tag tag, Writer writer) throws IOException {
    writer.write("<");
    writer.write(tag.tagName());

    for (Entry<String, String> attribute : tag.attributes().entrySet()) {
      writer.write(" ");

      String keyValue =
          String.format(
              "%s=\"%s\"",
              attribute.getKey(),
              HtmlEscapers.htmlEscaper().escape(attribute.getValue()));

      writer.write(keyValue);
    }

    writer.write(">");
  }

  private static void writeClosingTag(Tag tag, Writer writer) throws IOException {
    writer.write("</" + tag.tagName() + ">");
  }

  @AutoValue
  abstract static class Tag {
    static Tag create(String tagName, ImmutableMap<String, String> attributes) {
      return new AutoValue_HtmlTextRenderer_Tag(tagName, attributes);
    }

    abstract String tagName();

    abstract ImmutableMap<String, String> attributes();
  }

  private Tag getTagForStyle(Style style) {
    switch (style.getNumber()) {
      case Style.ITALICS_VALUE:
        return Tag.create("i", ImmutableMap.of());
    }

    throw new IllegalArgumentException("No valid tag for " + style);
  }
}
