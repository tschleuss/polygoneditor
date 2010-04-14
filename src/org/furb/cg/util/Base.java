package org.furb.cg.util;

public class Base {

	private static Base base = new Base();
	private int screenSize;
	private float workspaceWidth;
	private float espaceRightTop;
	private float espaceLeftBottom;
	
	private Base() {
		super();
	}
	
	public static Base getInstace()
	{
		if( base == null ) {
			base = new Base();
		}
		
		return base;
	}
	
	public int calcX(int baseX){
		float maxX = 480f;
		return (int) (baseX * (50f/maxX)) - 9;
	}
	
	public int calcY(int baseY){
		float maxY = screenSize - espaceRightTop;
		return (int) (40 - (baseY * (50f/maxY)));
	}

	public int getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(int screenSize) {
		this.screenSize = screenSize;
	}

	public float getWorkspaceWidth() {
		return workspaceWidth;
	}

	public void setWorkspaceWidth(float workspaceWidth) {
		this.workspaceWidth = workspaceWidth;
	}

	public float getEspaceRightTop() {
		return espaceRightTop;
	}

	public void setEspaceRightTop(float espaceRightTop) {
		this.espaceRightTop = espaceRightTop;
	}

	public float getEspaceLeftBottom() {
		return espaceLeftBottom;
	}

	public void setEspaceLeftBottom(float espaceLeftBottom) {
		this.espaceLeftBottom = espaceLeftBottom;
	}
}
