package com.mobile.ctm.snapt.utils;

public interface Communicator {
     void updateTextView(String message);
     void scanErrorToast(String message);
     void updateTextViewSuccess(String message);
     void updateTextViewReset(String message);
}
