package com.github.violetlaw.citegen;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.common.collect.Lists;

public class CitationGeneratorTest {

  // TODO(nnaze): Implement parameterized tests at this point.

  private static Path GOLDEN_TESTS = Paths.get("src/test/resources/goldentests");
  private static Path INPUT = GOLDEN_TESTS.resolve("input");
  private static Path OUTPUT = GOLDEN_TESTS.resolve("output");

  @AutoValue
  static abstract class GoldenTestCase {
    static GoldenTestCase create(File input, File output) {
      return new AutoValue_TestCase(input, output);
    }

    abstract File input();

    abstract File output();
  }

  private static List<GoldenTestCase> getTestCases() throws IOException {
    List<GoldenTestCase> testCases = Lists.newLinkedList();
    DirectoryStream<Path> inputPaths = Files.newDirectoryStream(INPUT, "*.json");
    for (Path inputPath : inputPaths) {
      Path outputPath = OUTPUT.resolve(inputPath.getFileName());

      testCases.add(GoldenTestCase.create(inputPath.toFile(), outputPath.toFile()));
    }

    return testCases;
  }
}
