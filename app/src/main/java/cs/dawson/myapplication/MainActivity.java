package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    int quizAttempts, tries, correctAns, incorrectAns, currQuestion, percentage;
    ImageButton[] buttons;
    int[] rightWrong;
    int randCorrect;
    int intpScore;

    boolean quizFinished = false;

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
    int[] lastScores;

    private void loadArrays() {
        questionIMG = new int[4];
        questionIMG[0] = R.drawable.stop;
        questionIMG[1] = R.drawable.maxspeed;
        questionIMG[2] = R.drawable.noparking;
        questionIMG[3] = R.drawable.uturn;


        questionString = new int[4];
        questionString[0] = R.string.stop;
        questionString[1] = R.string.maximum;
        questionString[2] = R.string.parking;
        questionString[3] = R.string.uturn;

        invalidIMG = new int[11];
        invalidIMG[0] = R.drawable.construction;
        invalidIMG[1] = R.drawable.dirtahead;
        invalidIMG[2] = R.drawable.lightahead;
        invalidIMG[3] = R.drawable.mutlipleroads;
        invalidIMG[4] = R.drawable.noentry;
        invalidIMG[5] = R.drawable.norightturns;
        invalidIMG[6] = R.drawable.quayend;
        invalidIMG[7] = R.drawable.yiels;
        invalidIMG[8] = R.drawable.nopassing;
        invalidIMG[9] = R.drawable.nowalking;
        invalidIMG[10] = R.drawable.sharedroad;

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

        lastScores = new int[2];
        lastScores[0] = 0;
        lastScores[1] = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currQuestion = 1;
        loadArrays();
        restoreSharedPreferences();

        nextBut = (Button) findViewById(R.id.nextBut);
        question = (TextView) findViewById(R.id.question);
        qOutOf = (TextView) findViewById(R.id.qOutOf);
        qPercentage = (TextView) findViewById(R.id.qPercentage);
        qCorrect = (TextView) findViewById(R.id.qCorrect);
        nextBut = (Button) findViewById(R.id.nextBut);

        int correct = setGrid();// position of correct answer
        insertQuestion(correct);
        drawGrid(correct);
    }

    public void onHintClick(View v){

        Resources res = getResources();
        String searchPhrase = res.getResourceEntryName(questionString[randCorrect]) + " road sign";
        searchWeb(searchPhrase);
    }

    public void onAboutClick(View v){
        Intent clickedAbout = new Intent(this, AboutActivity.class);
        clickedAbout.putExtra("lastscore0", lastScores[0]);
        clickedAbout.putExtra("lastscore1", lastScores[1]);
        startActivity(clickedAbout);
    }

    private int setGrid() {

        grid = new int[4];
        usedImgs = new int[4];

        Random rand = new Random();
        randCorrect = rand.nextInt(4);

        logIt("randCorrect:"+ randCorrect );

        for(int i = 0; i<grid.length; i++){
            if(i == randCorrect){
                //grid[i] = questionIMG[currQuestion-1];
                grid[i] = questionIMG[randCorrect];
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
        insertQuestion(randCorrect);
        return randCorrect;
    }

    private void insertQuestion(int correct){
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(questionString[correct]);

    }

    /**
     * ISSUE: In the else clause, when setting a onClick on the button,
     * It cannot see the i, in which we can't set the button a specific task.
     * @param correct
     */

    private void drawGrid(int correct){

        String outOf = "Question " + currQuestion + " out of 4";
        qOutOf.setText(outOf);// was here

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
                        updateUI(tries,incorrectAns,correctAns,currQuestion);
                    }
                });
        } else {
            resetAllCounters();
            quizFinished = true;

            lastScores[0] = lastScores[1];
            lastScores[1] = intpScore;


            Intent quizEnd = new Intent(this, QuizFinished.class);
            quizEnd.putExtra("score", intpScore);
            startActivity(quizEnd);
            super.finish();
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
         currQuestion++;
         tries++;
         //loser cant even get the quiz question right.
     }
    }

    public void updateUI(int tries, int incorrectAns, int correctAns, int currQuestion) {
        //question 0 out of 4
        //correct/tries correct
        String corTries = correctAns + "/4 correct ; " + incorrectAns + "/4 incorrect";
        qCorrect.setText(corTries);
        //Score percentage
        if (incorrectAns == 0) {
            logIt("Can't divide by 0, fatal exception");
            qPercentage.setText("Your score is 100%");
            intpScore = 100;
        } else {
            double percentageScore =  ((double) correctAns / 4) * 100;
            intpScore = (int) percentageScore;
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
    protected void onPause() {
        super.onPause();
        getSharedPreferences();
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
        editor.putBoolean("quizFinished", quizFinished);
        editor.putInt("lastscores0", lastScores[0]);
        editor.putInt("lastscores1", lastScores[1]);
        editor.commit();
    }
    private void restoreSharedPreferences(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        boolean finished = preferences.getBoolean("quizFinished", quizFinished);
        lastScores[0] = preferences.getInt("lastscores0",lastScores[0]);
        lastScores[1] = preferences.getInt("lastscores1",lastScores[1]);

        if(finished){
            resetAllCounters();
        }
        else {

            quizAttempts = preferences.getInt("quizAttempts", quizAttempts);
            tries = preferences.getInt("tries", tries);
            correctAns = preferences.getInt("correctAns", correctAns);
            incorrectAns = preferences.getInt("incorrectAns", incorrectAns);
            currQuestion = preferences.getInt("currQuestion", currQuestion);
            percentage = preferences.getInt("percentage", percentage);
            randCorrect = preferences.getInt("randCorrect", randCorrect);
        }
    }

    private void resetAllCounters(){
        tries = 0;
        incorrectAns = 0;
        correctAns = 0;
        currQuestion = 1;
        percentage = 0;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
