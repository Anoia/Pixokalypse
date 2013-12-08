package util;

/**
 * A point in a 2D grid, with integer x and y coordinates
 * @author badlogic
 *
 */
public class GridPoint2 implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;
	public int y;
	
	public GridPoint2() {
	}
	
	public GridPoint2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public GridPoint2(GridPoint2 point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public GridPoint2 set(GridPoint2 point) {
		this.x = point.x;
		this.y = point.y;
		return this;
	}
	
	public GridPoint2 set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override   
	public boolean equals(Object obj){
	    if (obj instanceof GridPoint2) {
	    	GridPoint2 gp2 = (GridPoint2) obj;
	    	return (this.x == gp2.x) && (this.y == gp2.y);	
	    }
	    return super.equals(obj);
	}
	
    @Override
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(x);
        bits ^= java.lang.Double.doubleToLongBits(y) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
	
	public String toString(){
		 return getClass().getName() + "[x=" + x + ",y=" + y + "]";
	}
}
