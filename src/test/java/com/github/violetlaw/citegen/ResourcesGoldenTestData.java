package com.github.violetlaw.citegen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.TextOutput.TextContainer;
import com.google.auto.value.AutoValue;
import com.google.common.collect.Lists;
import com.google.protobuf.util.JsonFormat;

public class ResourcesGoldenTestData {

  // TODO(nnaze): Implement parameterized tests at this point.

  private static Path GOLDEN_TESTS = Paths.get("src/test/resources/goldentests");

  private static Path INPUT = GOLDEN_TESTS.resolve("input");
  private static Path OUTPUT = GOLDEN_TESTS.resolve("output");

  public static Iterable<GoldenTestData> getGoldenTestData() {
    try {
      return getGoldenTestDataFromResources();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static Iterable<GoldenTestData> getGoldenTestDataFromResources() throws IOException {
    List<GoldenTestData> testCases = Lists.newLinkedList();
    DirectoryStream<Path> inputPaths = Files.newDirectoryStream(INPUT, "*.json");

    for (Path inputPath : inputPaths) {
      Path outputPath = OUTPUT.resolve(inputPath.getFileName());

      testCases.add(InternalGoldenTestData.create(inputPath, outputPath));
    }

    return testCases;
  }

  @AutoValue
  static abstract class InternalGoldenTestData implements GoldenTestData {
    static InternalGoldenTestData create(Path inputPath, Path outputPath) {
      return new AutoValue_ResourcesGoldenTestData_InternalGoldenTestData(inputPath, outputPath);
    }

    abstract Path inputPath();

    abstract Path outputPath();

    @Override
    public CitationRequest input() {
      JsonFormat.Parser parser = JsonFormat.parser();
      CitationRequest.Builder builder = CitationRequest.newBuilder();
      try {
        parser.merge(new FileReader(inputPath().toFile()), builder);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return builder.build();
    }

    @Override
    public TextContainer output() {
      JsonFormat.Parser parser = JsonFormat.parser();
      TextContainer.Builder builder = TextContainer.newBuilder();
      try {
        parser.merge(new FileReader(outputPath().toFile()), builder);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return builder.build();
    }

    @Override
    public String toString() {
      return String.format("%s input: %s output: %s", super.toString(), inputPath(), outputPath());
    }
  }
}
