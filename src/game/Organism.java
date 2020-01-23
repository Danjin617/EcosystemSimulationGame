package game;

public class Organism extends BaseObject{
	private static int sequence = 0;
	int id;
	//int x;
	//int y;
	double xSpeed = 0.5;
	double ySpeed = 0.5;
	boolean isAlive = true;
	public Organism() {
		id = sequence;
		sequence = sequence+1;
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
	public double getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	public double getySpeed() {
		return ySpeed;
	}
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static final int MAX_STEP = 25;
	public void move(int direction) {
		if (direction==Ecosystem.DIRECTION_NORTH) {
			int temp = super.y - (int)(MAX_STEP * ySpeed);
			if (temp>=Ecosystem.getY1())
				super.y = temp;
			else 
				super.y = Ecosystem.getY1();
		}
		else if (direction==Ecosystem.DIRECTION_SOUTH) {
			int temp = super.y + (int)(MAX_STEP * ySpeed);
			if (temp<Ecosystem.getY2())
				super.y = temp;
			else 
				super.y = Ecosystem.getY2();
		}
		else if (direction==Ecosystem.DIRECTION_EAST) {
			int temp = super.x = super.x - (int)(MAX_STEP * xSpeed);
			if (temp>=Ecosystem.getX1())
				super.x = temp;
			else 
				super.x = Ecosystem.getX1();
		}
		else if (direction==Ecosystem.DIRECTION_WEST) {
			int temp = super.x = super.x + (int)(MAX_STEP * xSpeed);
			if (temp<Ecosystem.getX2())
				super.x = temp;
			else 
				super.x = Ecosystem.getX2();
		}
		
	}
		
}
