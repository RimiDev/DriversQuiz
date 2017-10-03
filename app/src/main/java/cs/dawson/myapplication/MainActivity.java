package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    int quizAttempts, tries, correctAns, incorrectAns, currQuestion, percentage;
    ImageButton[] buttons;
    int[] rightWrong;
    int randCorrect;

    TextView question;
    TextView qOutOf;
    TextView qPercentage;
    TextView qCorrect;
    Button nextBut;

    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;


    int[] questionIMG;
    int[] questionString;
    int[] grid;
    int[] invalidIMG;
    int[] usedImgs;

    Animation mAnimation;




    private void loadArrays() {
        questionIMG = new int[4];
        questionIMG[0] = R.drawable.stop;
        questionIMG[1] = R.drawable.maxspeed;
        questionIMG[2] = R.drawable.noparking;
        questionIMG[3] = R.drawable.uturn;


        questionString = new int[4];
        questionString[0] = R.string.question_0;
        questionString[1] = R.string.question_1;
        questionString[2] = R.string.question_2;
        questionString[3] = R.string.question_3;

        invalidIMG = new int[11];
        invalidIMG[0] = R.drawable.stop;
        invalidIMG[1] = R.drawable.maxspeed;
        invalidIMG[2] = R.drawable.noparking;
        invalidIMG[3] = R.drawable.uturn;
        //do rest

        buttons = new ImageButton[4];
        buttons[0] = (ImageButton) findViewById(R.id.img_1);
        buttons[1] = (ImageButton) findViewById(R.id.img_2);
        buttons[2] = (ImageButton) findViewById(R.id.img_3);
        buttons[3] = (ImageButton) findViewById(R.id.img_4);

        button1 = (ImageButton) findViewById(R.id.img_1);
        button2 = (ImageButton) findViewById(R.id.img_2);
        button3 = (ImageButton) findViewById(R.id.img_3);
        button4 = (ImageButton) findViewById(R.id.img_4);

        rightWrong = new int[2];
        rightWrong[0] = R.drawable.incorrect;
        rightWrong[1] = R.drawable.correct;

        nextBut = (Button) findViewById(R.id.nextBut);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadArrays();
        int correct = setGrid();
        drawGrid(correct);

        restoreSharedPreferences();

        question = (TextView) findViewById(R.id.question);
        qOutOf = (TextView) findViewById(R.id.qOutOf);
        qPercentage = (TextView) findViewById(R.id.qPercentage);
        qCorrect = (TextView) findViewById(R.id.qCorrect);
        nextBut = (Button) findViewById(R.id.nextBut);



//        Animation mAnimation = new AlphaAnimation(1, 0);
//        mAnimation.setDuration(200);
//        mAnimation.setInterpolator(new LinearInterpolator());
//        mAnimation.setRepeatCount(Animation.INFINITE);
//        mAnimation.setRepeatMode(Animation.REVERSE);


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
        correctAns = prefs.getInt("QuizAttempts", 0);
        incorrectAns = prefs.getInt("incorrect", 0);

    }

    public void onAboutClick(View v){
        Intent clickedAbout = new Intent(this, AboutActivity.class);
        startActivity(clickedAbout);
    }

    private int setGrid() {


        grid = new int[4];
        usedImgs = new int[4];

        Random rand = new Random();
        randCorrect = rand.nextInt(4);

        //question.setText(getResources().getString(questionString[q]));

        int q = rand.nextInt(3);

        logIt("q:"+ randCorrect );


        for(int i = 0; i<grid.length; i++){
            if(i == randCorrect){

                grid[i] = questionIMG[currQuestion];
            } else {
                    for (int j=0; j<usedImgs.length;j++){
                        int r = rand.nextInt(3);
                        if (r != usedImgs[j]){
                            grid[i] = invalidIMG[r];
                            usedImgs[i] = r;
                        } else {
                            continue;
                        }
                    }
            }


        }

        return randCorrect;


    }


    /**
     * ISSUE: In the else clause, when setting a onClick on the button,
     * It cannot see the i, in which we can't set the button a specific task.
     * @param correct
     */

    private void drawGrid(int correct){


        for (int i=0; i<buttons.length;i++){
            if (i == correct){
                logIt(buttons[correct].toString());
                buttons[correct].setBackgroundResource(grid[correct]);
                buttons[correct].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setBackgroundResource(rightWrong[1]);
                        view.setClickable(false);
                        correctImage();

                    }
                });
            } else {
                buttons[i].setBackgroundResource(grid[i]);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setBackgroundResource(rightWrong[0]);
                        view.setClickable(false);
                        wrongImage();

                    }
                });
            }

        }

    }


    public void correctImage() {
        nextBut.setVisibility(View.VISIBLE);
        if (currQuestion < 4) { // end quiz if more.
            tries = 0;
            currQuestion++;
            correctAns++;
            updateUI(tries,incorrectAns,correctAns,currQuestion);
            for (int i=0;i<buttons.length;i++){
                buttons[i].setClickable(false);
            }
            nextBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextBut.setVisibility(View.INVISIBLE);
                    //Reshuffle the grid to question 2
                    //Add other random images.
                    int correct = setGrid();
                    drawGrid(correct);
                }
            });
        } else {
            //window pop up CONGRATZ U WIN, winner winner chicken dinner.
        }

    }

    public void wrongImage() {
     if (tries == 1) {
         tries = 0;
         incorrectAns++;
         updateUI(tries,incorrectAns,correctAns,currQuestion);
         for (int i=0;i<buttons.length;i++){
             buttons[i].setClickable(false);
         }
         nextBut.setVisibility(View.VISIBLE);
//         switch (randCorrect){
//             case 0: button1.setAnimation(mAnimation);
//                 break;
//             case 1: button2.setAnimation(mAnimation);
//                 break;
//             case 2: button3.setAnimation(mAnimation);
//                 break;
//             case 3: button4.setAnimation(mAnimation);
//                 break;
//         }
        // buttons[randCorrect].startAnimation(mAnimation);
         //buttons[randCorrect].setBackground(getDrawable(R.drawable.imagebutton_green_border));
         nextBut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 buttons[randCorrect].clearAnimation();
                 nextBut.setVisibility(View.INVISIBLE);
                 //Reshuffle the grid to question 2
                 //Add other random images.
                 int correct = setGrid();
                 drawGrid(correct);
             }
         });

     } else {
         logIt("rand" + randCorrect);
         logIt(buttons[randCorrect] + "");
         logIt(buttons[randCorrect].toString());
         tries++;
         //loser cant even get the quiz question right.
     }
    }

    public void updateUI(int tries, int incorrectAns, int correctAns, int currQuestion) {
        //question 0 out of 4
        String outOf = "Question " + currQuestion + " out of 4";
        qOutOf.setText(outOf);
        //correct/tries correct
        String corTries = correctAns + "/" + incorrectAns + " correct";
        qCorrect.setText(corTries);
        //Score percentage
        if (incorrectAns == 0) {
            logIt("Can't divide by 0, fatal exception");
            qPercentage.setText("Your score is 100%");
        } else {
            double percentageScore =  ((double) correctAns / incorrectAns) * 100;
            int intpScore = (int) percentageScore;
            if (intpScore > 100){
                String pScore100 = "Your score is 100%";
            } else {
                String pScore = "Your score is " + intpScore + "%";
                qPercentage.setText(pScore);
            }
        }
    }

    public static void logIt(String msg) {
        final String TAG = "-------------------DQ: ";
        Log.d(TAG, msg);
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
        getSharedPreferences();


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


    private void getSharedPreferences(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("quizAttempts",quizAttempts);
        editor.putInt("tries",tries);
        editor.putInt("correctAns",correctAns);
        editor.putInt("incorrectAns",incorrectAns);
        editor.putInt("currQuestion", currQuestion);
        editor.putInt("percentage", percentage);
        editor.putInt("randCorrect", randCorrect);
        editor.apply(); // Android suggests to use apply, writes data faster
    }
    private void restoreSharedPreferences(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        quizAttempts = preferences.getInt("quizAttempts",quizAttempts);
        tries = preferences.getInt("tries", tries);
        correctAns = preferences.getInt("correctAns",correctAns);
        incorrectAns = preferences.getInt("incorrectAns", incorrectAns);
        currQuestion = preferences.getInt("currQuestion", currQuestion);
        percentage = preferences.getInt("percentage", percentage);
        randCorrect = preferences.getInt("randCorrect", randCorrect);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
