package com.example.trk.risk;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    static int freq[];
    static boolean isRed = true;
    static int min = 1;
    static int max = 6;
    static int numberOfAttackingSoldiers = 15;
    static int numberOfDefendingSoldiers = 15;

    static boolean disabledAttackerDice[] = new boolean[4];
    static boolean disabledDefenderDice[] = new boolean[3];
    static boolean over = false;
    static int winner = 0;
    int highestAttackingDice = 0;
    int secondHighestAttackingDice = 0;

    int highestDefendingDice = 0;
    int secondHighestDefendingDice = 0;

    TextView remainingAttackers;
    TextView remainingDefenders;

    static Random rand;
    MediaPlayer roll_sound;
    static int counter = 0;

  //  Integer.parseInt(myEditText.getText().toString())).

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        if(counter > 0)
            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
        counter++;
    }

    public void startTheGameAlready(View view)
    {
        EditText attackerInfoBox = (EditText) findViewById(R.id.initialAttackerNumber);
        EditText defenderInfoBox = (EditText) findViewById(R.id.initialDefenderNumber);
        try {
            numberOfAttackingSoldiers = Integer.parseInt(attackerInfoBox.getText().toString());
            numberOfDefendingSoldiers = Integer.parseInt(defenderInfoBox.getText().toString());
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Please enter a valid initial soldier number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(numberOfAttackingSoldiers == 0 || numberOfDefendingSoldiers == 0)
        {
            Toast.makeText(this, "Please enter a valid initial soldier number", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressBar attackingProgress = (ProgressBar) findViewById(R.id.attackingProgress);
        ProgressBar defendingProgress = (ProgressBar) findViewById(R.id.defendingProgress);

        int maxSoldier = numberOfAttackingSoldiers > numberOfDefendingSoldiers ? numberOfAttackingSoldiers : numberOfDefendingSoldiers;

        attackingProgress.setMax(maxSoldier);
        defendingProgress.setMax(maxSoldier);

        attackingProgress.setProgress(numberOfAttackingSoldiers);
        defendingProgress.setProgress(numberOfDefendingSoldiers);

        remainingAttackers = (TextView) findViewById(R.id.numberOfAttackingSoldiers);
        remainingDefenders = (TextView) findViewById(R.id.numberOfDefendingSoldiers);

        remainingAttackers.setText("" + numberOfAttackingSoldiers);
        remainingDefenders.setText("" + numberOfDefendingSoldiers);

        String toastMessage = "";
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(toastMessage);

        for(int c = 0; c < disabledAttackerDice.length; c++)
            disabledAttackerDice[c] = false;
        for(int c = 0; c < disabledDefenderDice.length; c++)
            disabledDefenderDice[c] = false;


        if(numberOfAttackingSoldiers <= 0)
        {
            disabledAttackerDice[1] = true;
            disabledAttackerDice[2] = true;
            disabledAttackerDice[3] = true;
        }

        if(numberOfAttackingSoldiers == 1)
        {
            disabledAttackerDice[2] = true;
            disabledAttackerDice[3] = true;
        }

        if(numberOfAttackingSoldiers == 2)
        {
            disabledAttackerDice[3] = true;
        }

        if(numberOfDefendingSoldiers <= 0)
        {
            disabledDefenderDice[1] = true;
            disabledDefenderDice[2] = true;
        }

        if(numberOfDefendingSoldiers == 1)
        {
            disabledDefenderDice[2] = true;
        }

        ImageButton dicePicture;
        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_1);
        if(disabledAttackerDice[1] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_2);
        if(disabledAttackerDice[2] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
        if(disabledAttackerDice[3] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


        dicePicture = (ImageButton) findViewById(R.id.defender_dice_1);
        if(disabledDefenderDice[1] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


        dicePicture = (ImageButton) findViewById(R.id.defender_dice_2);
        if(disabledDefenderDice[2] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton dicePicture = (ImageButton) findViewById(R.id.attacker_dice_1);
        String diceImageNumber = "red_die_1";

        remainingAttackers = (TextView) findViewById(R.id.numberOfAttackingSoldiers);
        remainingDefenders = (TextView) findViewById(R.id.numberOfDefendingSoldiers);

        remainingAttackers.setText("" + numberOfAttackingSoldiers);
        remainingDefenders.setText("" + numberOfDefendingSoldiers);

        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));
        rand = new Random();
        freq = new int[7];

        roll_sound =  MediaPlayer.create(getApplicationContext(), R.raw.roll_dice_sound);

        for(int c = 0; c < disabledAttackerDice.length; c++)
            disabledAttackerDice[c] = true;
        for(int c = 0; c < disabledDefenderDice.length; c++)
            disabledDefenderDice[c] = true;

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_1);
        if(disabledAttackerDice[1] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_2);
        if(disabledAttackerDice[2] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
        if(disabledAttackerDice[3] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


        dicePicture = (ImageButton) findViewById(R.id.defender_dice_1);
        if(disabledDefenderDice[1] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


        dicePicture = (ImageButton) findViewById(R.id.defender_dice_2);
        if(disabledDefenderDice[2] == false)
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(true);
        }
        else
        {
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }


    }

    public void tap_dice(View view)
    {
        over = false;

        if(numberOfAttackingSoldiers <= 0)
        {
            disabledAttackerDice[1] = true;
            disabledAttackerDice[2] = true;
            disabledAttackerDice[3] = true;
        }

        if(numberOfAttackingSoldiers == 1)
        {
            disabledAttackerDice[2] = true;
            disabledAttackerDice[3] = true;
        }

        if(numberOfAttackingSoldiers == 2)
        {
            disabledAttackerDice[3] = true;
        }

        if(numberOfDefendingSoldiers <= 0)
        {
            disabledDefenderDice[1] = true;
            disabledDefenderDice[2] = true;
        }

        if(numberOfDefendingSoldiers == 1)
        {
            disabledDefenderDice[2] = true;
        }


        if(disabledAttackerDice[1] == false && disabledDefenderDice[1] == false)
            roll_sound.start();
        else
            return;

        String diceImageNumber;
        int number;
        ImageButton dicePicture;

        int dice1, dice2, dice3;
        int highestNum;
        // attacker dice 1
        number = rand.nextInt(max - min + 1) + min;
        dice1 = number;

        if(disabledAttackerDice[1] == false)
            diceImageNumber = "red_die_" + number;
        else
        {
            diceImageNumber = "red_die_1_disabled";
            dice1 = -1;
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_1);
        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));
        if(dice1 == -1)
            dicePicture.setEnabled(false);

        // attacker dice 2
        number = rand.nextInt(max - min + 1) + min;
        dice2 = number;

        if(disabledAttackerDice[2] == false)
            diceImageNumber = "red_die_" + number;
        else
        {
            diceImageNumber = "red_die_1_disabled";
            dice2 = -1;
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_2);
        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));

        if(dice2 == -1)
            dicePicture.setEnabled(false);

        // attacker dice 3
        number = rand.nextInt(max - min + 1) + min;
        dice3 = number;

        if(disabledAttackerDice[3] == false)
            diceImageNumber = "red_die_" + number;
        else
        {
            diceImageNumber = "red_die_1_disabled";
            dice3 = -1;
        }

        dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));

        if(dice3 == -1)
            dicePicture.setEnabled(false);

        highestAttackingDice = dice1;
        highestNum = 1;
        if(dice2 > highestAttackingDice)
        {
            highestAttackingDice = dice2;
            highestNum = 2;
        }
        if(dice3 > highestAttackingDice)
        {
            highestAttackingDice = dice3;
            highestNum = 3;
        }
        secondHighestAttackingDice = -1;
        if(dice1 > secondHighestAttackingDice && highestNum != 1)
            secondHighestAttackingDice = dice1;
        if(dice2 > secondHighestAttackingDice && highestNum != 2)
            secondHighestAttackingDice = dice2;
        if(dice3 > secondHighestAttackingDice && highestNum != 3)
            secondHighestAttackingDice = dice3;

        // defender dice 1
        number = rand.nextInt(max - min + 1) + min;
        dice1 = number;

        if(disabledDefenderDice[1] == false)
            diceImageNumber = "white_die_" + number;
        else
        {
            dice1 = -1;
            diceImageNumber = "white_die_1_disabled";
        }

        dicePicture = (ImageButton) findViewById(R.id.defender_dice_1);
        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));

        if(dice1 == -1)
            dicePicture.setEnabled(false);

        // defender dice 2
        number = rand.nextInt(max - min + 1) + min;
        dice2 = number;
        if(disabledDefenderDice[2] == false)
            diceImageNumber = "white_die_" + number;
        else
        {
            dice2 = -1;
            diceImageNumber = "white_die_1_disabled";
        }

        dicePicture = (ImageButton) findViewById(R.id.defender_dice_2);
        dicePicture.setImageDrawable(getResources().getDrawable(getResourceID(diceImageNumber, "drawable", getApplicationContext() )));

        if(dice2 == -1)
            dicePicture.setEnabled(false);

        highestDefendingDice = dice1 > dice2 ? dice1 : dice2;
        secondHighestDefendingDice = dice1 <= dice2 ? dice1 : dice2;

        int attackerLoss = 0;
        int defenderLoss = 0;

        // attacker wins the first dice
        if((highestAttackingDice > highestDefendingDice) && (highestAttackingDice != -1) && (highestDefendingDice != -1))
        {
            numberOfDefendingSoldiers--;
            defenderLoss++;
        }
        else
        if((highestAttackingDice != -1) && (highestDefendingDice != -1))
        {
            numberOfAttackingSoldiers--;
            attackerLoss++;
        }

        // attacker wins the second dice
        if(secondHighestAttackingDice > secondHighestDefendingDice && (secondHighestAttackingDice != -1) && (secondHighestDefendingDice != -1))
        {
            numberOfDefendingSoldiers--;
            defenderLoss++;
        }
        else
        if((secondHighestAttackingDice != -1) && (secondHighestDefendingDice != -1))
        {
            numberOfAttackingSoldiers--;
            attackerLoss++;
        }

        if(numberOfAttackingSoldiers <= 0 && highestAttackingDice != -1)
        {
            numberOfAttackingSoldiers = 0;
            over = true;
            winner = -1;
            disabledAttackerDice[3] = true;
            disabledAttackerDice[1] = true;
            disabledAttackerDice[2] = true;

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_1);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_2);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

        }

        if(numberOfAttackingSoldiers == 1 && highestAttackingDice != -1)
        {
            disabledAttackerDice[3] = true;
            disabledAttackerDice[2] = true;

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_2);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

        }

        if(numberOfAttackingSoldiers == 2 && highestAttackingDice != -1)
        {
            disabledAttackerDice[3] = true;

            dicePicture = (ImageButton) findViewById(R.id.attacker_dice_3);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("red_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);
        }

        if(numberOfDefendingSoldiers <= 0 && highestDefendingDice != -1)
        {
            numberOfDefendingSoldiers = 0;
            over = true;
            winner = 1;
            disabledDefenderDice[1] = true;
            disabledDefenderDice[2] = true;

            dicePicture = (ImageButton) findViewById(R.id.defender_dice_1);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

            dicePicture = (ImageButton) findViewById(R.id.defender_dice_2);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

        }

        if(numberOfDefendingSoldiers == 1 && highestDefendingDice != -1)
        {
            disabledDefenderDice[2] = true;
            dicePicture = (ImageButton) findViewById(R.id.defender_dice_2);
            dicePicture.setImageDrawable(getResources().getDrawable(getResourceID("white_die_1_disabled", "drawable", getApplicationContext() )));
            dicePicture.setEnabled(false);

        }

        if(over)
        {
            if(numberOfAttackingSoldiers == numberOfDefendingSoldiers)
                winner = 0;
        }

        remainingAttackers.setText("" + numberOfAttackingSoldiers);
        remainingDefenders.setText("" + numberOfDefendingSoldiers);

        String toastMessage = "";
//        String toastMessage = "H-A : " + highestAttackingDice + " vs HD: " + highestDefendingDice + "\n";
//        toastMessage += "2nd H-A: " + secondHighestAttackingDice + " vs 2HD: " + secondHighestDefendingDice + "\n";
        toastMessage += "Attacker lost " + attackerLoss + " soldiers\n";
        toastMessage += "Defender lost " + defenderLoss + " soldiers\n";
        if(over == false && disabledAttackerDice[1] == false && disabledDefenderDice[1] == false)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

        if(over)
        {
            toastMessage = "";
            toastMessage += "Game over\n";
            if(winner == 0)
                toastMessage += "Draw. No one wins...";
            if(winner == 1)
                toastMessage += "Attacker Wins!";
            if(winner == -1)
                toastMessage += "Defender Wins!";

            TextView result = (TextView) findViewById(R.id.result);
            result.setText(toastMessage);

        }

        ProgressBar attackingProgress = (ProgressBar) findViewById(R.id.attackingProgress);
        ProgressBar defendingProgress = (ProgressBar) findViewById(R.id.defendingProgress);

        attackingProgress.setProgress(numberOfAttackingSoldiers);
        defendingProgress.setProgress(numberOfDefendingSoldiers);

    }

    protected final static int getResourceID (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }
}
