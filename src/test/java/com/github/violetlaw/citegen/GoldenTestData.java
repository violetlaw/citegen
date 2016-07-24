package com.github.violetlaw.citegen;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.TextOutput.TextContainer;

public interface GoldenTestData {

  CitationRequest input();

  TextContainer output();
}
