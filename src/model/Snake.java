package model;

import java.awt.Point;
import java.util.LinkedList;

import ui.GamePanel;

public class Snake {

	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UP = -GamePanel.WIDTH;
	public static final int DOWN = GamePanel.WIDTH;
	
	public LinkedList<Point> body = new LinkedList<Point>();
	
	public Snake(){
	   init();	
	}
	private void init(){
		body.add(new Point(0, 0));
	}
	
	public int size(){
		return body.size();
	}
	
	
	public void move(){
		
	}
	public void changeDirection(){
		
	}
	public void eat(){
		
	}
	public boolean isEatBody(){
		return false;
	}
	public void drawMe(){
		
	}
	
	private class SankeDirver implements Runnable{
		
		@Override
		public void run() {
			
			while(true){
				move();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
