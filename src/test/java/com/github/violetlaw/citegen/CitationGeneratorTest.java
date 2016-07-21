package com.github.violetlaw.citegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Citation.CitationRequest.Builder;
import com.github.violetlaw.citegen.TextOutput.TextContainer;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Lists;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CitationGeneratorTest {

  // TODO(nnaze): Implement parameterized tests at this point.

  private static Path GOLDEN_TESTS = Paths.get("src/test/resources/goldentests");

  private static Path INPUT = GOLDEN_TESTS.resolve("input");
  private static Path OUTPUT = GOLDEN_TESTS.resolve("output");

  @Parameters
  // TODO(nnaze): Also paramaterize the test name.
  public static Iterable<GoldenTestData> data() throws IOException {
    return getTestCases();
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

	@AutoValue
  public static abstract class GoldenTestData {
    static GoldenTestData create(Path inputPath, Path outputPath) {
      return new AutoValue_CitationGeneratorTest_GoldenTestData(inputPath, outputPath);
    }

    abstract Path inputPath();

    abstract Path outputPath();
    
    CitationRequest input() throws IOException {
    	Parser parser = JsonFormat.parser();
    	CitationRequest.Builder builder = CitationRequest.newBuilder();
    	parser.merge(new FileReader(inputPath().toFile()), builder);
    	return builder.build();
    }
    
    TextContainer output() throws IOException {
    	Parser parser = JsonFormat.parser();
    	TextContainer.Builder builder = TextContainer.newBuilder();
    	parser.merge(new FileReader(outputPath().toFile()), builder);
    	return builder.build();
    }
  }

  private static List<GoldenTestData> getTestCases() throws IOException {
    List<GoldenTestData> testCases = Lists.newLinkedList();
    DirectoryStream<Path> inputPaths = Files.newDirectoryStream(INPUT, "*.json");
    for (Path inputPath : inputPaths) {
      Path outputPath = OUTPUT.resolve(inputPath.getFileName());

      testCases.add(GoldenTestData.create(inputPath, outputPath));
    }

    return testCases;
  }
}
