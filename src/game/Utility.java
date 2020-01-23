package game;

import java.util.ArrayList;

public class Utility {
	public static int randomNumberInclusive(int low, int high) {
		return (int)(Math.random()*(high-low+1)+low);
	}
	public static int randomNumberInclusive(int low, int high, int notThisOne) {
		int rtn = randomNumberInclusive(low, high);
		if (rtn == notThisOne) {
			rtn = rtn+1;
			if (rtn>high)
				rtn = low;
		}	
		return rtn;
	}
    public static int distance(int x1, int y1, int x2, int y2) {
    	int side1 = Math.abs(x1-x2);
    	int side2 = Math.abs(y1-y2);
    	int distance = (int) Math.sqrt(side1*side1 +side2*side2);
    	
    	return distance;
    }
/*
    public static boolean locationNearWall(Location l, int maxX, int maxY) {
    	boolean rtn = false;
    	if (l.getX()<=5 || l.getY()<=5)
    		rtn = true;
    	else if (maxX-l.getX() <=5 || maxY-l.getY()<=5)
    		rtn = true;
    	
    	return rtn;
    }
*/    
    public static boolean locationOverlapWall(BaseObject obj, int boundaryX1, int boundaryY1, int boundaryX2, int boundaryY2) {
    	boolean rtn = false;
		int x1 = obj.getX();
		int y1 = obj.getY();
		int x2 = x1+obj.getWidth();
		int y2 = y1+obj.getLength();
		
		
		if (x1>=boundaryX2 || x1<=boundaryX1 || y1>=boundaryY2 || y1<=boundaryY1) 
			rtn = true;
		else if (x2>=boundaryX2 || x2<=boundaryX1 || y2>=boundaryY2 || y2<=boundaryY1) 
			rtn = true;		
    	
    	return rtn;
    }
	public static boolean locationInHuntingRange(BaseObject obj1, BaseObject obj2) {
		int xdis = Math.abs(obj1.getX()-obj2.getX());
		int ydis = Math.abs(obj1.getY()-obj2.getY());
		if (xdis<=150 || ydis<=150)
			return true;
		else 
			return false;
	}
	public static boolean locationOverlap(BaseObject obj1, BaseObject obj2) {
		if (locationOverlapObj1InObj2(obj1, obj2) || locationOverlapObj1InObj2(obj2, obj1))
			return true;
		else
			return false;
	}
	public static boolean locationOverlapObj1InObj2(BaseObject obj1, BaseObject obj2) {
		boolean rtn = false;
		
		if (obj1==null || obj2==null)
			return rtn;
		
		int x11 = obj1.getX();
		int y11 = obj1.getY();
		int x12 = x11+obj1.getWidth();
		int y12 = y11+obj1.getLength();
		
		int x21 = obj2.getX();
		int y21 = obj2.getY();
		int x22 = x21+obj2.getWidth();
		int y22 = y21+obj2.getLength();
		
		if (x11>=x21 && x11<=x22 && y11>=y21 && y11<=y22) 
			rtn = true;
		else if (x12>=x21 && x12<=x22 && y12>=y21 && y12<=y22) 
			rtn = true;
		else if (x12>=x21 && x12<=x22 && y11>=y21 && y11<=y22) 
			rtn = true;
		else if (x11>=x21 && x11<=x22 && y12>=y21 && y12<=y22) 
			rtn = true;
		return rtn;
	}
	public static boolean contains (ArrayList<BaseObject> locations, BaseObject l) {
		boolean rtn = false;
		if (l==null)
			return rtn;
		for (int i=0; i<locations.size(); i++) {
			if (l.getX()==locations.get(i).getX() && l.getY()==locations.get(i).getY()) {
				rtn = true;
				break;
			}
		}		
		return rtn;
	}
	public static void deepCopy(ArrayList<BaseObject> toList, ArrayList<BaseObject> fromList) {
		for (int i=0; i<fromList.size(); i++) {
			toList.add(fromList.get(i));
		}
	}
}
