package com.github.violetlaw.citegen;

import com.github.violetlaw.citegen.Citation.CitationRequest;
import com.github.violetlaw.citegen.Text.TextBlock;

public interface GoldenTestData {

  CitationRequest input();

  TextBlock output();
}
