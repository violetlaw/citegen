package com.github.violetlaw.citegen;

import java.io.IOException;
import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CitationGeneratorGoldenDataTest {

  @Parameters(name = "{0}")
  public static Iterable<GoldenTestData> data() throws IOException {
    return ResourcesGoldenTestData.getGoldenTestData();
  }

  final private GoldenTestData data;

  public CitationGeneratorGoldenDataTest(GoldenTestData data) {
    this.data = data;
  }

  @Test
  public void testGoldenTestData() throws IOException {
    verifyInputOutput(data.input(), data.output());
  }

  private void verifyInputOutput(CitationRequest request, TextBlock expectedOutput) {

    CitationGenerator citationGenerator = new CitationGenerator();

    TextBlock actualOutput = citationGenerator.handleRequest(request);

    if (!expectedOutput.equals(actualOutput)) {
      throw new AssertionError(generateErrorMessage(expectedOutput, actualOutput));
    }
  }

  private static String generateErrorMessage(TextBlock expectedOutput, TextBlock actualOutput) {
    StringBuilder builder = new StringBuilder();

    builder.append("Expected and actual output protos do not match.\n");
    builder.append("Expected proto:\n" + expectedOutput);
    builder.append("Actual proto:\n" + expectedOutput);

    // Attempt to display in JSON format.
    try {
      String json = JsonFormat.printer().print(actualOutput);
      builder.append("Actual proto in JSON format:\n" + json);
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }

    return builder.toString();
  }
}
