package com.example.finalprojectsunday.customview;
import com.example.finalprojectsunday.tflite.Detector.Recognition;
import java.util.List;


public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
