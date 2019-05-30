package com.example.owner.project_final;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block implements DrawableItem {

    private final float mTop;
    private final float mLeft;
    private final float mBottom;
    private final float mRight;
    private int mHard;

    public Block(float top, float left, float bottom, float right){
        mTop =top;
        mLeft =left;
        mBottom = bottom;
        mRight = right;
        mHard =1;
    }

    private boolean mIsCollision = false; // 충돌상태를 기록하는 플래그

    public void collision(){
        mIsCollision = true ;
    }

    private boolean mIsExist = true;

    public boolean isExist(){
        return  mIsExist;
    }

    public void draw(Canvas canvas, Paint paint){
        if(mIsExist){
            // 내구성이 0이상일때에만 색칠부분 그리기
            if(mIsCollision){
                mHard--;
                mIsCollision = false;
                if(mHard <=0){
                    mIsExist = false;
                    return;
                }
            }
            // 블록색깔 나중에 바꾸기

            paint.setColor(0x80312602);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
            // 테두리선 부분 그리기
            paint.setColor(0x4d312602);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4f);
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);

        }
    }
}
