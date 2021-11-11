package com.zybooks.lightsout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    private  static final String GAME_STATE = "gameState";
    private final int REQUEST_CODE_COLOR = 0;

    private LightsOutGame mGame;
    private Button[][] mButtons;
    private int mOnColor;
    private int mOffColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnColor = ContextCompat.getColor(this, R.color.colorOn);
        mOffColor = ContextCompat.getColor(this, R.color.colorOff);

        mButtons = new Button[LightsOutGame.NUM_ROWS][LightsOutGame.NUM_COLS];

        GridLayout gridLayout = findViewById(R.id.light_grid);
        int childIndex = 0;
        for(int row = 0; row < LightsOutGame.NUM_ROWS; row++) {
            for(int col = 0; col < LightsOutGame.NUM_COLS; col++ ){
                mButtons[row][col] = (Button) gridLayout.getChildAt(childIndex);
                childIndex++;
            }
        }
        mGame = new LightsOutGame();
        if(savedInstanceState == null){
            startGame();
        }
        else{
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.restoreState(gameState);
            setButtonColors();
        }
    }

    private void startGame() {
        mGame.newGame();
    }

    public void onLightButtonClick(View view){
        //find  the row and the col selected
        boolean buttonFound = false;
        for(int row = 0; row < LightsOutGame.NUM_ROWS && !buttonFound; row++ ){
            for(int col = 0; col < LightsOutGame.NUM_COLS && !buttonFound; col++){
                if(view == mButtons[row][col]){
                    mGame.selectLight(row, col);
                    buttonFound = true;
                }
            }
        }
        setButtonColors();
    }

    private void setButtonColors() {
        //set all button bacground
        for(int row = 0; row < LightsOutGame.NUM_ROWS; row++ ){
            for(int col = 0; col < LightsOutGame.NUM_COLS; col++ ){
                if(mGame.isLightOn(row, col) ){
                    mButtons[row][col].setBackgroundColor(mOnColor);
                }
                else{
                    mButtons[row][col].setBackgroundColor(mOffColor);
                }
            }
        }
    }

//help button
    public void onHelpClicked(View view){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void onChangeColorClicked(View view){
        Intent intent = new Intent(this, ColorActivity.class);
        startActivityForResult(intent, REQUEST_CODE_COLOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_COLOR) {
            int colorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
            mOnColor = ContextCompat.getColor(this, colorId);
            setButtonColors();
        }
    }
    // TODO : ADD METHOD TO RESPOND TO THE NEW GAME BUTTON CLICK
}