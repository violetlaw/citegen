package com.github.violetlaw.citegen;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Text.TextBlock;
import com.google.common.base.Charsets;

import com.google.common.io.Resources;
import com.google.protobuf.util.JsonFormat;

public class CitationGeneratorOutputTest {

  @Test
  public void testInsideAgencyWalkerHtmlRendering() throws IOException {
    CitationRequest request =
        parseCitationRequestJson(getReader("citationjson/inside_agency_walker.json"));
    CitationGenerator citationGenerator = new CitationGenerator();
    TextBlock output = citationGenerator.handleRequest(request);
    String htmlOutput = renderTextBlockAsHtml(output);
    String expected = "Christopher J. Walker, <i>Inside Agency Statutory Interpretation</i>";
    assertThat(expected).isEqualTo(htmlOutput);
  }

  private static String renderTextBlockAsHtml(TextBlock textBlock) throws IOException {
    StringWriter writer = new StringWriter();
    new HtmlTextRenderer().render(textBlock, writer);
    return writer.toString();
  }

  private static Reader getReader(String resourceName) throws IOException {
    URL url = Resources.getResource(resourceName);
    return Resources.asCharSource(url, Charsets.UTF_8).openBufferedStream();
  }

  private static CitationRequest parseCitationRequestJson(Reader jsonReader) throws IOException {
    JsonFormat.Parser parser = JsonFormat.parser();
    CitationRequest.Builder builder = CitationRequest.newBuilder();
    parser.merge(jsonReader, builder);
    return builder.build();
  }
}
