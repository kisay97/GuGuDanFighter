package com.estsoft.gugudanfighter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Timer timer = new Timer();
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String buttonText = ((Button)v).getText().toString();
            int buttonTextNumber = Integer.parseInt(buttonText);

            if(buttonTextNumber == correctAnswer){
                correctQuizCount++;
                ((TextView)findViewById(R.id.textView6)).setText("" + correctQuizCount + "/" + totalQuizCcount);
            }

            makeQuiz();
        }
    };

    private int totalQuizCcount = 0;        //문제 총 갯수
    private int correctQuizCount = 0;       //맞은 문제 수
    private int lValue = 0;                  //문제의 왼쪽 숫자
    private int rValue = 0;                  //문제의 오른쪽 숫자
    private int correctAnswer = 0;          //문제의 정답
    private int answerButton = 1;           //문제의 정답 버튼

    private int[] filed = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //UI 셋팅(맞춘개수 초기화, 남은시간 초기화, 리스너 추가)
        setUI();

        //문제 생성(l,rValue crrectAnswer 생성, 버튼에 숫자 세팅)
        makeQuiz();

        //타이머 실행
        timer.schedule( new MyTimerTask(), 1000, 1000 );
    }

    @Override
    public void onBackPressed(){

    }

    protected void setUI(){
        ((TextView)findViewById(R.id.textView6)).setText("" + correctQuizCount + "/" + totalQuizCcount);
        ((TextView)findViewById(R.id.timeText)).setText("" + 60);
        initNumberButton();
        setNumberButtonListener();
    }

    protected void setNumberButtonListener(){
        ((Button)findViewById(R.id.button1)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button2)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button3)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button4)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button5)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button6)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button7)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button8)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.button9)).setOnClickListener(buttonClickListener);
    }

    protected void initNumberButton(){
        for (int i=0;i<9;i++){
            filed[i]=0;
        }
        ((Button)findViewById(R.id.button1)).setText("" + 0);
        ((Button)findViewById(R.id.button2)).setText("" + 0);
        ((Button)findViewById(R.id.button3)).setText("" + 0);
        ((Button)findViewById(R.id.button4)).setText("" + 0);
        ((Button)findViewById(R.id.button5)).setText("" + 0);
        ((Button)findViewById(R.id.button6)).setText("" + 0);
        ((Button)findViewById(R.id.button7)).setText("" + 0);
        ((Button)findViewById(R.id.button8)).setText("" + 0);
        ((Button)findViewById(R.id.button9)).setText("" + 0);
    }

    protected void makeQuiz(){
        totalQuizCcount++;
        ((TextView)findViewById(R.id.textView6)).setText("" + correctQuizCount + "/" + totalQuizCcount);

        lValue = (int) (Math.random()*9) + 1;
        ((TextView)findViewById(R.id.textView7)).setText("" + lValue);
        rValue = (int) (Math.random()*9) + 1;
        ((TextView)findViewById(R.id.textView9)).setText("" + rValue);
        correctAnswer = lValue * rValue;

        initNumberButton();

        answerButton = (int) (Math.random()*9);

        filed[answerButton] = correctAnswer;
        Log.d("correctAnswer", ""+correctAnswer);
        Log.d("answerButton", ""+answerButton);

        for (int i = 0; i < 9; i++){
            if(i == answerButton) continue;
            while(true){
                boolean flag = true;
                int val = (int) (Math.random()*81) + 1;

                for(int j = 0; j < 9; j++){
                    Log.d("filed", "filed["+j+"]" + filed[j] + " ?= " + val);
                    if(filed[j] == val){
                        flag = false;
                        Log.d("flag", ""+flag);
                    }
                }

                if(flag){
                    filed[i] = val;
                    break;
                }
            }
        }

        setButton();
    }

    protected void setButton(){
        ((Button)findViewById(R.id.button1)).setText("" + filed[0]);
        ((Button)findViewById(R.id.button2)).setText("" + filed[1]);
        ((Button)findViewById(R.id.button3)).setText("" + filed[2]);
        ((Button)findViewById(R.id.button4)).setText("" + filed[3]);
        ((Button)findViewById(R.id.button5)).setText("" + filed[4]);
        ((Button)findViewById(R.id.button6)).setText("" + filed[5]);
        ((Button)findViewById(R.id.button7)).setText("" + filed[6]);
        ((Button)findViewById(R.id.button8)).setText("" + filed[7]);
        ((Button)findViewById(R.id.button9)).setText("" + filed[8]);
    }

    private class MyTimerTask extends TimerTask {
        private int restTime = 10; //남은 시간

        @Override
        public void run() {
            if( --restTime <= 0 ) {
                timer.cancel();

                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("correctQuizCount", correctQuizCount);
                intent.putExtra("totalQuizCount", totalQuizCcount);
                startActivity(intent);

                finish();
                return;
            }

            // UI 변경
            runOnUiThread( new Runnable(){
                @Override
                public void run() {
                    TextView tv = (TextView)findViewById( R.id.timeText );
                    tv.setText( ""+restTime );
                }
            });
        }
    }
}
