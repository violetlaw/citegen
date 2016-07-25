package com.github.violetlaw.citegen;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import com.google.common.collect.ImmutableList;

public class CitationGeneratorTest {

  @Test
  public void zeroAuthorsGivesNull() {
    assertThat(CitationGenerator.createAuthorsText(ImmutableList.<String>of())).isNull();
  }

  @Test
  public void oneAuthor() {
    assertThat(CitationGenerator.createAuthorsText(ImmutableList.of("Alexander Hamilton")))
        .isEqualTo("Alexander Hamilton");
  }

  @Test
  public void twoAuthors() {
    assertThat(
            CitationGenerator.createAuthorsText(
                ImmutableList.of("Alexander Hamilton", "James Madison")))
        .isEqualTo("Alexander Hamilton & James Madison");
  }

  @Test
  public void moreThanTwoAuthors() {
    assertThat(
            CitationGenerator.createAuthorsText(
                ImmutableList.of("Alexander Hamilton", "James Madison", "John Jay")))
        .isEqualTo("Alexander Hamilton et al.");
  }
}
