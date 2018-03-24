package de.dome.farbenerraten.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import de.dome.farbenerraten.Util.AnimationUtil;
import de.dome.farbenerraten.R;
import de.dome.farbenerraten.Util.ColorUtil;
import de.dome.farbenerraten.Util.RandomUtil;
import de.dome.farbenerraten.Views.Dialog.ScoreDialog;

public class ActivityFarbenErraten extends AppCompatActivity {

    //region - Vars -

    private int gameTimeLeft;
    private int startCountDown;
    private int correctAnswerId;
    private int counterRightAnswer;
    private int counterWrongAnswer;

    private boolean gameStarted;
    private boolean startBtnPressed;

    private ColorUtil colorUtil;
    private ScoreDialog scoreDialog;

    private Button btnColor0;
    private Button btnColor1;
    private Button btnColor2;
    private Button btnColor3;
    private TextView txtViewTimer;
    private TextView txtViewAnswer;

    private CountDownTimer gameCountDownTimer;

    //endregion

    //region - Override -

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("orientation", false);
        outState.putBoolean("gameStarted", gameStarted);
    }

    /**
     * Stellt die Instance wieder her, wenn z. B. der Bildschirm gedreht wurde
     * @param savedInstanceState Anhand des Bundle wird die Instance wiederhergestellt
     */
    private void onRestoreInstance(Bundle savedInstanceState) {
        gameStarted = savedInstanceState != null && savedInstanceState.getBoolean("gameStarted");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestoreInstance(savedInstanceState);
        setContentView(R.layout.activity_farben_erraten);

        init();
        setViews();
    }

    @Override
    protected void onPause(){
        pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        resumGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        resetGame();
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region - Init -

    /**
     * Instanziert die Objekte der Klasse
     */
    private void init(){
        restoreVars();
        colorUtil = new ColorUtil(this);
        scoreDialog = new ScoreDialog(this);
    }

    //endregion

    //region - Views -

    /**
     * Erstellt die Views für die Activity
     */
    private void setViews(){
        btnColor0 = (Button) findViewById(R.id.btnColor0);
        btnColor1 = (Button) findViewById(R.id.btnColor1);
        btnColor2 = (Button) findViewById(R.id.btnColor2);
        btnColor3 = (Button) findViewById(R.id.btnColor3);
        txtViewTimer = (TextView) findViewById(R.id.txt_view_timer);
        txtViewAnswer = (TextView) findViewById(R.id.txt_view_answer);

        btnColor0.setOnClickListener(clickListener);
        btnColor1.setOnClickListener(clickListener);
        btnColor2.setOnClickListener(clickListener);
        btnColor3.setOnClickListener(clickListener);
        txtViewAnswer.setOnClickListener(clickListener);
    }

    /**
     * Setzt die Buttons visible
     */
    private void setButtonsVisible(){
        btnColor0.setVisibility(View.VISIBLE);
        btnColor1.setVisibility(View.VISIBLE);
        btnColor2.setVisibility(View.VISIBLE);
        btnColor3.setVisibility(View.VISIBLE);
    }

    /**
     * Setzt die Buttons invisible
     */
    private void setButtonsInvisible(){
        btnColor0.setVisibility(View.INVISIBLE);
        btnColor1.setVisibility(View.INVISIBLE);
        btnColor2.setVisibility(View.INVISIBLE);
        btnColor3.setVisibility(View.INVISIBLE);
    }

    /**
     * Setzt den 'Game Over' Screen
     */
    private void setGameOverState(){
        txtViewAnswer.setText("");
        txtViewAnswer.setTextColor(Color.BLACK);
        txtViewTimer.setText(getResources().getString(R.string.game_over));
    }

    /**
     * Setzt die Farben für die Buttons
     * @param colors Enhält die Schriftfarben
     * @param colorName Enhält die Farbnamen
     */
    private void setColorsToButtons(int colors[], int colorName[]){
        btnColor0.setTextColor(colorUtil.getColor(colors[0]));
        btnColor1.setTextColor(colorUtil.getColor(colors[1]));
        btnColor2.setTextColor(colorUtil.getColor(colors[2]));
        btnColor3.setTextColor(colorUtil.getColor(colors[3]));
        btnColor0.setText(colorUtil.getColorName(colorName[0]));
        btnColor1.setText(colorUtil.getColorName(colorName[1]));
        btnColor2.setText(colorUtil.getColorName(colorName[2]));
        btnColor3.setText(colorUtil.getColorName(colorName[3]));
    }

    //region - Animation -

    /**
     * Startet eine Animation für den Start count down
     */
    private void startAnimatedCountDown(int countDown) {
        if (countDown == 0) {
            txtViewAnswer.setText("");
            return;
        }

        txtViewAnswer.setText(String.valueOf(countDown));
        txtViewAnswer.startAnimation(AnimationUtil.getAnimation(animationListener));
    }

    //endregion

    //region - Dialog -

    /**
     * Ruft einen ScoreDialog auf
     */
    private void showScoreDialog(){
        scoreDialog.setScore(counterRightAnswer, counterWrongAnswer);
        scoreDialog.setOnClickListener(dialogRestartListener);
        scoreDialog.show();
    }

    //endregion

    //endregion

    //region - Game -

    /**
     * Startet das Spiel
     */
    private void startGame(){
        if (!gameStarted)
            setButtonsVisible();

        gameStarted = true;
        startGameTimer();
    }

    /**
     * Setzt das Spiel zurück und Startet es neu
     */
    private void resetGame(){
        if (!gameStarted)
            return;

        txtViewTimer.setText("");
        gameCountDownTimer.cancel();
        txtViewAnswer.setTextColor(Color.BLACK);

        restoreVars();
        setButtonsInvisible();
        startAnimatedCountDown(3);
    }

    /**
     * Pausiert das Spiel
     */
    private void pauseGame(){
        if (gameStarted)
            gameCountDownTimer.cancel();
    }

    /**
     * Setzt das Spiel dort vort, wo es pausiert wurde
     */
    private void resumGame(){
        if (gameStarted)
            startGameTimer();
    }

    /**
     * Generiert zufällige Zahlen um den Buttons Text und Textfarbe zuzuordnen
     */
    private void generateColors(){
        int randomTextColors[] = new int[4];
        int randomColorNames[] = new int[4];

        //Befüllt die Arrays mit zufälligen Werten, damit nicht immer die Zahl 0 vorhanden ist im Array
        for (int i = 0; i < randomTextColors.length; i++){
            randomTextColors[i] = RandomUtil.getRandom(ColorUtil.count());
            randomColorNames[i] = RandomUtil.getRandom(ColorUtil.count());
        }

        //Generiert zufällige Zahlen, die nur einmal vorkommen
        for (int i = 0; i < randomTextColors.length; i++){
            for (int t = 0; t < randomTextColors.length; t++){
                if(t == i)
                    continue;
                if (randomTextColors[i] == randomTextColors[t]){
                    randomTextColors[i] = RandomUtil.getRandom(ColorUtil.count());
                    t--;
                    i = 0;
                }
            }
        }

        //Generiert Farbennamen, die ungleich der Textfarbe sind
        for (int i = 0; i < randomColorNames.length; i++){
            for (int t = 0; t < randomColorNames.length; t++){
                if(t == i)
                    continue;
                if(randomTextColors[i] == randomColorNames[i])
                    randomColorNames[i] = RandomUtil.getRandom(ColorUtil.count());

                if (randomColorNames[i] == randomColorNames[t]){
                    randomColorNames[i] = RandomUtil.getRandom(ColorUtil.count());

                    t--;
                    i = 0;
                }
            }
        }

        setAnswer(randomTextColors);
        setColorsToButtons(randomTextColors, randomColorNames);
    }

    /**
     * Setzt einen zufälligen Button als richtige und zeigt dem User die Aufgabe
     * @param textColor Enthält die Schriftfarben der Buttons
     */
    private void setAnswer(int textColor[]){
        correctAnswerId = RandomUtil.getRandom();

        int textColorId = textColor[correctAnswerId];
        while (textColorId == textColor[correctAnswerId]){
            textColorId = RandomUtil.getRandom(ColorUtil.count());
        }

        txtViewAnswer.setTextColor(colorUtil.getColor(textColorId));
        txtViewAnswer.setText(colorUtil.getColorName(textColor[correctAnswerId]));
    }

    /**
     * Setzt die wichtigsten Variablen auf ihren default Wert
     */
    private void restoreVars(){
        startCountDown = 3;
        gameTimeLeft = 5000;
        counterRightAnswer = 0;
        counterWrongAnswer = 0;
        gameStarted = false;
    }

    //endregion

    //region - Events -

    /**
     * ClickListener für die Buttons der Activity
     */
    private Button.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.txt_view_answer:{
                    if (startBtnPressed)
                        return;

                    startBtnPressed = true;
                    startAnimatedCountDown(3);
                    break;
                }
                case R.id.btnColor0:{
                    counterRightAnswer = correctAnswerId == 0 ? counterRightAnswer + 1 : counterRightAnswer;
                    counterWrongAnswer = correctAnswerId == 0 ? counterWrongAnswer : counterWrongAnswer + 1;
                    break;
                }
                case R.id.btnColor1:{
                    counterRightAnswer = correctAnswerId == 1 ? counterRightAnswer + 1 : counterRightAnswer;
                    counterWrongAnswer = correctAnswerId == 1 ? counterWrongAnswer : counterWrongAnswer + 1;
                    break;
                }
                case R.id.btnColor2:{
                    counterRightAnswer = correctAnswerId == 2 ? counterRightAnswer + 1 : counterRightAnswer;
                    counterWrongAnswer = correctAnswerId == 2 ? counterWrongAnswer : counterWrongAnswer + 1;
                    break;
                }
                case R.id.btnColor3:{
                    counterRightAnswer = correctAnswerId == 3 ? counterRightAnswer + 1 : counterRightAnswer;
                    counterWrongAnswer = correctAnswerId == 3 ? counterWrongAnswer : counterWrongAnswer + 1;
                    break;
                }
            }

            if (gameStarted)
                generateColors();
        }
    };

    /**
     * AnimationsListener für den StartCountDown
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener(){

        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animation The started animation.
         */
        @Override
        public void onAnimationStart(Animation animation) {
            //Nothing TO-DO
        }

        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which reached its end.
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            startAnimatedCountDown(--startCountDown);

            if(startCountDown == 0){
                generateColors();
                startGame();
            }
        }

        /**
         * <p>Notifies the repetition of the animation.</p>
         *
         * @param animation The animation which was repeated.
         */
        @Override
        public void onAnimationRepeat(Animation animation) {
            //Nothing TO-DO
        }
    };

    /**
     * Erstellt einen OnClickListener für den ScoreDialog
     */
    private Button.OnClickListener dialogRestartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            scoreDialog.dismiss();
            txtViewTimer.setText("");

            restoreVars();
            setButtonsInvisible();
            startAnimatedCountDown(3);
        }
    };

    //endregion

    //region - CountDownTimer -

    /**
     * Startet einen CountDownTimer für das Spiel
     */
    private void startGameTimer(){
        if (gameCountDownTimer != null)
            gameCountDownTimer.cancel();

        gameCountDownTimer = new CountDownTimer(gameTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                gameTimeLeft = (int) millisUntilFinished;
                int countDown = (int) millisUntilFinished / 1000;

                if (countDown <= 60)
                    txtViewTimer.setText(String.valueOf(countDown));
            }

            @Override
            public void onFinish() {
                gameStarted = false;
                setGameOverState();
                showScoreDialog();
            }
        };
        gameCountDownTimer.start();
    }

    //endregion
}
