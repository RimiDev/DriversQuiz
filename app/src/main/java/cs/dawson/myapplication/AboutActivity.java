/**
 * Class that handles the AboutActivity
 *
 * @author Nicolas Mazzone & Maxime Lacasse
 */

package cs.dawson.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView savedScores = (TextView) findViewById(R.id.savedScores);

        int score0 = getIntent().getIntExtra("lastscore0", 0);
        int score1 = getIntent().getIntExtra("lastscore1", 0);

        savedScores.setText("Last quiz scores: " + score0 + "%, " + score1 + "%");
    }
}
