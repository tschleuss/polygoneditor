package org.furb.cg.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import org.furb.cg.model.GLBezier;
import org.furb.cg.model.GLPoint;
import org.furb.cg.util.Base;

import com.sun.opengl.util.GLUT;

public class Bezier implements Calculo {

	private GL				gl;
	private GLU				glu;
	private GLUT			glut;
	private GLAutoDrawable	glDrawable;
	private GLBezier		bezierTool;
	private int				glFont;
	private boolean			configPointGroup = true;
	private GLPoint			selected;
	
	float[] points = new float[2];
	
	public void init(GL gl, GLU glu, GLUT glut, GLAutoDrawable glDrawable) 
	{
		this.gl			= gl;
		this.glu		= glu;
		this.glut		= glut;
		this.glDrawable	= glDrawable;
		this.bezierTool	= new GLBezier();
		this.glFont		= GLUT.BITMAP_HELVETICA_10;
	}
	
	public void draw()
	{
		 if(bezierTool.getPointsCount() > 0) {
			 bezierTool.draw(glDrawable);
		 }
	}

	public void keyPressed(KeyEvent e) {
		
		int selectedPoint;
		
		switch(e.getKeyChar())
		{
			case ' ':
				
				configPointGroup = !configPointGroup;
				glDrawable.display();
				break;
				
			case '0':
				bezierTool.setPointGroupSelected(-1);
				bezierTool.setPointSelected(-1);
				glDrawable.display();
				break;
				
			case '1':
			case '2':
			case '3':
			case '4':

				selectedPoint = Integer.parseInt(String.valueOf(e.getKeyChar())); 
				
				if(configPointGroup){
					
					bezierTool.setPointGroupSelected(selectedPoint);
				}
				else
				{
					bezierTool.setPointSelected(selectedPoint);
				}
				
				glDrawable.display();

				break;
			default: {
				
				try {
					
					selectedPoint = Integer.parseInt(String.valueOf(e.getKeyChar())); 
					
					if(configPointGroup){
						bezierTool.setPointGroupSelected(selectedPoint);
					}
					
					glDrawable.display();;
					
				} catch (Exception ex) {
					//Exception
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		  //botao direito
		  if (e.getButton() == MouseEvent.BUTTON3)
		  {	
			  selected = bezierTool.getSelectedPoint();
			  if(selected != null){
				  selected.setX(x);
				  selected.setY(y);
			  }
		  }	
		  //botao esquerdo
		  else if( e.getButton() == MouseEvent.BUTTON1 )
		  {
				bezierTool.addPoint(x, y);
				
				if(bezierTool.getPointsCount() == 1){
					bezierTool.setPointGroupSelected(1);
					bezierTool.setPointSelected(1);
				}
		  }
		  
		  glDrawable.display();
	}

	public void keyReleased(KeyEvent e) {
		return;
	}

	public void keyTyped(KeyEvent e) {
		return;
	}

	public void mouseDragged(MouseEvent e) {
		return;
	}

	public void mouseMoved(MouseEvent e) {
		return;
	}

	public void mouseClicked(MouseEvent e) {
		return;
	}

	public void mouseEntered(MouseEvent e) {
		return;
	}

	public void mouseExited(MouseEvent e) {
		return;
	}

	public void mouseReleased(MouseEvent e) {
		return;
	}
}
