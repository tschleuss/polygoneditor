package org.furb.cg.util;

/**
 * Modos existentes para utilizacao no programa.
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public enum Mode {

	NEW 			(0),
	OPEN_POLYGON 	(1),
	CLOSE_POLYGON 	(2),
	CIRCLE 			(3),
	SPLINE  		(4),
	EXIT    		(5),
	SELECTION		(6),
	ZOOM_IN			(7),
	ZOOM_OUT		(8),
	COLOR			(9);
	
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
