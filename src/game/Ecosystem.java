package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ecosystem {
	//30
	ArrayList<Prey> preys;
	//10
	ArrayList<Predator> predators;
	//20
	ArrayList<Obstacle> obstacles;
	ArrayList<Integer> locationx;
	ArrayList<Integer> locationy;
	UserCell player;
	int score = 0;
	int showDisasterArea = 0;
	int disasterAreaX1;
	int disasterAreaWidth ;
	int disasterAreaY1 ;
	int disasterAreaLength ;
	int spawnPrey = 0;
	int spawnPredator = 0;
	int repeatDisaster = 0;
	int shrinkPlayer = 0;
	Obstacle[] rivers = new Obstacle[6];
	//Obstacle r2;
	

	public static int boundaryX;
	public static int boundaryY;
	public static int maxX;
	public static int maxY;
	public static int screenX;
	public static int screenY;
	private int xChange;
	private int yChange;
	BufferedImage imagePrey;
	BufferedImage imagePredator;
	BufferedImage imagePlayer;
	BufferedImage imageTree;
	BufferedImage imageRiver;
	BufferedImage bg;
	BufferedImage disaster;
	JPanel parent;
	
	public static int getX1() {
		return boundaryX;
	}
	public static int getY1() {
		return boundaryY;
	}
	public static int getX2() {
		return boundaryX+maxX;
	}
	public static int getY2() {
		return boundaryY+maxY;
	}
	public void init(
			int xSize, int ySize, 
			int screenXSize, int screenYSize,
			BufferedImage imagePrey,
			BufferedImage imagePredator,
			BufferedImage imagePlayer,
			BufferedImage imageTree,
			BufferedImage imageRiver,
			BufferedImage bg,
			BufferedImage disaster,
			JPanel parent
			) {
		this.imagePlayer = imagePlayer;
		this.imagePredator = imagePredator;
		this.imagePrey = imagePrey;
		this.imageTree = imageTree;
		this.imageRiver = imageRiver;
		this.bg = bg;
		this.disaster = disaster;
		this.parent = parent;
		//this.x
		
		showDisasterArea = 0;
		spawnPredator =0;
		spawnPrey = 0;
		repeatDisaster = 50;
		shrinkPlayer = 0;
		maxX = xSize;
		maxY = ySize;
		screenX = screenXSize;
		screenY = screenYSize;
		boundaryX = (screenX-maxX)/2;
		boundaryY =(screenY-maxY)/2;
		player = new UserCell();
		player.setX((screenX-50)/2);
		player.setY((screenY-150)/2);
		preys = new ArrayList<Prey>();
		for (int i=0; i<3; i++) {
			Prey p = new Prey();
			int x = Utility.randomNumberInclusive(getX1()+p.getWidth(), getX2()-p.getWidth());
			int y = Utility.randomNumberInclusive(getY1()+p.getLength(), getY2()-p.getLength());
			p.setX(x);
			p.setY(y);
			preys.add(p);
		}
		predators = new ArrayList<Predator>();
		for (int i=0; i<1; i++) {
			Predator p = new Predator();
			int x = Utility.randomNumberInclusive(getX1()+p.getWidth(), getX2()-p.getWidth());
			int y = Utility.randomNumberInclusive(getY1()+p.getLength(), getY2()-p.getLength());
			p.setX(x);
			p.setY(y);
			predators.add(p);
		}
		obstacles = new ArrayList<Obstacle>();
		for (int i=0; i<10; i++) {
			Obstacle p = new Obstacle();
			int x = Utility.randomNumberInclusive(getX1()+p.getWidth(), getX2()-p.getWidth());
			int y = Utility.randomNumberInclusive(getY1()+p.getLength(), getY2()-p.getLength());
			p.setX(x);
			p.setY(y);
			obstacles.add(p);
		}
		for (int i=0; i<rivers.length; i++) {
			int x, y;
			Obstacle r = new Obstacle();
			r.setRiver(true);
			if (i<3) {
				x = 200;//Utility.randomNumberInclusive(0, 28);
			}	
			else {
				x = maxX - 1000;
			}	
			if (i==0 || i==3) {
				y = 30;//Utility.randomNumberInclusive(0, maxY);
				r.setLength(200);
				r.setWidth(20);
			}	
			else if (i==1 || i==4) {
				y = 300;
				r.setLength(200);
				r.setWidth(20);
			}	
			else {
				y = 600;
				r.setLength(150);
				r.setWidth(20);
			}	
			r.setX(x);
			r.setY(y);
			if (i==1 || i==4) {
				r.setWidth(100);
			}
			if (i==0 || i==3) {
				r.setWidth(50);
			}
			if (i==2 || i==5) {
				r.setWidth(40);
			}
			obstacles.add(r);
			rivers[i] = r;
		}
	}
	public void shrinkPlayer() {
		shrinkPlayer++;
		if (shrinkPlayer>=10) {
			if (this.player.getLength()>=30 && this.player.getWidth()>=30) {
				this.player.setLength(this.player.getLength()-2);
				this.player.setWidth(this.player.getWidth()-2);	
			}
			shrinkPlayer = 0;
		}
	}
	public void spawn() {
		//return;
		
		spawnPredator ++;
		spawnPrey ++;
		
		if (spawnPredator>=30) {
			Predator p = new Predator();
			if (this.predators.size()==0) {
				int x = Utility.randomNumberInclusive(getX1()+p.getWidth(), getX2()-p.getWidth());
				int y = Utility.randomNumberInclusive(getY1()+p.getLength(), getY2()-p.getLength());
				p.setX(x);
				p.setY(y);
				
			}
			else {
				int random = Utility.randomNumberInclusive(0,predators.size()-1);
				p.setX(predators.get(random).getX());
				p.setY(predators.get(random).getY());
			}
			predators.add(p);
			spawnPredator = 0;
		}
		if (spawnPrey>=15) {
			Prey p = new Prey();
			if (this.preys.size()==0) {
				int x = Utility.randomNumberInclusive(getX1()+p.getWidth(), getX2()-p.getWidth());
				int y = Utility.randomNumberInclusive(getY1()+p.getLength(), getY2()-p.getLength());
				p.setX(x);
				p.setY(y);
				
			}
			else {
				int random = Utility.randomNumberInclusive(0,preys.size()-1);
				p.setX(preys.get(random).getX());
				p.setY(preys.get(random).getY());
			}
			preys.add(p);
			spawnPrey = 0;
		}

	}
	
	public void moveRelativePositions(int mouseX, int mouseY) {
		System.out.println("mouse moved");
		xChange = (this.player.getX() - mouseX)/2;
		if (xChange>15) xChange = 15;
		if (xChange<-15) xChange = -15;
		
		yChange = (this.player.getY() - mouseY)/2;
		if (yChange>15) yChange=15;
		if (yChange<-15) yChange=-15;
		
		//if (Utility.locationOverlapWall(this.player, boundaryX+xChange, boundaryY, boundaryX+xChange+maxX,  boundaryY+maxY))
		//	xChange=-xChange;
		//if (Utility.locationOverlapWall(this.player, boundaryX, boundaryY+yChange, boundaryX+maxX,  boundaryY+yChange+maxY))
		//	yChange = -yChange;
		
		if (this.player.getX()<=boundaryX+15) {
			xChange = 20+ boundaryX - this.player.getX();
		}
		if (this.player.getY()<=boundaryY+15) {
			yChange = 20 + boundaryY - this.player.getY();
		}
		if (this.player.getX()+this.player.getWidth()>=boundaryX+maxX-15) {
			xChange = boundaryX+maxX-(this.player.getX()+this.player.getWidth()) - 20;
		}
		if (this.player.getY()+this.player.getLength()>=boundaryY+maxY-15) {
			yChange = boundaryY+maxY-(this.player.getY()+this.player.getLength()) - 20;
		}
		
		//if (boundaryX+xChange<screenX-maxX || boundaryX+xChange>maxX-screenX) 
		//	xChange = 0;
		//if (boundaryY+yChange<screenY-maxY || boundaryY+yChange>maxY-screenY) {
		//	yChange = 0;
		//}
		
		for (int i=0; i<this.obstacles.size(); i++) {
			Obstacle p = this.obstacles.get(i).copy();
			if (Utility.locationOverlap(this.player, p)) {
				xChange = -xChange;
				yChange = -yChange;
			}
		}	
		 //moveRelativePositions();
	}
	
	private void moveRelativePositions() {
		if (xChange==0 || yChange==0) {
			System.out.println("no relative move");
			return;
		}	
		
		for (int i=0; i<this.obstacles.size(); i++) {
			//Obstacle p = this.obstacles.get(i).copy();
			if (
					//Utility.locationOverlap(this.player, p) ||
					Utility.locationOverlapWall(this.player, Ecosystem.getX1(),  Ecosystem.getY1(), Ecosystem.getX2(), Ecosystem.getY2())) {

				xChange = -xChange;
				yChange = -yChange;
				break;
			}
			else {
			}
		}	
		
		for (int i=0; i<this.preys.size(); i++) {
			Prey p = preys.get(i);
			p.setX(p.getX()+xChange);
			p.setY(p.getY()+yChange);
		}
		for (int i=0; i<this.predators.size(); i++) {
			Predator p = predators.get(i);
			p.setX(p.getX()+xChange);
			p.setY(p.getY()+yChange);
		}
		for (int i=0; i<this.obstacles.size(); i++) {
			Obstacle p = this.obstacles.get(i);
			p = obstacles.get(i);
			p.setX(p.getX()+xChange);
			p.setY(p.getY()+yChange);

		}
		boundaryX+=xChange;
		boundaryY+=yChange;
		if (xChange>0) xChange--;
		else if (xChange<0) xChange++;
		//else xChange++;
		if (yChange>0)	yChange--;
		else if (yChange<0) yChange++;
		//else yChange++;
	}
	public boolean advance(int playerDirection) {
		boolean gameOver = false;
		executeDisaster();
		spawn();
		repeatDisaster();
		shrinkPlayer();
		
		moveRelativePositions() ;
		//System.out.println(xChange+" "+yChange);
		
		//move
		for (int i=0; i<this.preys.size(); i++) {
			preys.get(i).move(this);
		}
		for (int i=0; i<this.predators.size(); i++) {
			predators.get(i).move(this);
		}
		//if (playerDirection!=-1)
		//	player.move(playerDirection);
		
		//organisms fall into river
		for (int i=0; i< rivers.length; i++) {			
			if (Utility.locationOverlap(rivers[i], this.player)) {
				gameOver = true;
				return gameOver;
			}
			Iterator<Prey> k = this.preys.iterator();
			while (k.hasNext()) {
				Prey p = k.next();
				if (Utility.locationOverlap(rivers[i], p)) {
					k.remove();
				}
			}
			Iterator<Predator> k2 = this.predators.iterator();
			
			while (k2.hasNext()) {
				Predator p = k2.next();
				if (Utility.locationOverlap(rivers[i], p)) {
					k2.remove();
				}
			}
		}
		
		//predator eat
		for (int i=0; i<this.predators.size(); i++) {
			//predators eat preys
			Iterator<Prey> j = this.preys.iterator();
			while (j.hasNext()) {
				if (Utility.locationOverlap(this.predators.get(i), j.next())) {
					j.remove();
				}
			}
			//predators eat player
			if (Utility.locationOverlap(this.predators.get(i), this.getPlayerAsBaseObject())) {
				gameOver = true;
				return gameOver;
			}
		}
		//player eat
		Iterator<Prey> j = this.preys.iterator();
		while (j.hasNext()) {
			if (Utility.locationOverlap(this.getPlayerAsBaseObject(), j.next())) {
				j.remove();
				this.score = this.score+1;
				this.getPlayer().setLength(this.getPlayer().getLength()+10);
				this.getPlayer().setWidth(this.getPlayer().getWidth()+10);
				
			}
		}

		return gameOver;
	}
	public static final int DIRECTION_NORTH = 0;
	public static final int DIRECTION_SOUTH = 1;
	public static final int DIRECTION_EAST = 2;
	public static final int DIRECTION_WEST = 3;
	
	public ArrayList<BaseObject> getObstacles() {
		ArrayList<BaseObject> rtn = new ArrayList<BaseObject>();
		for (int i=0; i<this.obstacles.size(); i++) {
			rtn.add(this.obstacles.get(i));
		}
		return rtn;
	}
	public ArrayList<BaseObject> getPreys(Prey p) {
		ArrayList<BaseObject> rtn = new ArrayList<BaseObject>();
		for (int i=0; i<this.preys.size(); i++) {
			if (p!=null && p.getId()==this.preys.get(i).getId())
				continue;
			rtn.add(p);
		}
		return rtn;
	}	
	public ArrayList<BaseObject> getPreys() {
		return getPreys(null);
	}
	public ArrayList<BaseObject> getPredators(Predator p) {
		ArrayList<BaseObject> rtn = new ArrayList<BaseObject>();
		for (int i=0; i<this.predators.size(); i++) {
			if (p!=null && p.getId()==this.predators.get(i).getId())
				continue;
			rtn.add(p);
		}
		return rtn;
	}
	public ArrayList<BaseObject> getPredators() {
		return getPredators(null);
	}
	
	public void movePlayer(int direction) {
		player.move(direction);
	}
	public void collision() {
		
	}
	public void repeatDisaster() {
		if (this.repeatDisaster>=0)
			this.repeatDisaster--;
		else {
			disaster();
			this.repeatDisaster = 50;
		}
	}
	public void disaster()  {
		int x1 = screenX/5;
		int width = screenX/2;
		int y1 = screenY/5;
		int length = screenY/2;
		disasterAreaX1 = x1;
		disasterAreaWidth = width;
		disasterAreaY1 = y1 ;
		disasterAreaLength  = length ;
		showDisasterArea = 15;
	}
	public void executeDisaster() {
		if (showDisasterArea<=0)
			return;
		BaseObject o= new BaseObject();
		o.setX(disasterAreaX1);
		o.setY(disasterAreaY1);
		o.setWidth(disasterAreaWidth);
		o.setLength(disasterAreaLength);
		Iterator<Prey> j = this.preys.iterator();
		while (j.hasNext()) {
			if (Utility.locationOverlap(o, j.next())) {
				j.remove();
			}
		}
		Iterator<Predator> i = this.predators.iterator();
		while (i.hasNext()) {
			if (Utility.locationOverlap(o, i.next())) {
				i.remove();
			}
		}
	}
	public void die() {
		
	}

	public void setPreys(ArrayList<Prey> preys) {
		this.preys = preys;
	}

	public void setPredators(ArrayList<Predator> predators) {
		this.predators = predators;
	}


	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public ArrayList<Integer> getLocationx() {
		return locationx;
	}

	public void setLocationx(ArrayList<Integer> locationx) {
		this.locationx = locationx;
	}

	public ArrayList<Integer> getLocationy() {
		return locationy;
	}

	public void setLocationy(ArrayList<Integer> locationy) {
		this.locationy = locationy;
	}

	public UserCell getPlayer() {
		return player;
	}

	public void setPlayer(UserCell player) {
		this.player = player;
	}

	public void setPlayerLocation(int x, int y) {
		this.player.setX(x);
		this.player.setY(y);
	}
	public int getScore() {
		return score;
	}
	public BaseObject getPlayerAsBaseObject() {
		return this.player;
	}
	public void setScore(int score) {
		this.score = score;
	}
    public void show (Graphics g)
    {
    	//g.setColor (Color.green);
		//g.fillRect(0, 0, maxX, maxY);
      	g.drawImage(bg, boundaryX, boundaryY, maxX, maxY, null);

    	for (int i=0; i<this.obstacles.size();i++) {
    		if (this.obstacles.get(i).isRiver()) {
        		//g.setColor (Color.cyan);
        		//g.fillRect (this.obstacles.get(i).getX(), this.obstacles.get(i).getY(), this.obstacles.get(i).getWidth(),  this.obstacles.get(i).getLength());
              	g.drawImage(imageRiver, this.obstacles.get(i).getX(), this.obstacles.get(i).getY(), this.obstacles.get(i).getWidth(),  this.obstacles.get(i).getLength(), null);
    		}
    		else {
        		//g.setColor (Color.green);
        		//g.fillRect (this.obstacles.get(i).getX(), this.obstacles.get(i).getY(), this.obstacles.get(i).getWidth(),  this.obstacles.get(i).getLength());
              	g.drawImage(imageTree, this.obstacles.get(i).getX(), this.obstacles.get(i).getY(), this.obstacles.get(i).getWidth(),  this.obstacles.get(i).getLength(), null);
    		}
    	}
    	for (int i=0; i<this.preys.size();i++) {
       		//g.setColor (Color.yellow);
       	    //g.fillOval(this.preys.get(i).getX(), this.preys.get(i).getY(),  this.preys.get(i).getWidth(),  this.preys.get(i).getLength());
       	 	g.drawImage(imagePrey, this.preys.get(i).getX(), this.preys.get(i).getY(), this.preys.get(i).getWidth(),  this.preys.get(i).getLength(), null);
    	}
    	for (int i=0; i<this.predators.size();i++) {
       		//g.setColor (Color.red);
    		//g.fillRect(this.predators.get(i).getX(), this.predators.get(i).getY(),  this.predators.get(i).getWidth(),  this.predators.get(i).getLength());
        	g.drawImage(imagePredator, this.predators.get(i).getX(), this.predators.get(i).getY(), this.predators.get(i).getWidth(),  this.predators.get(i).getLength(), null);
        	
    	}
   		//g.setColor (Color.blue);   	 
    	//g.fillOval(this.player.getX(), this.player.getY(), this.player.getWidth(),  this.player.getLength());
    	g.drawImage(imagePlayer, this.player.getX(), this.player.getY(), this.player.getWidth(),  this.player.getLength(), null);
    	
    	if (showDisasterArea>0) {
    		//g.setColor (Color.red);  
    		//g.drawRect(disasterAreaX1, disasterAreaY1, disasterAreaWidth, disasterAreaLength);
        	g.drawImage(this.disaster, disasterAreaX1, disasterAreaY1, disasterAreaWidth, disasterAreaLength, null);
   		
    		showDisasterArea--;
    	}
  	//mark game area
		//g.drawRect(0, 0, maxX, maxY);
    	
     }

}
