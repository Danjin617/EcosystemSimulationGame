package game;

public class Obstacle extends BaseObject{
	int timeBeforeNaturalDeath;
	//int x;
	//int y;
	//int length;
	//int width;
	boolean isHarmful;
	
	boolean isRiver = false;
	public Obstacle() {
		super.length = 30;
		super.width = 30;

	}
	public Obstacle copy() {
		Obstacle o = new Obstacle();
		o.setHarmful(this.isHarmful);
		o.setLength(this.length);
		o.setTimeBeforeNaturalDeath(this.timeBeforeNaturalDeath);
		o.setWidth(this.width);
		o.setX(this.x);
		o.setY(this.y);
		return o;
	}
	public int getTimeBeforeNaturalDeath() {
		return timeBeforeNaturalDeath;
	}
	public void setTimeBeforeNaturalDeath(int timeBeforeNaturalDeath) {
		this.timeBeforeNaturalDeath = timeBeforeNaturalDeath;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public boolean isHarmful() {
		return isHarmful;
	}
	public void setHarmful(boolean isHarmful) {
		this.isHarmful = isHarmful;
	}
	public boolean isRiver() {
		return isRiver;
	}
	public void setRiver(boolean isRiver) {
		this.isRiver = isRiver;
	}

}
