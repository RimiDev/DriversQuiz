package cs.dawson.myapplication;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int quizAttempts, tries, correct, incorrect;

    ImageButton img_1;
    ImageButton img_2;
    ImageButton img_3;
    ImageButton img_4;

    TextView question;

    int[] questionIMG;
    int[] questionString;
    int[] grid;
    int[] invalidIMG;


    private void loadArrays(){
        questionIMG = new int[4];
        questionIMG[0] = R.drawable.stop_1;
        questionIMG[1] = R.drawable.maxspeed;
        questionIMG[2] = R.drawable.noparking;
        questionIMG[3] = R.drawable.nouturn;

        questionString = new int[4];
        questionString[0] = R.string.question_0;
        questionString[1] = R.string.question_1;
        questionString[2] = R.string.question_2;
        questionString[3] = R.string.question_3;

        invalidIMG = new int[11];
        invalidIMG[0] = R.drawable.dq_icon;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getImageButtons();
        loadArrays();

        question = (TextView) findViewById(R.id.question);

        //img_1.setVisibility(View.INVISIBLE);
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_1.setBackgroundResource(R.drawable.noparking);

            }
        });

       // img_2.setBackgroundResource(R.drawable.noparking); // sets image for imagebutton


        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        quizAttempts = prefs.getInt("QuizAttempts", 0);
        tries = prefs.getInt("tries", 0);
        correct = prefs.getInt("QuizAttempts", 0);
        incorrect = prefs.getInt("incorrect", 0);

    }

    private void setGrid(){
        grid = new int[4];

        Random rand = new Random();

        int  q = rand.nextInt(3);

        for(int i = 0; i<grid.length; i++){
            if(i != q){

            }

        }

    }


    private void getImageButtons(){
        img_1 = (ImageButton) findViewById(R.id.img_1);
        img_2 = (ImageButton) findViewById(R.id.img_2);
        img_3 = (ImageButton) findViewById(R.id.img_3);
        img_4 = (ImageButton) findViewById(R.id.img_4);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
