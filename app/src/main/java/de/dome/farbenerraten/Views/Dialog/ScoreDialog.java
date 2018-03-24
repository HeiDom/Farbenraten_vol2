package de.dome.farbenerraten.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.dome.farbenerraten.R;

/**
 * Created by Dominik on 10.09.2017.
 */
public class ScoreDialog extends Dialog{

    private ImageView btnRestart;
    private TextView txtViewRight;
    private TextView txtViewWrong;

    /**
     * Creates a ScoreDialog window that uses the default ScoreDialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param context the context in which the dialog should run
     */
    public ScoreDialog(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * Setzt die wichtigsten Attribute
     */
    private void init(){
        setCancelable(false);
        setContentView(R.layout.score_dialog);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //Setzt den Hintergrund Transparent, damit der Button halb aus dem Dialog schaut
        setViews();
    }

    /**
     * Setzt die Views die in dem Layout vorhanden sind
     */
    private void setViews(){
        btnRestart = (ImageView) findViewById(R.id.dialog_restart_button);
        txtViewRight = (TextView) findViewById(R.id.txtView_score_right);
        txtViewWrong = (TextView) findViewById(R.id.txtView_score_wrong);
    }

    /**
     * Set the score to the dialog
     * @param right amount of right answers
     * @param wrong amount of wrong answers
     */
    public void setScore(int right, int wrong){
        txtViewRight.setText(String.valueOf(right));
        txtViewWrong.setText(String.valueOf(wrong));
    }

    /**
     * Setzt den ClickListener für den ScoreDialog-Button
     * @param listener enthält den OnClickListener
     */
    public void setOnClickListener(Button.OnClickListener listener){
        btnRestart.setOnClickListener(listener);
    }
}
