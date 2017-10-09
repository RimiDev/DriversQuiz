/**
 * Class that handles the QuizFinished Activity
 *
 * @author Nicolas Mazzone & Maxime Lacasse
 */

package cs.dawson.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizFinished extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finished);

        TextView finalScore = (TextView) findViewById(R.id.final_score);
        finalScore.setText("You\'re final score is "+ getIntent().getIntExtra("score", 0)+ "%");
    }

    /**
     * Starts a new quiz
     * @param v
     */
    public void onPlayAgainClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * if user clicks quit, the app is closed.
     * @param v
     */
    public void onQuitClick(View v){
        super.finish();
    }


}
