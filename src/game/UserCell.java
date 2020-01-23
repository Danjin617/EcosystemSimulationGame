package game;

public class UserCell extends Organism{
	int size = 10;
	int timelLastEaten;
	int energy = 10;
	
	public UserCell() {
		super.length = 30;
		super.width = 30;

	}
	public void move(int direction) {
		super.move(direction);
		//movement will lose 2 energy
		loseEnergy(2);
	}
	
	//eat will gain 10 energy
	public void eat() {
		this.energy = this.energy + 10;
	}
	
	public void loseEnergy(int amount) {
		this.energy = this.energy - amount;
	}
	
	public int checkEnergy() {
		return energy;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTimelLastEaten() {
		return timelLastEaten;
	}

	public void setTimelLastEaten(int timelLastEaten) {
		this.timelLastEaten = timelLastEaten;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}
}
