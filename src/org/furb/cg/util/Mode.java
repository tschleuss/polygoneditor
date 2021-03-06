package org.furb.cg.util;

/**
 * Modos existentes para utilizacao no programa.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public enum Mode {

	OPEN_POLYGON 	(1),
	CLOSE_POLYGON 	(2),
	CIRCLE 			(3),
	SPLINE  		(4),
	SELECTION		(5),
	ROTATE			(6),
	SCALE			(7),
	PAN				(8),
	MOVE_POINT		(9),
	DO_NOTHING		(0);
	
	private int mode;
	
	private Mode(int mode) {
		this.mode = mode;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
