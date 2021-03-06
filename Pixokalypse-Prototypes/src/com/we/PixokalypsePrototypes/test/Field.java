package com.we.PixokalypsePrototypes.test;

public class Field {
	public int blockID;
	public FieldCategory fieldCategory;
	public FieldType fieldType;
	public String spriteName;
	public int xAxis;
	public int yAxis;
	public boolean entered = false;

	public Field(int xAxis, int yAxis) {
		this(xAxis, yAxis, -1, FieldCategory.EMPTY);
	}

	public Field(int xAxis, int yAxis, int blockID, FieldCategory fieldType) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.blockID = blockID;
		this.fieldCategory = fieldType;
	}

	@Override
	public boolean equals(Object obj) {
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
