package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int tries, correctAns, incorrectAns, currQuestion, percentage, rightPos, rightImage;

    int[] usedQuestions = new int[4];

    int[] orderWrongImages = new int[12];
    int usedImagePosition;

    TextView question;
    TextView qOutOf;
    TextView qPercentage;
    TextView qCorrect;

    Button nextBut;


    int[] questionIMG;
    int[] questionCorrect;
    int[] questionString;
    int[] invalidIMG;
    int[] lastScores;
    int[] rightWrong;


    ImageButton[] buttons;

    public static void logIt(String msg) {
        final String TAG = "-------------------DQ: ";
        Log.d(TAG, msg);
    }

    private void logAllCounters(){
     logIt("tries: " + tries+ " correctAns: " + correctAns +" incorrectAns: " + incorrectAns
             +" currQuestion: " + currQuestion +" percentage: " + percentage +" rightPos: " + rightPos + " rightImage: " + rightImage);
    }

    private void loadAll() {
        questionIMG = new int[4];
        questionIMG[0] = R.drawable.stop;
        questionIMG[1] = R.drawable.maxspeed;
        questionIMG[2] = R.drawable.noparking;
        questionIMG[3] = R.drawable.uturn;

        questionCorrect = new int[4];
        questionCorrect[0] = R.drawable.stopcorrect;
        questionCorrect[0] = R.drawable.maxspeedcorrect;
        questionCorrect[0] = R.drawable.noparkingcorrect;
        questionCorrect[0] = R.drawable.uturncorrect;


        questionString = new int[4];
        questionString[0] = R.string.stop;
        questionString[1] = R.string.maximum;
        questionString[2] = R.string.parking;
        questionString[3] = R.string.uturn;

        invalidIMG = new int[12];
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
        invalidIMG[11] = R.drawable.downhill;

        rightWrong = new int[2];
        rightWrong[0] = R.drawable.incorrect;
        rightWrong[1] = R.drawable.correct;

        lastScores = new int[2];
        lastScores[0] = 0;
        lastScores[1] = 0;

        usedQuestions[0] = -1;
        usedQuestions[1] = -1;
        usedQuestions[2] = -1;
        usedQuestions[3] = -1;


        buttons = new ImageButton[4];
        buttons[0] = (ImageButton) findViewById(R.id.img_1);
        buttons[1] = (ImageButton) findViewById(R.id.img_2);
        buttons[2] = (ImageButton) findViewById(R.id.img_3);
        buttons[3] = (ImageButton) findViewById(R.id.img_4);


        question = (TextView) findViewById(R.id.question);
        qOutOf = (TextView) findViewById(R.id.qOutOf);

        qPercentage = (TextView) findViewById(R.id.qPercentage);

        qCorrect = (TextView) findViewById(R.id.qCorrect);

        nextBut = (Button) findViewById(R.id.nextBut);

    }

    private void initializeCounters() {
        usedImagePosition = 0;
        tries = 0;
        correctAns = 0;
        incorrectAns = 0;
        currQuestion = 0; // make sure to always display + 1
        percentage = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAll();
        initializeCounters();
        restoreSharedPreferences();

        logIt("onCreate() currQuestion = " + currQuestion);


        orderWrongImages = orderAvailableWrongImages();
        rightPos = selectAvailableQuestion();
        setupGrid(rightPos);

    }



    private int selectAvailableQuestion(){

        Random rand = new Random();
        int randomVal = rand.nextInt(4);// we want between 0 and 3 since there are only 4 questions

        while(isUsedQuestion(randomVal)){
            randomVal = rand.nextInt(4); // cause infinit loop..
        }


        usedQuestions[currQuestion] = randomVal;
        logIt("New correct value generated: " + randomVal);
        return randomVal;
    }

    private boolean isUsedQuestion(int value){

        logIt("UsedQuestions length: " + usedQuestions.length);

        for (int i = 0; i<usedQuestions.length; i++) {

            logIt("isUsedQuestionLoop i = " + i);

            if(usedQuestions[i] == value){
                return true;
            }
        }
        return false;
    }

    /**
     * generates an array for the order of images to be display as wrong
     * @return
     */
    private int[] orderAvailableWrongImages(){

        int[] currWrongImages = new int[12];

        List<Integer> wrongList = new ArrayList<Integer>();
        for(int i = 0; i< 12; i++){
            wrongList.add(i);
        }
        Collections.shuffle(wrongList);

        for(int j = 0; j<currWrongImages.length; j++){
            currWrongImages[j] = wrongList.get(j);
            logIt("currWrongImages[j] = "+ currWrongImages[j]);
        }
        return currWrongImages;
    }


    /**
     * draws one question
     * @param questionNumber
     */
    private void setupGrid(int questionNumber){

        int[] grid = new int[4];

        Random rand = new Random();
        int putQuestionHere = rand.nextInt(4);


        for(int i = 0; i<grid.length; i++){

            if(i == putQuestionHere){
                grid[i] = questionNumber;
                logIt("1grid[i] = "+ grid[i]);
            }
            else{
                if(usedImagePosition == 0){
                    grid[i] = -1*(usedImagePosition+111);
                    logIt("2grid[i] = "+ grid[i]);
                    usedImagePosition++;
                }else {
                    grid[i] = -1 * usedImagePosition;
                    logIt("3grid[i] = "+ grid[i]);
                    usedImagePosition++;
                }
            }
        }
        drawGrid(grid);
    }

    private void drawGrid(int[] grid){

        for (int i = 0; i<grid.length; i++){
            logIt("Grid["+i+"] contains " + grid[i] );

            if(grid[i]>=0 && grid[i]<50){
                //this is correct question
                int pos = grid[i]; // position of right answer on grid
                rightImage = i;
                buttons[i].setBackgroundResource(questionIMG[pos]);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view.setBackgroundResource(rightWrong[1]);
                        view.setClickable(false);
                        currQuestion++;
                        logIt("currQuestion = " + currQuestion);
                        selectedCorrectImage();
                    }
                });
                question.setText(questionString[pos]);
            }else {
                //wrong images here
                int pos = -1*grid[i];// multiply by -1 to convert back to positive integer
                if(pos > 100 ){
                    pos = pos-111;
                }
                logIt("POS = " + pos);
                buttons[i].setBackgroundResource(invalidIMG[orderWrongImages[pos]]);
                buttons[i].setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        view.setBackgroundResource(rightWrong[0]);
                        view.setClickable(false);
                        selectedWrongImage();
                    }
                });
            }

        }
        qOutOf.setText((currQuestion+1) + " out of 4");

    }

    private void selectedWrongImage() {
        tries++;
        
        if (tries == 2) {
            for(int i = 0; i<buttons.length; i++){
                if(i!=rightImage){
                    buttons[i].setBackgroundResource(rightWrong[0]);
                }
            }

            if(currQuestion < 3) {
                nextBut.setVisibility(View.VISIBLE);
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setClickable(false);
                }
                currQuestion++;
                logIt("currQuestion = " + currQuestion);
                tries = 0;
                incorrectAns++;
                updateText(correctAns, incorrectAns);
            }
            else if(currQuestion == 3){
                currQuestion++;
                logIt("currQuestion = " + currQuestion);
                //handle end of game
                incorrectAns++;
                updatePercentage();
                lastScores[0] = lastScores[1];
                lastScores[1] = percentage;
                Intent quizEnd = new Intent(this, QuizFinished.class);
                quizEnd.putExtra("score", percentage);
                initializeCounters();
                startActivity(quizEnd);
                super.finish();
            }
        }
    }

    private void selectedCorrectImage() {

        //update counters
        //modify text

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setClickable(false);
        }
        if (currQuestion < 4) {
            tries = 0;
            correctAns++;
            nextBut.setVisibility(View.VISIBLE);
            updateText(correctAns,incorrectAns);
        }else{
            //game ended handle here
            correctAns++;
            updatePercentage();
            lastScores[0] = lastScores[1];
            lastScores[1] = percentage;
            Intent quizEnd = new Intent(this, QuizFinished.class);
            quizEnd.putExtra("score", percentage);
            initializeCounters();
            startActivity(quizEnd);
            super.finish();
        }



    }

    private void updatePercentage(){
        int percent = correctAns*25;
        percentage = percent;
        qPercentage.setText("Your current grade is " + percent + "%");
    }

    private void updateText(int correctAns, int incorrectAns){
        qCorrect.setText(correctAns + " correct ; " + incorrectAns + " incorrect");
        updatePercentage();
        logAllCounters();
    }

    public void onNextClick(View v){
        nextBut.setVisibility(View.INVISIBLE);
        rightPos = selectAvailableQuestion();
        setupGrid(rightPos);

    }

    public void onHintClick(View v) {
        Resources res = getResources();
        String searchPhrase = res.getResourceEntryName(questionString[rightPos]) + " road sign"; //fix this need to get right phrase from the current correct image
        searchWeb(searchPhrase);
    }

    public void onAboutClick(View v) {
        Intent clickedAbout = new Intent(this, AboutActivity.class);
        clickedAbout.putExtra("lastscore0", lastScores[0]);
        clickedAbout.putExtra("lastscore1", lastScores[1]);
        startActivity(clickedAbout);
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


    private void getSharedPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("tries", tries);
        editor.putInt("correctAns", correctAns);
        editor.putInt("incorrectAns", incorrectAns);
        editor.putInt("currQuestion", currQuestion);
        editor.putInt("percentage", percentage);
        editor.putInt("lastscores0", lastScores[0]);
        editor.putInt("lastscores1", lastScores[1]);
        editor.commit();
    }

    private void restoreSharedPreferences() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        lastScores[0] = preferences.getInt("lastscores0", lastScores[0]);
        lastScores[1] = preferences.getInt("lastscores1", lastScores[1]);
        currQuestion = preferences.getInt("currQuestion", currQuestion);
        logIt("restoreSharedPreferences() currQuestion = " + currQuestion);


        if (currQuestion>3) {
            initializeCounters();
        } else {
            tries = preferences.getInt("tries", tries);
            correctAns = preferences.getInt("correctAns", correctAns);
            incorrectAns = preferences.getInt("incorrectAns", incorrectAns);
            percentage = preferences.getInt("percentage", percentage);
            updateText(correctAns,incorrectAns);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        question.setText(savedInstanceState.getString("question"));
        qOutOf.setText(savedInstanceState.getString("qOutOf"));
        qCorrect.setText(savedInstanceState.getString("qCorrect"));

        usedQuestions = savedInstanceState.getIntArray("usedQuestions");
        orderWrongImages = savedInstanceState.getIntArray("orderWrongImages");

        questionIMG = savedInstanceState.getIntArray("questionIMG");
        questionString = savedInstanceState.getIntArray("questionString");
        invalidIMG = savedInstanceState.getIntArray("invalidIMG");
        lastScores = savedInstanceState.getIntArray("lastScores");
        rightWrong = savedInstanceState.getIntArray("rightWrong");

        usedImagePosition = savedInstanceState.getInt("usedImagePosition");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {


        outState.putString("question", question.getText().toString());
        outState.putString("qOutOf", qOutOf.getText().toString());
        outState.putString("qCorrect", qCorrect.getText().toString());

        outState.putIntArray("usedQuestions", usedQuestions);
        outState.putIntArray("orderWrongImages", orderWrongImages);

        outState.putIntArray("questionIMG", questionIMG);
        outState.putIntArray("questionString", questionString);
        outState.putIntArray("invalidIMG", invalidIMG);
        outState.putIntArray("lastScores", lastScores);
        outState.putIntArray("rightWrong", rightWrong);

        outState.putInt("usedImagePosition", usedImagePosition);




/*
        Button nextBut;
        ImageButton[] buttons;*/

        super.onSaveInstanceState(outState);
    }
}
