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
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

public class CitationGeneratorTest {

  // TODO(nnaze): Implement parameterized tests at this point.

  private static Path GOLDEN_TESTS = Paths.get("src/test/resources/goldentests");

  private static Path INPUT = GOLDEN_TESTS.resolve("input");
  private static Path OUTPUT = GOLDEN_TESTS.resolve("output");

 @Parameters
  public static Iterable<GoldenTestData> data() throws IOException {
    return getTestCases();
  }

  @AutoValue
  public static abstract class GoldenTestData {
    static GoldenTestData create(Path inputPath, Path outputPath) {
      return new AutoValue_CitationGeneratorTest_GoldenTestData(inputPath, outputPath);
    }

    abstract Path inputPath();

    abstract Path outputPath();
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
