package com.github.violetlaw.citegen;

import java.io.IOException;
import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.TextOutput.TextContainer;

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

  private void verifyInputOutput(CitationRequest request, TextContainer outputText) {
    // TODO(nnaze): Implement input/output test here.
  }
}
