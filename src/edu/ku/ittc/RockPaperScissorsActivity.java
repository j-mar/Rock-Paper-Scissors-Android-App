package edu.ku.ittc;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RockPaperScissorsActivity extends Activity implements OnClickListener{
    private Button button_r, button_p, button_s, button_next, button_reset;
    private ImageView p1_weapon, p2_weapon;
    private TextView winner, score_p1, score_p2;
	
    private int p1Weapon = -1, p2Weapon = -1;
    private int p1Score = 0, p2Score = 0;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initViewElements(savedInstanceState);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
    	savedInstanceState.putInt("p1_score", p1Score);
    	savedInstanceState.putInt("p2_score", p2Score);
    	
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
    	super.onRestoreInstanceState(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	initViewElements(savedInstanceState);
    }

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.button_next)
		{
			startRPS();
		}
		else if (v.getId() == R.id.button_reset)
		{
			updateScores(-1);
			startRPS();
		}
		else
		{
			switch(v.getId())
			{
			case R.id.button_r:
				p1Weapon = 0;
				break;
			case R.id.button_p:
				p1Weapon = 1;
				break;
			case R.id.button_s:
				p1Weapon = 2;
				break;
			}
			
			// Disable weapon buttons
			button_r.setEnabled(false);
			button_p.setEnabled(false);
			button_s.setEnabled(false);
			
			p1_weapon.setImageResource(R.drawable.select_yes);
			
			if(p1Weapon != -1 && p2Weapon != -1)
			{
				countdown();	
			}
			else
			{
				// Player 1 did not select a weapon
			}
		}
	}
	
	private void initViewElements(Bundle savedInstanceState)
	{
        // Initialize scores
        if(savedInstanceState != null)
        {
        	p1Score = savedInstanceState.getInt("p1_score");
        	p2Score = savedInstanceState.getInt("p2_score");
        }
        
        
        // Set up buttons
        button_r = (Button)findViewById(R.id.button_r);
        button_p = (Button)findViewById(R.id.button_p);
        button_s = (Button)findViewById(R.id.button_s);
        button_next = (Button)findViewById(R.id.button_next);
        button_reset = (Button)findViewById(R.id.button_reset);
                
        // Set up labels
        p1_weapon = (ImageView)findViewById(R.id.p1_weapon);
        p2_weapon = (ImageView)findViewById(R.id.p2_weapon);
        winner = (TextView)findViewById(R.id.winner);
        score_p1 = (TextView)findViewById(R.id.score_p1);
        score_p2 = (TextView)findViewById(R.id.score_p2);

        // Allow a button to be clicked
        button_r.setOnClickListener(this);
        button_p.setOnClickListener(this);
        button_s.setOnClickListener(this);
        button_next.setOnClickListener(this);
        button_reset.setOnClickListener(this);
        
        startRPS();
	}
	
	private void startRPS()
	{
		// Enable weapon buttons, disable next round button
		button_r.setEnabled(true);
		button_p.setEnabled(true);
		button_s.setEnabled(true);
		button_next.setEnabled(false);
		button_reset.setEnabled(false);

		// Set weapon selection labels to default "No weapon selected"
		// Set winner label as unknown
		p1_weapon.setImageResource(R.drawable.select_no);
		p2_weapon.setImageResource(R.drawable.select_no);
        winner.setText(R.string.unknown_result);
        
        // Select the computer's weapon first and indicate this to the user
        selectComputerWeapon();
        
        // Wait for player 1 to select a weapon
	}
	
	
	private void revealWeapons()
	{
		p1_weapon.setImageResource(weaponNumberToImgRes(p1Weapon));
		p2_weapon.setImageResource(weaponNumberToImgRes(p2Weapon));
	}
	
	private int getWinner()
	{
		// Compare the player's choices
		int compare = p1Weapon - p2Weapon;
		int result = -1;
		
		if(compare == 0) {
			winner.setText(R.string.tie);			// Tie
			result = 0;
		}
		else if(compare == -1 || compare == 2) {
			winner.setText(R.string.comp_win);		// Computer Wins
			result = 2;
		}
		else if(compare == -2 || compare == 1) {
			winner.setText(R.string.p1_win);		// Player 1 Wins
			result = 1;
		}

		return ( result ) ;

	}
	
	private void updateScores( int result )
	{
		if(result == 2 || result == 0)
		{
			p2Score++;
		}
		if ( result == 1 || result == 0)
		{
			p1Score++;
		}
		if (result == -1 )
		{
			p1Score = 0;
			p2Score = 0;
		}
		
		score_p1.setText(Integer.toString(p1Score));
		score_p2.setText(Integer.toString(p2Score));
		
	}
	
	private int selectRandomWeapon()
	{
		// Set up random number generator
		Random generator = new Random();
		
		// Get random number
		int randomWeapon = generator.nextInt( 3 );
		
		return ( randomWeapon );
	}
	
	private void selectComputerWeapon()
	{
		// Get a random number from 0 to 2
		p2Weapon = selectRandomWeapon();
		
		// Indicate to the user that the computer has selected a weapon
		p2_weapon.setImageResource(R.drawable.select_yes);
	}
	
	private int weaponNumberToImgRes(int weaponNum)
	{
		int weapon = -1;
		
		switch(weaponNum)
		{
		case 0:
			weapon = R.drawable.rock;
			break;
		case 1:
			weapon = R.drawable.paper;
			break;
		case 2:
			weapon = R.drawable.scissors;
			break;
		}
		
		return weapon;
	}

	private void countdown()
	{
		new CountDownTimer(5000, 1000) {

			public void onTick(long millisUntilFinished) {
		    	int count = (int) millisUntilFinished / 1000;
		        switch (count)
		        {
		        case 3:
		        	winner.setText("Rock...");
		        	break;
		        case 2:
		        	winner.setText("Paper...");
		        	break;
		        case 1:
		        	winner.setText("Scissors...");
		        	break;
		        }
		    }

		    public void onFinish()
		    {
		    	revealWeapons();
		    	updateScores(getWinner());
			    button_next.setEnabled(true);
			    button_reset.setEnabled(true);
		    }
		}.start();
	}
}
