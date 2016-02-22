package model;

import java.awt.Point;

import ui.GamePanel;
import util.Ai;

public class Food {
	
	public Point food;
	
	
	
	public  Food(){
		
	}
	public Food(int x,int y){
		
	    this.food = new Point(x, y);
		
	}
	public void newfood(){
		
		boolean cell_free = false;
		while(!cell_free){
			int x = (int)(Math.random()*GamePanel.WIDTH);
			int y = (int)(Math.random()*GamePanel.HEIGHT);
			food = new Point(x, y);
			cell_free = Ai.is_cell_free(food, GamePanel.snake);
		}
		//food = new Point(2, 0);
	}
	
	public boolean isSnakeEatFood(Snake snake){
		return false;
	}
	
	public void draw(){
		
	}

}
