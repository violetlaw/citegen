package com.github.violetlaw.citegen;

import java.util.List;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Citation.Publication;
import com.github.violetlaw.citegen.Text.Style;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.github.violetlaw.citegen.Text.TextBlock.Builder;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CitationGenerator {

  public TextBlock handleRequest(CitationRequest request) {

    // TODO(nnaze): Implement.
    TextBlock.Builder builder = TextBlock.newBuilder();

    TextBlock authorsBlock = createAuthorsTextBlock(request.getAuthorsList());
    if (authorsBlock != null) {
      builder.addChildren(authorsBlock);

      TextBlock commaTextBlock = TextBlock.newBuilder().setText(", ").build();
      builder.addChildren(commaTextBlock);
    }

    String titleString = getTitleString(request);
    if (titleString != null) {
      TextBlock titleBlock =
          TextBlock.newBuilder().setText(titleString).addStyle(Style.ITALICS).build();
      builder.addChildren(titleBlock);
    }

    // Separating space
    builder.addChildren(TextBlock.newBuilder().setText(" ").build());

    TextBlock publicationBlock = getPublicationBlock(request.getPublication());
    builder.addChildren(publicationBlock);

    return builder.build();
  }

  private static TextBlock getPublicationBlock(Publication publication) {
    // TODO(nanaze): Rendering can differ based
    return TextBlock.newBuilder().setText(publication.getTitle()).build();
  }

  @Nullable
  private static String getTitleString(CitationRequest request) {
    if (request.getTitle() == null) {
      return null;
    }

    StringBuilder builder = new StringBuilder();
    builder.append(request.getTitle());

    String volume = request.getVolume();
    if (volume != null) {
      builder.append(String.format(", %s", volume));
    }

    return builder.toString();
  }

  @Nullable
  private static TextBlock createAuthorsTextBlock(List<String> authors) {

    String authorsText = createAuthorsText(authors);
    if (authorsText == null) {
      return null;
    }

    TextBlock.Builder builder = TextBlock.newBuilder();
    builder.setText(createAuthorsText(authors));
    return builder.build();
  }

  @VisibleForTesting
  static String createAuthorsText(List<String> authors) {
    // With one author, we just list the author.
    if (authors.size() == 1) {
      return Iterables.getOnlyElement(authors);
    }

    // For two authors, join with ampersand.
    if (authors.size() == 2) {
      Joiner joiner = Joiner.on(" & ");
      return joiner.join(authors);
    }

    // > 2, use "et al."
    if (authors.size() > 2) {
      String firstAuthor = authors.get(0);
      return firstAuthor + " et al.";
    }

    return null;
  }
}
