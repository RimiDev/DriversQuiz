package cs.dawson.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizFinished extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finished);

        TextView finalScore = (TextView) findViewById(R.id.final_score);
        finalScore.setText("You\'re final score is "+ getIntent().getIntExtra("score", 0)+ "%");
    }

    public void onPlayAgainClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onQuitClick(View v){
        super.finish();
    }


}
