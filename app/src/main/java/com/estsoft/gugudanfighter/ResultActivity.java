package com.estsoft.gugudanfighter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        //맞춘개수 텍스트 설정
        setQuitCount(intent.getIntExtra("correctQuizCount", 0), intent.getIntExtra("totalQuizCount", 0));

        //버튼 리스너 설정
        findViewById(R.id.restartNoButton).setOnClickListener(this);

        findViewById(R.id.restartYesButton).setOnClickListener(this);
    }

    //맞춘개수 텍스트 설정해주는 함수
    private void setQuitCount(int correct, int total){
        ((TextView)findViewById(R.id.resultLabel)).setText("맞춘개수 " + correct + "/" + total);
    }

    //버튼 클릭
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.restartNoButton){
            finish();
            return;
        } else if (v.getId() == R.id.restartYesButton){
            startActivity(new Intent(ResultActivity.this, GameActivity.class));
            finish();
            return;
        }
    }
}
