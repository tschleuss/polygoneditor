package org.furb.cg.model;

public class Ponto {

	private double x;
	private double y;
	private double z;
	private double w;
	
	public Ponto() {
		this(0, 0, 0, 1);
	}
	
	public Ponto(double x, double y) {
		this(x, y, 0, 1);
	}
	
	public Ponto(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public double getW() {
		return w;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}

	public void setW(double w) {
		this.w = w;
	}
}
