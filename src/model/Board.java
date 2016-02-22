package model;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import sun.net.www.content.image.gif;
import ui.GamePanel;

public class Board {
	
	public int[][] board = new int[GamePanel.WIDTH][GamePanel.HEIGHT];
	
	
	public Board(){
		init();
	}
	
	private void init(){
		for(int i = 0;i<GamePanel.WIDTH;i++){
			for(int j = 0;j<GamePanel.HEIGHT;j++){
				this.board[i][j] = GamePanel.UNDEFINE;
			}
		}
	}
	
	public boolean isSnakeEatGround(Snake snake){
		return false;
	}
	
	public void drawMe(Graphics g){
		for(int i = 0;i<GamePanel.WIDTH;i++){
			for(int j = 0;j<GamePanel.HEIGHT;j++){
				if (board[i][j]==GamePanel.FOOD) {
					g.setColor(Color.GREEN);
					g.fillRect(i*30, j*30, 29, 29);
				}
				else if (board[i][j]==GamePanel.SNAKE) {
					g.setColor(Color.BLUE);
					g.fillRect(i*30, j*30, 29, 29);
				}else{
					g.setColor(Color.GRAY);
					g.fillRect(i*30, j*30, 29, 29);
				}
			}
		}
		Point head = GamePanel.snake.body.getFirst();
		g.setColor(Color.YELLOW);
		g.fillRect(head.x*30, head.y*30, 29, 29);
	}
	
}
