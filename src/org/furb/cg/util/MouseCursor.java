package org.furb.cg.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class MouseCursor {

	private static MouseCursor mouseCursor = new MouseCursor();

	
	private Cursor closedhandCursor;
	private Cursor openhandCursor;
	
	private MouseCursor() {
		super();
		
		this.createHandCursors();
	}
	
	private void createHandCursors(){
		
		int curWidth=32;
		int curHeight=32;
		int curCol;
		Image img;
		int x,y;
		
		int closed_black[] = { 6,5,7,5,9,5,10,5,12,5,13,5,5,6,8,6,11,6,14,6,
				15,6,5,7,14,7,16,7,6,8,16,8,5,9,6,9,16,9,4,10,
				16,10,4,11,16,11,4,12,15,12,5,13,15,13,6,14,14,14,
				7,15,14,15,7,16,14,16,0};
		
		int closed_white[] = { 6,4,7,4,9,4,10,4,12,4,13,4,5,5,8,5,11,5,14,5,15,5,
				4,6,6,6,7,6,9,6,10,6,12,6,13,6,16,6,4,7,15,7,17,7,
				5,8,17,8,4,9,17,9,3,10,5,10,15,10,17,10,3,11,17,11,
				3,12,16,12,4,13,16,13,5,14,15,14,6,15,15,15,6,16,
				15,16,7,17,14,17,0};
		
		int closed_whiteruns[] = {6,13,7,15,7,15,5,15,5,15,5,14,6,14,7,13,8,13,8,13,0};
		
		int open_black[] = { 10,3,11,3,6,4,7,4,9,4,12,4,13,4,14,4,5,5,8,5,9,5,12,5,
				15,5,5,6,8,6,9,6,12,6,15,6,17,6,6,7,9,7,12,7,15,7,16,7,18,7,
				6,8,9,8,12,8,15,8,18,8,4,9,5,9,7,9,15,9,18,9,3,10,6,10,7,10,
				18,10,3,11,7,11,17,11,4,12,17,12,5,13,17,13,5,14,16,14,6,15,
				16,15,7,16,15,16,8,17,15,17,8,18,15,18,0};
		
		int open_white[] = {10,2,11,2,6,3,7,3,9,3,12,3,13,3,5,4,8,4,10,4,11,4,15,4,
				4,5,6,5,7,5,10,5,11,5,13,5,14,5,16,5,17,5,4,6,6,6,7,6,10,6,
				11,6,13,6,14,6,16,6,18,6,5,7,7,7,8,7,10,7,11,7,13,7,14,7,17,7,
				19,7,4,8,5,8,7,8,8,8,10,8,11,8,13,8,14,8,16,8,17,7,19,8,3,9,6,9,
				16,9,17,9,19,9,2,10,4,10,5,10,19,10,2,11,18,11,3,12,18,12,4,13,
				18,13,4,14,17,14,5,15,17,15,6,16,16,16,7,17,18,17,7,18,16,18,
				8,19,15,19,0};
		
		int open_whiteruns[] = {9,14,8,17,4,16,5,16,6,16,6,15,7,15,8,14,9,14,9,14,0};	
	
		int pix[] = new int[curWidth*curHeight];
		for(y=0; y<=curHeight; y++) for(x=0; x<=curWidth; x++) pix[y+x]=0; // all points transparent
		
		// black pixels
		curCol=Color.black.getRGB();
		int n=0;
		while(closed_black[n]!=0)
			pix[closed_black[n++]+closed_black[n++]*curWidth]=curCol;

		// white pixels
		curCol=Color.white.getRGB();
		n=0;
		while(closed_white[n]!=0)
			pix[closed_white[n++]+closed_white[n++]*curWidth]=curCol;


		// white pixel runs
		n=0;
		y=7;
		while(closed_whiteruns[n]!=0) {
			for(x=closed_whiteruns[n++];x<closed_whiteruns[n];x++)
				pix[x+y*curWidth]=curCol;
			n++; y++;
		}
		
		
		img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(curWidth,curHeight,pix,0,curWidth));
		this.closedhandCursor = Toolkit.getDefaultToolkit().createCustomCursor(img,new Point(5,5),"closedhand");

		for(y=0; y<=curHeight; y++) for(x=0; x<=curWidth; x++) pix[y+x]=0; // all points transparent
		
		// black pixels
		curCol=Color.black.getRGB();
		n=0;
		while(open_black[n]!=0)
			pix[open_black[n++]+open_black[n++]*curWidth]=curCol;

		// white pixels
		curCol=Color.white.getRGB();
		n=0;
		while(open_white[n]!=0)
			pix[open_white[n++]+open_white[n++]*curWidth]=curCol;


		// white pixel runs
		n=0;
		y=9;
		while(open_whiteruns[n]!=0) {
			for(x=open_whiteruns[n++];x<open_whiteruns[n];x++)
				pix[x+y*curWidth]=curCol;
			n++; y++;
		}
		
		
		img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(curWidth,curHeight,pix,0,curWidth));
		this.openhandCursor = Toolkit.getDefaultToolkit().createCustomCursor(img,new Point(5,5),"openhand");
	}
	
	/**
	 * Retorna a Instancia da classe Rotate
	 * @return
	 */
	public static MouseCursor getInstace()
	{
		if( mouseCursor == null ) {
			mouseCursor = new MouseCursor();
		}
		
		return mouseCursor;
	}
	
	public void setNormalCursor()
	{
		Base.getInstace().getWindow().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void setOpenHandCursor(){
	    Base.getInstace().getWindow().setCursor(openhandCursor);
	}
	
	public void setCloseHandCursor(){
	    Base.getInstace().getWindow().setCursor(closedhandCursor);
	}
	
}
