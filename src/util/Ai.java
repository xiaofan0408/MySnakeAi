package util;

import java.awt.Point;
import java.awt.print.Printable;
import java.util.LinkedList;

import model.Board;
import model.Food;
import model.Snake;
import ui.GamePanel;

public class Ai {
	
	private int[] mov={Snake.LEFT,Snake.RIGHT,Snake.UP,Snake.DOWN};
	private int[][] mov2 ={{-1,0},{1,0},{0,-1},{0,1}};
	
	private Snake tmpsnake = new Snake();
	
	
	private Board tmpboard = new Board();
	
	private Food food;
	
	public Ai() {
	}
 	
	public static boolean is_cell_free(Point idx,Snake snake){
		for(int i =0;i<snake.body.size();i++){
			Point p = new Point(snake.body.get(i).x, snake.body.get(i).y);
			if (p.x == idx.x && p.y == idx.y) {
				return false;
			}
		}
		return true;
	}
	public boolean is_move_possible(Point idx,int[] move){
		
	//	boolean flag = false;
		
//		if (move == Snake.LEFT) {
//			if (idx.x>0) {
//				flag = true;
//			}
//			else
//			{
//				flag = false;
//			}
//		}
//		else if(move == Snake.RIGHT){
//			if (idx.x < GamePanel.WIDTH) {
//				flag = true;
//			}
//			else
//			{
//				flag = false;
//			}
//			
//		}
//		else if(move == Snake.UP){
//			if (idx.y >0) {
//				flag = true;
//			}
//			else
//			{
//				flag = false;
//			}
//		}
//		else if(move == Snake.DOWN){
//			if (idx.y < GamePanel.HEIGHT) {
//				flag = true;
//			}
//			else
//			{
//				flag = false;
//			}
//		}
		
        Point temp = new Point(idx.x,idx.y);
		
		temp.x = temp.x + move[0];
		temp.y = temp.y + move[1];
		if (temp.x <0|| temp.x>GamePanel.WIDTH-1) {
			return false;
		}
		if (temp.y <0|| temp.y>GamePanel.HEIGHT-1) {
			return false;
		}
		return true;
	}
	public static void board_reset(Snake snake,Food food,Board board){
		for(int i = 0;i<GamePanel.WIDTH;i++){
			for(int j = 0;j<GamePanel.HEIGHT;j++){
				if (i == food.food.x&& j == food.food.y) {
					board.board[i][j] = GamePanel.FOOD;
				}
				else if (is_cell_free(new Point(i, j), snake)) {
					board.board[i][j] = GamePanel.UNDEFINE;	
				}
				else {
					board.board[i][j] = GamePanel.SNAKE;
				}
			}
		}
	}
	public boolean board_refresh(Food food,Snake snake,Board board){
		
		LinkedList<Point> queue = new LinkedList<Point>();
		
		queue.add(food.food);
		
		boolean[][] vis = new boolean[GamePanel.WIDTH+1][GamePanel.HEIGHT+1];
		
		boolean found = false;
		
		while(queue.size()>0){
			
			Point idx = queue.pop();
			
			if (vis[idx.x][idx.y]) {
				continue;
			}
			vis[idx.x][idx.y] = true;
			
			for(int i =0;i<4;i++){
				if (is_move_possible(idx, mov2[i])) {
					
					Point temp = new Point(idx.x,idx.y);
					temp.x = temp.x + mov2[i][0];
					temp.y = temp.y + mov2[i][1];
					
					Point head = snake.body.getFirst();
					
//					if ((idx.x+mov2[i][0])==snake.body.get(0).x&&
//						(idx.y+mov2[i][1])==snake.body.get(0).x) {
//						found = true;
//					}
					if (temp.x == head.x && temp.y == head.y) {
						found = true;
					}
					
//					if (board.board[idx.x+mov2[i][0]-1][idx.y+mov2[i][1]-1]<GamePanel.SNAKE) {
//						if (board.board[idx.x+mov2[i][0]-1][idx.y+mov2[i][1]-1]
//								> board.board[idx.x-1][idx.y-1]+1) {
//							board.board[idx.x+mov2[i][0]-1][idx.y+mov2[i][1]-1] = board.board[idx.x-1][idx.y-1]+1;
//						}
//						if (!vis[idx.x+mov2[i][0]][idx.y+mov2[i][1]]) {
//							Point p = new Point(idx.x+mov2[i][0], idx.y+mov2[i][1]);
//							queue.addLast(p);
//						}
//					}
					if (board.board[temp.x][temp.y]<GamePanel.SNAKE) {
						
						if (board.board[temp.x][temp.y]>board.board[idx.x][idx.y]+1) {
							board.board[temp.x][temp.y]=board.board[idx.x][idx.y]+1;
						}
						if (!vis[temp.x][temp.y]) {
							queue.add(temp);
						}
					}
				}
			}
			
		}
//		for(int i =0;i<GamePanel.WIDTH;i++){
//			
//			for(int j =0;j<GamePanel.HEIGHT;j++){
//				System.out.print(board.board[j][i]+" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		return found;
	}
	public int[]  choose_shortest_safe_move(Snake snake,Board board){
		int[] best_move={-1111,-1111};
	    int min = GamePanel.SNAKE;
	    for(int i =0;i<4;i++){
	    	Point head = snake.body.getFirst();
	    	Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
//	    	if (is_move_possible(snake.body.getFirst(), mov2[i])&&
//	    		board.board[snake.body.getFirst().x+mov2[i][0]][snake.body.getFirst().y+mov2[i][1]]<min) {
//				min = board.board[snake.body.getFirst().x+mov2[i][0]][snake.body.getFirst().y+mov2[i][1]];
//				best_move = mov2[i];
//			}
	    	if (is_move_possible(head, mov2[i])&&board.board[temp.x][temp.y]<min){
				min = board.board[temp.x][temp.y];
				best_move = mov2[i];
			}
	    }
		return best_move;
	}
	public int[] choose_longest_safe_move(Snake snake,Board board){
		
		int[] best_move={-1111,-1111};
	    int max = -1;
	    for(int i =0;i<4;i++){
//	    	if (is_move_possible(snake.body.getFirst(), mov2[i])&&
//	    	board.board[snake.body.getFirst().x+mov2[i][0]][snake.body.getFirst().y+mov2[i][1]]<GamePanel.UNDEFINE
//	    	&&board.board[snake.body.getFirst().x+mov2[i][0]][snake.body.getFirst().y+mov2[i][1]]>max) {
//				max = board.board[snake.body.getFirst().x+mov2[i][0]][snake.body.getFirst().y+mov2[i][1]];
//				best_move = mov2[i];
//			}
	    	Point head = snake.body.getFirst();
	    	Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
			if (is_move_possible(head, mov2[i])&&board.board[temp.x][temp.y]<GamePanel.UNDEFINE
					&&board.board[temp.x][temp.y]>max){
				max = board.board[temp.x][temp.y];
				best_move = mov2[i];
			}
	    }
		return best_move;
	}
	public boolean is_tail_inside(){
		
		Point tail = new Point(tmpsnake.body.getLast().x, tmpsnake.body.getLast().y);
		Point food = new Point(GamePanel.food.food.x, GamePanel.food.food.y);
		
		tmpboard.board[tail.x][tail.y] = 0;
		
		tmpboard.board[food.x][food.y] = GamePanel.SNAKE;
		
		Food tmpfood = new Food(tail.x,tail.y);
		
		boolean result = board_refresh(tmpfood, tmpsnake, tmpboard);
		
		for(int i =0;i<4;i++){
//			if (is_move_possible(tmpsnake.body.get(0), mov2[i])&&
//					(tmpsnake.body.get(0).x==tmpsnake.body.getLast().x)&&
//					(tmpsnake.body.get(0).y==tmpsnake.body.getLast().y)&&tmpsnake.size()>3) {
//				result = false;
//			}
			Point head = new Point(tmpsnake.body.getFirst().x, tmpsnake.body.getFirst().y);
			Point temp = new Point(head.x, head.y);
			temp.x = temp.x + mov2[i][0];
			temp.y = temp.y + mov2[i][1];
			if (is_move_possible(head, mov2[i])&&temp.x == tail.x&&temp.y==tail.y&&tmpsnake.body.size()>3) {
				return false;
			}
		}
		
		return result;
	}
	public int[] follow_tail(){
	//	tmpsnake = new Snake();
		tmpsnake.body.clear();
		for(int i =0;i<GamePanel.snake.body.size();i++){
			
			Point p = new Point(GamePanel.snake.body.get(i).x, GamePanel.snake.body.get(i).y);
			
			tmpsnake.body.add(p);
		}
	//	tmpboard = new Board();
		board_reset(tmpsnake, GamePanel.food, tmpboard);
		
		Point tail = new Point(tmpsnake.body.getLast().x, tmpsnake.body.getLast().y);
		Point food = new Point(GamePanel.food.food.x, GamePanel.food.food.y);
		
		tmpboard.board[tail.x][tail.y] = GamePanel.FOOD;
		tmpboard.board[food.x][food.y] = GamePanel.SNAKE;
		
		Food tmpfood = new Food(tmpsnake.body.getLast().x,tmpsnake.body.getLast().y);
		
		board_refresh(tmpfood, tmpsnake, tmpboard);
		
		tmpboard.board[tail.x][tail.y] = GamePanel.SNAKE;
		
		return choose_longest_safe_move(tmpsnake, tmpboard);
	}
	
	public int[] any_possible_move(){
		
		int[] best_move={-1111,-1111};

		board_reset(GamePanel.snake, GamePanel.food, GamePanel.board);
		
		board_refresh(GamePanel.food, GamePanel.snake, GamePanel.board);
		
		int min = GamePanel.SNAKE;
		
		for(int i =0;i<4;i++){
//		    	if (is_move_possible(GamePanel.snake.body.get(0), mov2[i])&&
//		    		GamePanel.board.board[GamePanel.snake.body.get(0).x+mov2[i][0]][GamePanel.snake.body.get(0).y+mov2[i][1]]<min) {
//					min = GamePanel.board.board[GamePanel.snake.body.get(0).x+mov2[i][0]][GamePanel.snake.body.get(0).y+mov2[i][1]];
//					best_move = mov2[i];
//				}
			 Point head = GamePanel.snake.body.getFirst();
		     Point temp = new Point(head.x, head.y);
			 temp.x = temp.x + mov2[i][0];
			 temp.y = temp.y + mov2[i][1];
			 
			 if (is_move_possible(head, mov2[i])&&GamePanel.board.board[temp.x][temp.y]<min) {
				min = GamePanel.board.board[temp.x][temp.y];
				best_move = mov2[i];
			 }
		 }
			return best_move;
		
	}
	public  void  virtual_shortest_move(){
	//	tmpsnake = new Snake();
		tmpsnake.body.clear();
        for(int i =0;i<GamePanel.snake.body.size();i++){
			
			Point p = new Point(GamePanel.snake.body.get(i).x, GamePanel.snake.body.get(i).y);
			
			tmpsnake.body.add(p);
		}
	//	tmpboard = new Board();
		for(int i =0;i<GamePanel.WIDTH;i++){
			for(int j =0;j<GamePanel.HEIGHT;j++){
				tmpboard.board[i][j] = GamePanel.board.board[i][j];
			}
		}
		board_reset(tmpsnake, GamePanel.food, tmpboard);
		
		boolean food_eated = false;
		
		while(!food_eated){
			  board_refresh(GamePanel.food, tmpsnake, tmpboard);
			  int[] move = choose_shortest_safe_move(tmpsnake, tmpboard);
			  Point oldhead = tmpsnake.body.getFirst();
			  tmpsnake.body.addFirst(new Point(oldhead.x+move[0], oldhead.y+move[1]));
			  Point newhead = tmpsnake.body.getFirst();
			  if (newhead.x==GamePanel.food.food.x&&newhead.y==GamePanel.food.food.y) {
				board_reset(tmpsnake, GamePanel.food, tmpboard);
				tmpboard.board[GamePanel.food.food.x][GamePanel.food.food.y] = GamePanel.SNAKE;
				food_eated =true;
			 }
			 else{
				tmpboard.board[newhead.x][newhead.y] = GamePanel.SNAKE;
				tmpboard.board[tmpsnake.body.getLast().x][tmpsnake.body.getLast().y] = GamePanel.UNDEFINE;
				tmpsnake.body.removeLast();
			  }
		}
	}
	public int[] find_safe_way(){
		int[] safe_move = {-1111,-1111};
		virtual_shortest_move();
		if (is_tail_inside()) {
			return choose_shortest_safe_move(GamePanel.snake, GamePanel.board);
		}
		safe_move = follow_tail();
		return safe_move;
	}
	public void makeMove(int[] pbestmove){
		Point oldhead = GamePanel.snake.body.getFirst();
		GamePanel.snake.body.addFirst(new Point(oldhead.x+pbestmove[0], oldhead.y+pbestmove[1]));
		  Point newhead = GamePanel.snake.body.getFirst();
		  if (newhead.x==GamePanel.food.food.x&&newhead.y==GamePanel.food.food.y) {
			  
			  System.out.println(GamePanel.snake.body.size());
			  if (GamePanel.snake.body.size() == GamePanel.WIDTH*GamePanel.HEIGHT) {
				System.out.println();
			}
			GamePanel.board.board[GamePanel.food.food.x][GamePanel.food.food.y] = GamePanel.SNAKE;
			System.out.println(GamePanel.food.food.x+" "+GamePanel.food.food.y);
			if (GamePanel.snake.size()<GamePanel.WIDTH*GamePanel.HEIGHT) {
				GamePanel.food.newfood();
			}
		 }
		 else{
			 GamePanel.board.board[newhead.x][newhead.y] = GamePanel.SNAKE;
			 GamePanel.board.board[GamePanel.snake.body.getLast().x][GamePanel.snake.body.getLast().y] = GamePanel.UNDEFINE;
			 GamePanel.snake.body.removeLast();
		  }
	}

}
