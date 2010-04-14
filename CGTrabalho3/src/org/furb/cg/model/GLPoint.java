package org.furb.cg.model;

public class GLPoint {

	private float x;
	private float y;
	
	private float RED;
	private float GREEN;
	private float BLUE;
	
	private boolean hasColor;
	
	private boolean selected = false;

	public GLPoint(float x, float y){
		this.setX(x);
		this.setY(y);
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setRED(float rED) {
		RED = rED;
		hasColor = true;
	}

	public float getRED() {
		return RED;
	}

	public void setGREEN(float gREEN) {
		GREEN = gREEN;
		hasColor = true;
	}

	public float getGREEN() {
		return GREEN;
	}

	public void setBLUE(float bLUE) {
		BLUE = bLUE;
		hasColor = true;
	}

	public float getBLUE() {
		return BLUE;
	}
	
	public boolean hasColor(){
		return this.hasColor;
	}
	
}
