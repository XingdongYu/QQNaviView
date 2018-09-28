package com.robog.qqnaviview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.robog.library.QQNaviView;

public class MainActivity extends AppCompatActivity {

    private QQNaviView mBubbleView;
    private QQNaviView mPersonView;
    private QQNaviView mStarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBubbleView = (QQNaviView) findViewById(R.id.qq_view_bubble);
        mPersonView = (QQNaviView) findViewById(R.id.qq_view_person);
        mStarView = (QQNaviView) findViewById(R.id.qq_view_star);
        mBubbleView.setBigIcon(R.drawable.bubble_big);
        mBubbleView.setSmallIcon(R.drawable.bubble_small);
        mBubbleView.check();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPersonView.lookLeft();
            }
        }, 50);
    }

    public void onClick(View view){
        resetIcon();
        switch (view.getId()){
            case R.id.qq_view_bubble:
                mBubbleView.setBigIcon(R.drawable.bubble_big);
                mBubbleView.setSmallIcon(R.drawable.bubble_small);
                mPersonView.lookLeft();
                showToast("bubble");
                break;
            case R.id.qq_view_person:
                mPersonView.setBigIcon(R.drawable.person_big);
                mPersonView.setSmallIcon(R.drawable.person_small);
                mBubbleView.lookRight();
                showToast("person");
                break;
            case R.id.qq_view_star:
                mStarView.setBigIcon(R.drawable.star_big);
                mStarView.setSmallIcon(R.drawable.star_small);
                mBubbleView.lookRight();
                mPersonView.lookRight();
                showToast("star");
                break;
        }
    }

    private void resetIcon() {
        mBubbleView.setBigIcon(R.drawable.pre_bubble_big);
        mBubbleView.setSmallIcon(R.drawable.pre_bubble_small);

        mPersonView.setBigIcon(R.drawable.pre_person_big);
        mPersonView.setSmallIcon(R.drawable.pre_person_small);

        mStarView.setBigIcon(R.drawable.pre_star_big);
        mStarView.setSmallIcon(R.drawable.pre_star_small);
    }

    private void showToast(CharSequence msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
