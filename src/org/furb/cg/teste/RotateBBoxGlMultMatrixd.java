package org.furb.cg.teste;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class RotateBBoxGlMultMatrixd implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private double[] matrix = {	1, 0, 0, 0,
								0, 1, 0, 0,
								0, 0, 1, 0,
								0, 0, 0, 1};
	
	private Point[] object = {
			new Point(2.0,2.0,0.0,1.0),
			new Point(2.0,4.0,0.0,1.0),		
			new Point(4.0,4.0,0.0,1.0),
			new Point(4.0,2.0,0.0,1.0) };	

	public Point transformPoint(Point point) {
		
		/*
		 * ROTAÇÃO
		final double radian = (5 * Math.PI) / 180;
		final double cosAngle = Math.cos(radian);
		final double sinAngle = Math.sin(radian);
		
		double[] rotate = {  cosAngle, sinAngle, 0,
			                -sinAngle, cosAngle, 0, 
							 0, 0, 1};
							
		Point pointResult = new Point(
				rotate[0]*point.getX()  + rotate[3]*point.getY() + rotate[6]*point.getZ(),
				rotate[1]*point.getX()  + rotate[4]*point.getY() + rotate[5]*point.getZ(),
				0,0);
		*/
		
		double[] escale = {  0.5, 0, 0,
			                 0, 0.5, 0, 
							 0, 0, 1};
							
		Point pointResult = new Point(
				escale[0]*point.getX()  + escale[3]*point.getY() + escale[6]*point.getZ(),
				escale[1]*point.getX()  + escale[4]*point.getY() + escale[5]*point.getZ(),
				0,0);
		
		return pointResult;
	}
	
	
	// "render" feito logo após a inicialização do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// método definido na interface GLEventListener.
	// "render" feito pelo cliente OpenGL.
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();

		 // configurar window
		 glu.gluOrtho2D( -30.0f,  30.0f,  -30.0f,  30.0f);
		 
		 displaySRU();
		 drawObject();

		 gl.glFlush();
	}	

	public void displaySRU() {
		 gl.glColor3f(1.0f, 0.0f, 0.0f);
		 gl.glBegin(GL.GL_LINES);
		 	gl.glVertex2f(-20.0f,  0.0f);
		 	gl.glVertex2f( 20.0f,  0.0f);
		 gl.glEnd();
		 gl.glColor3f(0.0f, 1.0f, 0.0f);
		 gl.glBegin(GL.GL_LINES);
		 	gl.glVertex2f( 0.0f, -20.0f);
		 	gl.glVertex2f( 0.0f,  20.0f);
		 gl.glEnd();
	}
	
	public void drawObject() {
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d( object[0].getX(), object[0].getY());
			gl.glVertex2d( object[1].getX(), object[1].getY());
			gl.glVertex2d( object[2].getX(), object[2].getY());
			gl.glVertex2d( object[3].getX(), object[3].getY());
		gl.glEnd();
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_P:
				System.out.println("P0[" + object[0].getX() + "," + object[0].getY() + "," + object[0].getZ() + "," + object[0].getW() + "]");
				System.out.println("P1[" + object[1].getX() + "," + object[1].getY() + "," + object[1].getZ() + "," + object[1].getW() + "]");
				System.out.println("P2[" + object[2].getX() + "," + object[2].getY() + "," + object[2].getZ() + "," + object[2].getW() + "]");
				System.out.println("P3[" + object[3].getX() + "," + object[3].getY() + "," + object[3].getZ() + "," + object[3].getW() + "]");
				break;
			case KeyEvent.VK_M:
				System.out.println("______________________");
				System.out.println("|" + matrix[ 0] + " | " + matrix[ 1] + " | " + matrix[ 2] + " | " + matrix[ 3] );
				System.out.println("|" + matrix[ 4] + " | " + matrix[ 5] + " | " + matrix[ 6] + " | " + matrix[ 7] );
				System.out.println("|" + matrix[ 8] + " | " + matrix[ 9] + " | " + matrix[10] + " | " + matrix[11] );
				System.out.println("|" + matrix[12] + " | " + matrix[13] + " | " + matrix[14] + " | " + matrix[15] );
				break;
			
			case KeyEvent.VK_RIGHT:
				matrix[4] = 2.0; 		matrix[5] = 1.0;		matrix[6] = 0.0;		matrix[7] = 1.0;
				break;
			case KeyEvent.VK_LEFT:
				matrix[4] = -2.0; 		matrix[5] = 1.0;		matrix[6] = 0.0;		matrix[7] = 1.0;
				break;
			case KeyEvent.VK_DOWN:
				matrix[4] = 0.0; 		matrix[5] = -2.0;		matrix[6] = 0.0;		matrix[7] = 1.0;
				break;
			case KeyEvent.VK_UP:
				matrix[4] = 0.0; 		matrix[5] = 2.0;		matrix[6] = 0.0;		matrix[7] = 1.0;
				break;
	
			case KeyEvent.VK_Z:
				matrix[0] = 0.5; 		matrix[5] = 0.5;		matrix[10] = 1.0;		matrix[15] = 1.0;
				break;
		}

		object[0] = transformPoint(object[0]);
		object[1] = transformPoint(object[1]);
		object[2] = transformPoint(object[2]);
		object[3] = transformPoint(object[3]);
		
//		System.out.println(" --- keyPressed ---");
		glDrawable.display();
	}

	// método definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
//		System.out.println(" --- reshape ---");
	}

	// método definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibição associado foi alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
//		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
//		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
//		System.out.println(" --- keyTyped ---");
	}
	
}

