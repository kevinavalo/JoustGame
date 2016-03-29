//Kevin Avalo (kwa6nf)
//I had help from Nikhil Gupta (ng3br).

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Scoreboard {
	private int score1 = 0;
	private int score2 = 0;
	private boolean gameOver = true;
	private ArrayList<Integer> highScores = new ArrayList<Integer>();
	private int highScoresCapacity = 0;
	
	public Scoreboard(){
		this.score1 = 0;
		this.score2 = 0;
		this.gameOver = false;
		this.highScores = new ArrayList<Integer>();
		this.highScoresCapacity = 3;
	}
	public Scoreboard(int highScoresCapacity){
		this.score1 = 0;
		this.score2 = 0;
		this.gameOver = false;
		this.highScores = new ArrayList<Integer>();
		this.highScoresCapacity = highScoresCapacity;
	}
	public void incrementScore1(){
		this.score1 += 1;
	}
	public void incrementScore2(){
		this.score2 += 1;
	}
	public int getScore1(){
		return this.score1;
	}
	public int getScore2(){
		return this.score2;
	}
	public boolean isGameOver(){
		return this.gameOver;
	}
	public void endGame(){
		this.gameOver = true;
	}
	public ArrayList<Integer> getHighScores(){
		return this.highScores;
	}
	public void addHighScore(int newScore){
		this.highScores.add(newScore);
		Collections.sort(highScores);
		Collections.reverse(highScores);
		while(this.highScores.size() > this.highScoresCapacity){
			this.highScores.remove(highScoresCapacity);
		}
	}
	public void loadHighScores(String filename) throws Exception{
		File scoreFile = new File(filename);
		Scanner input = new Scanner(scoreFile);
		while(input.hasNext()){
			String line = input.next();
			String[] scores = line.split(",");
			for(String s : scores){
				highScores.add(Integer.parseInt(s));	
			}
			
		}
		Collections.sort(highScores);
		Collections.reverse(highScores);
		while(this.highScores.size() > this.highScoresCapacity){
			this.highScores.remove(highScoresCapacity);
		}
		
	}
	public void saveHighScores(String filename) throws Exception{
		PrintWriter writer = new PrintWriter(filename);
		String line = "";
		for(int x : highScores){
			line += x + ",";
		}
		
		line = line.substring(0, line.length() - 1);
		writer.print(line);
		writer.close();
	}
	public String toString(){
		String gameInfo = "";
		String highScore = "";
		for(int x : this.highScores){
			highScore += x + ",";
		}
		if(!highScore.equals("")){
		highScore = highScore.substring(0, highScore.length() - 1);
		}
		if(this.gameOver){
			gameInfo = this.score1 + " - " + this.score2 + "." + " Game is over. High scores: " + highScore;
		} else {
			gameInfo = this.score1 + " - " + this.score2 + "." + " Game is not over. High scores: " + highScore;
		}
		return gameInfo;
	}
}
