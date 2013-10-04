package com.we.PixokalypsePrototypes.test;
	
public class Field {
	public int xAxis;
	public int yAxis;
	public int blockID;
	public FieldType fieldType;
	
	public Field(int xAxis, int yAxis){
		this(xAxis, yAxis, -1, FieldType.EMPTY);
	}
	
	public Field(int xAxis, int yAxis, int blockID, FieldType fieldType){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.blockID = blockID;
		this.fieldType = fieldType;
	}
	
	@Override   
	public boolean equals(Object obj){
	    if (obj instanceof Field) {
	    	Field field = (Field) obj;
	    	return (this.xAxis == field.xAxis) && (this.yAxis == field.yAxis);	
	    }
	    return super.equals(obj);
	}
	
    @Override
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(xAxis);
        bits ^= java.lang.Double.doubleToLongBits(yAxis) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
	
	
	
}
