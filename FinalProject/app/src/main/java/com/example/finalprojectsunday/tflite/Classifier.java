package com.example.finalprojectsunday.tflite;

import android.graphics.Bitmap;

public interface Classifier {
  float[][][][] recognizeImage(Bitmap bitmap);

  void enableStatLogging(final boolean debug);

  String getStatString();

  void close();

  void setNumThreads(int num_threads);

  void setUseNNAPI(boolean isChecked);
}
