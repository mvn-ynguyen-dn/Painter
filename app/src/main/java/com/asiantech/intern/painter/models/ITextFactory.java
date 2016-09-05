package com.asiantech.intern.painter.models;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.asiantech.intern.painter.beans.TextObject;

/**
 * Copyright @2016 AsianTech Inc.
 * Created by LyHV on 9/3/2016.
 */
public interface ITextFactory {
    void onDraw(Canvas canvas, TextObject textObject);

    boolean isTouchInTextArea(TextObject textObject, float x, float y);

    void updateCoordinatesText(TextObject textObject, float movementX, float movementY);

    void updateAngle(TextObject textObject, float angle);

    void onRotateText(TextObject textObject, Canvas canvas, Matrix matrix);
}
