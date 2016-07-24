package com.github.violetlaw.citegen;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Text.TextBlock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CitationGeneratorTest {

  @Parameters
  public static Iterable<GoldenTestData> data() throws IOException {
    return ResourcesGoldenTestData.getGoldenTestData();
  }

  final private GoldenTestData data;

  public CitationGeneratorTest(GoldenTestData data) {
    this.data = data;
  }

  @Test
  public void testGoldenTestData() throws IOException {
    verifyInputOutput(data.input(), data.output());
  }

  private void verifyInputOutput(CitationRequest request, TextBlock outputText) {
    CitationGenerator citationGenerator = new CitationGenerator();

    TextBlock output = citationGenerator.handleRequest(request);

    assertThat(output).isInstanceOf(TextBlock.class);

    // TODO(nnaze): Implement input/output test here.
  }
}
