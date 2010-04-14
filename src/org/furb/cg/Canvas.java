package org.furb.cg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.furb.cg.controller.Bezier;
import org.furb.cg.controller.Poligono;
import org.furb.cg.util.Base;

import com.sun.opengl.util.GLUT;

public class Canvas implements GLEventListener, KeyListener, MouseMotionListener, MouseListener {
	
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;

	private float workspaceWidth;
	private float espaceRightTop;
	private float espaceLeftBottom;

	private int screenSize;
	private Poligono poligonoAtivo;
	
	public Canvas()
	{
		this.workspaceWidth = 30.0f;
		this.espaceRightTop = 40.0f;
		this.espaceLeftBottom = -10.0f;
		this.screenSize = 500;
		
		Base.getInstace().setScreenSize(screenSize);
		Base.getInstace().setWorkspaceWidth(workspaceWidth);
		Base.getInstace().setEspaceRightTop(espaceRightTop);
		Base.getInstace().setEspaceLeftBottom(espaceLeftBottom);
	}
	
	public void init(GLAutoDrawable drawable) {
		
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		//Poligono ativo
		this.poligonoAtivo = new Bezier();
		this.poligonoAtivo.init(gl, glu, glut, glDrawable);
	}


	public void display(GLAutoDrawable drawable) 
	{
		
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();

		 // configurar window
		 glu.gluOrtho2D( espaceLeftBottom,  espaceRightTop,  espaceLeftBottom,  espaceRightTop);

		 // configurar cor de desenho (valores r, g, b)
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 
		 //linhas verticais e horizontais
		 gl.glBegin(GL.GL_LINES);

		 	gl.glVertex2f(0, 0);
		 	gl.glVertex2f(0, workspaceWidth);

		 	gl.glVertex2f(-1, workspaceWidth-1);
		 	gl.glVertex2f(0, workspaceWidth);
		 	gl.glVertex2f(1, workspaceWidth-1);
		 	gl.glVertex2f(0, workspaceWidth);

		 	gl.glVertex2f(0, 0);
		 	gl.glVertex2f(workspaceWidth, 0);

		 	gl.glVertex2f(workspaceWidth-1, -1);
		 	gl.glVertex2f(workspaceWidth, 0);
		 	gl.glVertex2f(workspaceWidth-1, 1);
		 	gl.glVertex2f(workspaceWidth, 0);
		 gl.glEnd();
		
		 //Chama o metodo draw do poligono ativo
		 poligonoAtivo.draw();
		 
		 gl.glFlush();
	}
	
	public void keyPressed(KeyEvent e) {
		this.poligonoAtivo.keyPressed(e);
	}
	
	public void mousePressed(MouseEvent e) {
		this.poligonoAtivo.mousePressed(e);
	}
	
	public void mouseDragged(MouseEvent e) {
		this.poligonoAtivo.mouseDragged(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		this.poligonoAtivo.mouseReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		this.poligonoAtivo.mouseMoved(e);
	}
	
	public void keyReleased(KeyEvent e) {
		this.poligonoAtivo.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		this.poligonoAtivo.keyTyped(e);
	}
	
	public void mouseClicked(MouseEvent e) {
		this.poligonoAtivo.mouseClicked(e);
	}
	
	public void mouseEntered(MouseEvent e) {
		this.poligonoAtivo.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		this.poligonoAtivo.mouseExited(e);
	}
	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		return;
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		return;
	}
}
