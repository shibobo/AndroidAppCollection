package com.shibobo.littletoys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class PolygonesActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private PolygonesVIew mPolygonesVIew;
    private SeekBar seekbar_kill;
    private SeekBar seekbar_life;
    private SeekBar seekbar_assistant;
    private SeekBar seekbar_phy;
    private SeekBar seekbar_magic;
    private SeekBar seekbar_defense;
    private SeekBar seekbar_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polygonesviewlayout);
        mPolygonesVIew= (PolygonesVIew) findViewById(R.id.polygonesview);
        seekbar_kill= (SeekBar) findViewById(R.id.seekbar_kill);
        seekbar_life= (SeekBar) findViewById(R.id.seekbar_life);
        seekbar_assistant= (SeekBar) findViewById(R.id.seekbar_assistant);
        seekbar_phy= (SeekBar) findViewById(R.id.seekbar_phy);
        seekbar_magic= (SeekBar) findViewById(R.id.seekbar_magic);
        seekbar_defense= (SeekBar) findViewById(R.id.seekbar_defense);
        seekbar_money= (SeekBar) findViewById(R.id.seekbar_money);

        seekbar_kill.setOnSeekBarChangeListener(this);
        seekbar_life.setOnSeekBarChangeListener(this);
        seekbar_assistant.setOnSeekBarChangeListener(this);
        seekbar_phy.setOnSeekBarChangeListener(this);
        seekbar_magic.setOnSeekBarChangeListener(this);
        seekbar_defense.setOnSeekBarChangeListener(this);
        seekbar_money.setOnSeekBarChangeListener(this);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        double value=(double) (seekBar.getProgress()/5);//比例值
        switch (seekBar.getId()){
            case R.id.seekbar_kill:
                mPolygonesVIew.setValue1(value);
//                Toast.makeText(this, "value= "+value, Toast.LENGTH_SHORT).show();
                break;
            case R.id.seekbar_life:
                mPolygonesVIew.setValue2(value);
                break;
            case R.id.seekbar_assistant:
                mPolygonesVIew.setValue3(value);
                break;
            case R.id.seekbar_phy:
                mPolygonesVIew.setValue4(value);
                break;
            case R.id.seekbar_magic:
                mPolygonesVIew.setValue5(value);
                break;
            case R.id.seekbar_defense:
                mPolygonesVIew.setValue6(value);
                break;
            case R.id.seekbar_money:
                mPolygonesVIew.setValue7(value);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
