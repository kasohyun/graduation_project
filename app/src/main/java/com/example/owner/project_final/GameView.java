package com.example.owner.project_final;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import java.util.ArrayList;


public class GameView extends TextureView implements TextureView.SurfaceTextureListener,
        View.OnTouchListener {
    private Thread mThread;
    volatile private boolean mIsRunnable;
    //volatile로 지정하지않은 변수는 Thread내에서 갱신되지않을경우
    // 변수값이 바뀌지않는다고 생각하여 초기값그대로 참조함 => 최적화처리를 무효로 할수있음
    volatile private float mTouchedX;
    volatile  private float mTouchedY;
    private float mBlockWidth;
    private float mBlockHeight;
    static final int BLOCK_COUNT = 100;
    private int mLife;
    private long mGameStartTime;
    private Handler mHandler ;



    public GameView(final Context context) {
        super(context);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);


        mHandler = new Handler() {
            // UI Thread에서 실행되는 핸들러
            @Override
            public void handleMessage(Message msg){
                Intent intent = new Intent(context, ClearActivity.class);	// 추가
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);	        //
                intent.putExtras(msg.getData());			            //
                context.startActivity(intent);                              //
            }
        };

    }



    public void start(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {

                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);

                while(true){
                    long startTime = System.currentTimeMillis();
                    synchronized (GameView.this){
                        if(!mIsRunnable){
                            break;
                        }
                        Canvas canvas = lockCanvas();

                        if(canvas==null){
                            continue; // 캔버스를 잠글수없다면 다시 while문으로 이동
                        }
                        canvas.drawColor(Color.WHITE);

                        float padLeft = mTouchedX - mPadHalfWidth;
                        float padRight = mTouchedX + mPadHalfWidth;
                        mPad.setLeftRight(padLeft, padRight);
                        mBall.move();
                        float ballTop = mBall.getY() - mBallRadius;
                        float ballLeft = mBall.getX() - mBallRadius;
                        float ballBottom = mBall.getY() + mBallRadius;
                        float ballRight = mBall.getX() + mBallRadius;

                        if(ballLeft<0 && mBall.getSpeedX() < 0 || ballRight >= getWidth() && mBall.getSpeedX() >0){
                            mBall.setSpeedX(-mBall.getSpeedX()); // 가로방향 벽에 부딪히면 가로속도반전
                        }

                        if(ballTop<0 ){
                            mBall.setSpeedY(-mBall.getSpeedY()); // 세로방향벽에 부딪히면 세로속도반전
                        }

                        if(ballBottom > getHeight()){
                            if(mLife > 0){
                                mLife--;
                                mBall.reset();
                            }else{
                                unlockCanvasAndPost(canvas);
                                Message message = Message.obtain();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(ClearActivity.EXTRA_IS_CLEAR, false);
                                bundle.putInt(ClearActivity.EXTRA_BLOCK_COUNT, getBlockCount());
                                bundle.putLong(ClearActivity.EXTRA_TIME, System.currentTimeMillis()-mGameStartTime);
                                message.setData(bundle);
                                mHandler.sendMessage(message);
                                return;

                            }

                        }

                        Block leftBlock = getBlock(ballLeft, mBall.getY());
                        Block topBlock = getBlock(mBall.getX(), ballTop);
                        Block rightBlock = getBlock(ballRight, mBall.getY());
                        Block bottomBlock = getBlock(mBall.getX(), ballBottom);

                        boolean isCollision = false;

                        if(leftBlock != null){
                            mBall.setSpeedX(-mBall.getSpeedX());
                            leftBlock.collision();
                            isCollision = true;
                        }
                        if(topBlock!=null){
                            mBall.setSpeedY(-mBall.getSpeedY());
                            topBlock.collision();
                            isCollision = true;
                        }
                        if(rightBlock!=null){
                            mBall.setSpeedX(-mBall.getSpeedX());
                            rightBlock.collision();
                            isCollision = true;
                        }
                        if(bottomBlock!=null){
                            mBall.setSpeedY(-mBall.getSpeedY());
                            bottomBlock.collision();
                            isCollision = true;
                        }

                        float padTop = mPad.getTop();
                        float ballSpeedY = mBall.getSpeedY();


                        if(ballBottom > padTop && ballBottom - ballSpeedY < padTop && padLeft < ballRight
                                && padRight > ballLeft){
                            if(ballSpeedY < mBlockHeight /3){
                                ballSpeedY *= -1.05F;
                            }else{
                                ballSpeedY = -ballSpeedY;
                            }
                            float ballSpeedX = mBall.getSpeedX() + (mBall.getX() - mTouchedX)/10;
                            if(ballSpeedX> mBlockWidth /5){
                                ballSpeedX = mBlockWidth /5;
                            }
                            mBall.setSpeedY(ballSpeedY);
                            mBall.setSpeedX(ballSpeedX);


                        }

                        // mPad.draw(canvas, paint);

                        for(DrawableItem item : mItemList){
                            item.draw(canvas,paint);
                        }
                        unlockCanvasAndPost(canvas);

                        if(isCollision && getBlockCount() == 0){
                            Message message = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(ClearActivity.EXTRA_IS_CLEAR, true);
                            bundle.putInt(ClearActivity.EXTRA_BLOCK_COUNT,0);
                            bundle.putLong(ClearActivity.EXTRA_TIME, System.currentTimeMillis() - mGameStartTime);
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }

                    }

                    long sleepTime = 16 -System.currentTimeMillis() + startTime;
                    if(sleepTime >0){
                        try{
                            Thread.sleep(sleepTime);
                        }catch (InterruptedException e){

                        }
                    }
                }


            }
        });
        mIsRunnable =true;
        mThread.start();
    }
    public void stop(){
        mIsRunnable = false;
    }



    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        readyObjects(width, height);


        // paint.setStyle(Paint.Style.STROKE);
        // paint.setColor(Color.BLUE);
        //paint.setStrokeWidth(8);

        //canvas.drawRect(0,0,500,300,paint);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        readyObjects(width, height);
    }

    private ArrayList<DrawableItem> mItemList;
    private ArrayList<Block> mBlockList;
    private Pad mPad;
    private float mPadHalfWidth;

    private Ball mBall;
    private float mBallRadius;

    public void readyObjects(int width, int height){
        mBlockWidth = width /10;
        mBlockHeight = height /20;
        mItemList = new ArrayList<DrawableItem>();
        mBlockList = new ArrayList<Block>();


        for(int i=0; i<BLOCK_COUNT; i++){
            float blockTop = i/10 * mBlockHeight ;
            float blockLeft = i %10 *mBlockWidth;
            float blockBottom = blockTop + mBlockHeight ;
            float blockRight = blockLeft +mBlockWidth;
            mBlockList.add(new Block(blockTop, blockLeft,blockBottom,blockRight));

        }
        mItemList.addAll(mBlockList);

        mPad = new Pad(height*0.8F, height*0.85F);
        mItemList.add(mPad);
        mPadHalfWidth = width /10;

        mBallRadius = width < height? width/40 : height/40;
        mBall = new Ball(mBallRadius, width/2, height/2);
        mItemList.add(mBall);
        mLife = 2;
        mGameStartTime = System.currentTimeMillis();
    }

    private int getBlockCount(){
        int count = 0;
        for(Block block : mBlockList){
            if(block.isExist()){
                count++;
            }
        }
        return count;
    }


    private Block getBlock(float x, float y){
        int index =(int) (x/mBlockWidth + (int)(y/mBlockHeight)*10);
        if(0<=index && index<BLOCK_COUNT){
            Block block = (Block) mItemList.get(index);
            if(block.isExist()){
                return block;
            }
        }
        return null;
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // SurfaceTextView 폐기될때 사용되는 메소드
        return true; // false로 설정할경우 프로그래머가 직접 폐기해야함
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchedX = event.getX();
        mTouchedY = event.getY();
        return true;
    }
}
