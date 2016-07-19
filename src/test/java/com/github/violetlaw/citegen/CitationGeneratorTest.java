package com.github.violetlaw.citegen;

import java.nio.file.FileSystems;

import java.nio.file.Path;

public class CitationGeneratorTest {
	
	// TODO(nnaze): Implement parameterized tests at this point.

	
  private static Path getInputOutputPath() {
    return getResourcesPath().resolve("inputoutput");
  }

  private static Path getResourcesPath() {
    return getRootPath().resolve("src/test/resources");
  }

  private static Path getRootPath() {
    return FileSystems.getDefault().getPath(".");
  }
}
