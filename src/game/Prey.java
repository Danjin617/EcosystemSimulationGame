package game;

import java.util.ArrayList;

public class Prey extends Organism {
	int timeBeforeNaturalDeath;
	int movePauseTimes = 0;
	public static final int MAX_STEP = 20;
	int direction = Ecosystem.DIRECTION_NORTH;

	public int getTimeBeforeNaturalDeath() {
		return timeBeforeNaturalDeath;
	}
	public Prey () {
		direction = Utility.randomNumberInclusive(0, 3);
		super.length = 30;
		super.width = 30;
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
		if (Math.abs(horiValue) <= Math.abs(vertValue)) {
			if (horiValue<0) 
				newDirection  = Ecosystem.DIRECTION_WEST;
			else
				newDirection  = Ecosystem.DIRECTION_EAST;
		}
		//go up or down
		else {
			if (vertValue<0) 
				newDirection  = Ecosystem.DIRECTION_SOUTH;
			else
				newDirection  = Ecosystem.DIRECTION_NORTH;			
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
	/**
	 * keep moving in a randomly generated direction
	 * if near an obstacle, move to one of the other 3 directions (randomly selected)
	 * @param eco
	 */
	public void move(Ecosystem eco) {
		BaseObject pickedTargetLocation = eco.getPlayerAsBaseObject();
		direction = determineDirectionBasedOnTargetLocation(pickedTargetLocation);
		
		//move and pause logic
		if (pause())
			return;

		int originalX = super.x;
		int originalY = super.y;
		move(direction);
		ArrayList<BaseObject> locations = eco.getObstacles();
		//Utility.deepCopy(locations, eco.getPreys(this));
		//Utility.deepCopy(locations, eco.getPredators());
		locations.add(eco.getPlayerAsBaseObject());
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
	}
}
