package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import model.Board;
import model.Food;
import model.Snake;
import sun.net.www.content.audio.aiff;
import util.Ai;

public class GamePanel extends JPanel implements Runnable{
	
	public static final int HEIGHT = 10;
	public static final int WIDTH =  20;
	
	public static final int FOOD = 0;
	public static final int UNDEFINE = (HEIGHT+1)*(WIDTH+1);
	public static final int SNAKE = 2*UNDEFINE;
	
	public static Snake snake = new Snake();
	
	public static Board board = new Board();
	
	public static Food food = new Food(2,2);
	
	private Ai ai = new Ai();
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
		new Thread(this).start();
	}
	
	@Override
	public void paint(Graphics g) {
	   board.drawMe(g);	
	}
	@Override
	public void run() {
		
		int[] best_move;
		
		while(true){ 
			ai.board_reset(snake, food, board);
			if (ai.board_refresh(food, snake, board)) {
				best_move = ai.find_safe_way();
			}
			else{
				best_move = ai.follow_tail();
			}
			if (best_move[0]==-1111&&best_move[1]==-1111) {
				best_move = ai.any_possible_move();
			}
			if (best_move[0]!=-1111&&best_move[1]!=-1111) {
				ai.makeMove(best_move);
				repaint();
			}
			else{
				break;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (GamePanel.snake.size()>=WIDTH*HEIGHT) {
				break;
			}
		}
	}
	
}
