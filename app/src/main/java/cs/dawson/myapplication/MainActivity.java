package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int quizAttempts, tries, correct, incorrect, currQuestion;


    ImageButton[] buttons;

    TextView question;

    int[] questionIMG;
    int[] questionString;
    int[] grid;
    int[] invalidIMG;


    private void loadArrays() {
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
        //do rest

        buttons = new ImageButton[4];
        buttons[0] = (ImageButton) findViewById(R.id.img_1);
        buttons[1] = (ImageButton) findViewById(R.id.img_2);
        buttons[2] = (ImageButton) findViewById(R.id.img_3);
        buttons[3] = (ImageButton) findViewById(R.id.img_4);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadArrays();
        int correct = setGrid();
        drawGrid(correct);

        question = (TextView) findViewById(R.id.question);

        Button hintButton = (Button) findViewById(R.id.hint_button);
        hintButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Resources res = getResources();
                String searchPhrase = res.getString(R.string.question_0); // hard coded question for test
                searchWeb(searchPhrase);
            }
        });

        //img_1.setVisibility(View.INVISIBLE);


        // img_2.setBackgroundResource(R.drawable.noparking); // sets image for imagebutton

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        quizAttempts = prefs.getInt("QuizAttempts", 0);
        tries = prefs.getInt("tries", 0);
        correct = prefs.getInt("QuizAttempts", 0);
        incorrect = prefs.getInt("incorrect", 0);

    }

    public void onAboutClick(View v){
        Intent clickedAbout = new Intent(this, AboutActivity.class);
        startActivity(clickedAbout);
    }

    private int setGrid() {


        grid = new int[4];

        Random rand = new Random();

        int q = rand.nextInt(3);


        for (int i = 0; i < grid.length - 1; i++) {
            if (i == q) {
                grid[i] = questionIMG[currQuestion];
                //set right action for click

                continue;
            } else {
                //genereate another random
                //verify random hasnt been used
                //add that random to a list so we dont duplicate it
                //add at grid[i]
                //set wrong action for click
            }


        }

        return q;


    }

    private void drawGrid(final int correct) {

        buttons[correct].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttons[correct].setBackgroundResource(R.drawable.noparking);

            }
        });


        buttons[0].setBackgroundResource(grid[0]);
        buttons[1].setBackgroundResource(grid[1]);
        buttons[2].setBackgroundResource(grid[2]);
        buttons[3].setBackgroundResource(grid[3]);
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

    public void searchWeb(String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }




}
