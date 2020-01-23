package game;

import java.util.ArrayList;

public class Predator extends Organism {
	int movePauseTimes=0;
	int timeBeforeNaturalDeath;
	public static final int MAX_STEP = 30;
	int direction = Ecosystem.DIRECTION_NORTH;
	BaseObject pickedTargetLocation = null;

	public int getTimeBeforeNaturalDeath() {
		return timeBeforeNaturalDeath;
	}
	public Predator () {
		direction = Utility.randomNumberInclusive(0, 3);
		super.length = 40;
		super.width = 40;
	}

	public void setTimeBeforeNaturalDeath(int timeBeforeNaturalDeath) {
		this.timeBeforeNaturalDeath = timeBeforeNaturalDeath;
	}
	private int determineDirectionBasedOnTargetLocation(BaseObject target) {
		if (!Utility.locationInHuntingRange(this, target))
			return this.direction;
		
		int newDirection = this.direction;
		if (target==null)
			return newDirection;
		int horiValue = target.getX() - super.x;
		int vertValue = target.getY() - super.y;
		
		//go left or right
		if (Math.abs(horiValue) > Math.abs(vertValue)) {
			if (horiValue<0) 
				newDirection  = Ecosystem.DIRECTION_EAST;
			else
				newDirection  = Ecosystem.DIRECTION_WEST;
		}
		//go up or down
		else {
			if (vertValue<0) 
				newDirection  = Ecosystem.DIRECTION_NORTH;
			else
				newDirection  = Ecosystem.DIRECTION_SOUTH;			
		}
	
		return newDirection;
	}
	public boolean pause() {
		//move and pause logic
		if (this.movePauseTimes<=10) {
			movePauseTimes = movePauseTimes+1;
			return true;
		}		
		else {
			movePauseTimes = movePauseTimes+1;
			if (movePauseTimes>=30)
				movePauseTimes = 0;
			return false;
		}
	}
	public void move(Ecosystem eco) {
		ArrayList<BaseObject> locations = eco.getObstacles();
		//Utility.deepCopy(locations, eco.getPredators(this));
		
		pickedTargetLocation = eco.getPlayerAsBaseObject();
		/*
		ArrayList<BaseObject> preyLocations = eco.getPreys();
		ArrayList<BaseObject> targetLocations = new ArrayList<BaseObject>();		
		targetLocations.add(eco.getPlayerAsBaseObject());		
		for (int i=0; i<preyLocations.size(); i++) {
			targetLocations.add(preyLocations.get(i));
		}
		
		if (!Utility.contains(targetLocations, pickedTargetLocation)) {
			//pick one location
			int index = Utility.randomNumberInclusive(0, targetLocations.size()-1);
			pickedTargetLocation = targetLocations.get(index);
		}
		*/
		direction = determineDirectionBasedOnTargetLocation(pickedTargetLocation);
		
		//move and pause logic
		if (pause())
			return;
		
		int originalX = super.x;
		int originalY = super.y;
		move(direction);
		//System.out.println(this.id+":x="+x);
		//System.out.println(this.id+":y="+y);
		
		for (int i=0; i<locations.size(); i++) {
			if (Utility.locationOverlap(this, locations.get(i)) || 
					Utility.locationOverlapWall(this, Ecosystem.getX1(),  Ecosystem.getY1(), Ecosystem.getX2(), Ecosystem.getY2())) {
				super.x = originalX;
				super.y = originalY;
				direction = Utility.randomNumberInclusive(0, 3, direction);
				move(direction);
				break;
			}
		}
		//System.out.println(this.id+"::x="+x);
		//System.out.println(this.id+"::y="+y);

	}
}
