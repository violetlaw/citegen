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
  public void testMarbury() throws IOException {
    assertJsonCitationHtmlOutput(
        getReader("citationjson/beyond_marbury_sunstein.json"),
        "Cass R. Sunstein, "
            + "<i>Beyond Marbury: The Executive's Power to Say What the Law Is, 115</i> "
            + "Yale L. J.");
  }

  @Test
  public void testInsideAgencyWalkerHtmlRendering() throws IOException {
    assertJsonCitationHtmlOutput(
        getReader("citationjson/inside_agency_walker.json"),
        "Christopher J. Walker, <i>Inside Agency Statutory Interpretation, 67</i> "
            + "Stan. L. Rev.");
  }

  @Test
  public void testTheContinuumEskridge() throws IOException {
    assertJsonCitationHtmlOutput(
        getReader("citationjson/the_continuum_eskridge_and_baer.json"),
        "William N. Eskridge, Jr. & Lauren E. Baer, <i>The Continuum of "
            + "Deference: Supreme Court Treatment of Agency Statutory "
            + "Interpretations from Chevron to Hamdan, 96</i> "
            + "Geo. L. J.");
  }

  private static void assertJsonCitationHtmlOutput(Reader jsonReader, String expectedHtml)
      throws IOException {
    CitationRequest request = parseCitationRequestJson(jsonReader);
    CitationGenerator citationGenerator = new CitationGenerator();
    TextBlock output = citationGenerator.handleRequest(request);
    String htmlOutput = renderTextBlockAsHtml(output);
    assertThat(htmlOutput).isEqualTo(expectedHtml);
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
