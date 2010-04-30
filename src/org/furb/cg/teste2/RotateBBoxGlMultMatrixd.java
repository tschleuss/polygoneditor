package org.furb.cg.teste2;

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
	
	private Transform matrix = new Transform();
	
	private Point[] object = {
			new Point(10.0,10.0,0.0,1.0),
			new Point(10.0,20.0,0.0,1.0),		
			new Point(20.0,20.0,0.0,1.0),
			new Point(20.0,10.0,0.0,1.0) };	
	
	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	// metodo definido na interface GLEventListener.
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
		double	Lx = 0;		double	Ly = 0;		double	Lz = 0;
		double	Sx = 1;		double	Sy = 1;		double	Sz = 1;
		

		switch (e.getKeyCode()) {
			case KeyEvent.VK_P:
				System.out.println("P0[" + object[0].getX() + "," + object[0].getY() + "," + object[0].getZ() + "," + object[0].getW() + "]");
				System.out.println("P1[" + object[1].getX() + "," + object[1].getY() + "," + object[1].getZ() + "," + object[1].getW() + "]");
				System.out.println("P2[" + object[2].getX() + "," + object[2].getY() + "," + object[2].getZ() + "," + object[2].getW() + "]");
				System.out.println("P3[" + object[3].getX() + "," + object[3].getY() + "," + object[3].getZ() + "," + object[3].getW() + "]");
				break;
			/*
			case KeyEvent.VK_M:
				
				System.out.println("______________________");
				System.out.println("|" + matrix.getElement( 0) + " | " + matrix.getElement( 1) + " | " + matrix.getElement( 2) + " | " + matrix.getElement( 3));
				System.out.println("|" + matrix.getElement( 4) + " | " + matrix.getElement( 5) + " | " + matrix.getElement( 6) + " | " + matrix.getElement( 7));
				System.out.println("|" + matrix.getElement( 8) + " | " + matrix.getElement( 9) + " | " + matrix.getElement(10) + " | " + matrix.getElement(11));
				System.out.println("|" + matrix.getElement(12) + " | " + matrix.getElement(13) + " | " + matrix.getElement(14) + " | " + matrix.getElement(15));
				
				break;
			
			case KeyEvent.VK_RIGHT:
				Lx = 2.0;
				matrix.setElement(12,Lx); 		matrix.setElement(13,Ly);		matrix.setElement(14,Lz);		matrix.setElement(15,1.0);
				break;
			case KeyEvent.VK_LEFT:
				Lx = -2.0;
				matrix.setElement(12,Lx); 		matrix.setElement(13,Ly);		matrix.setElement(14,Lz);		matrix.setElement(15,1.0);
				break;
			case KeyEvent.VK_UP:
				Ly = 2.0;
				matrix.setElement(12,Lx); 		matrix.setElement(13,Ly);		matrix.setElement(14,Lz);		matrix.setElement(15,1.0);
				break;
			case KeyEvent.VK_DOWN:
				Ly = -2.0;
				matrix.setElement(12,Lx); 		matrix.setElement(13,Ly);		matrix.setElement(14,Lz);		matrix.setElement(15,1.0);
				break;

			case KeyEvent.VK_PAGE_UP:
				Sx = 2.0;		Sy = 2.0;
				matrix.setElement(0,Sx); 		matrix.setElement(5,Sy);		matrix.setElement(10,Sz);		matrix.setElement(15,1.0);
				break;
			case KeyEvent.VK_PAGE_DOWN:
				Sx = 0.5;		Sy = 0.5;
				matrix.setElement(0,Sx); 		matrix.setElement(5,Sy);		matrix.setElement(10,Sz);		matrix.setElement(15,1.0);
				break;

			case KeyEvent.VK_1:
				Lx = -15.0; 	Ly = -15.0;
				Sx = 0.5;		Sy = 0.5;
				matrix.setElement(12,(-Lx)); 	matrix.setElement(13,(-Ly));	matrix.setElement(14,(-Lz));		matrix.setElement(15,1.0);
				matrix.setElement(0,Sx); 		matrix.setElement(5,Sy);		matrix.setElement(10,Sz);			matrix.setElement(15,1.0);
				matrix.setElement(12,Lx); 		matrix.setElement(13,Ly);		matrix.setElement(14,Lz);			matrix.setElement(15,1.0);
				System.out.println("ATENCAO - nao faz escala em relacao a um ponto fixo!");
				break;

			case KeyEvent.VK_2:
				Lx = -15.0; 	Ly = -15.0;
				Sx = 0.5;		Sy = 0.5;
				matrixTranslateInverse.setElement(12, (-Lx)); 	matrixTranslateInverse.setElement(13, (-Ly));	matrixTranslateInverse.setElement(14,(-Lz));		matrixTranslateInverse.setElement(15,1.0);
				matrixScale.setElement(0,Sx); 					matrixScale.setElement(5,Sy);					matrixScale.setElement(10,Sz);						matrixScale.setElement(15,1.0);
				matrixTranslate.setElement(12,Lx); 				matrixTranslate.setElement(13, Ly);				matrixTranslate.setElement(14, Lz);					matrixTranslate.setElement(15,1.0);
				matrix = matrix.transformMatrix(matrixTranslateInverse);
				matrix = matrix.transformMatrix(matrixScale);
				matrix = matrix.transformMatrix(matrixTranslate);
				System.out.println("Multiplicacao de matrizes para escala com um ponto fixo.");
				break;

			case KeyEvent.VK_3:
				Lx = -15.0; 	Ly = -15.0;
				Sx = 2;		Sy = 2;
				matrix.setElement( 0,Sx); 				matrix.setElement( 1,0);					matrix.setElement( 2,0);					matrix.setElement( 3,0);
				matrix.setElement( 4,0); 				matrix.setElement( 5,Sy);					matrix.setElement( 6,0);					matrix.setElement( 7,0);
				matrix.setElement( 8,0);				matrix.setElement( 9,0);					matrix.setElement(10,Sz);					matrix.setElement(11,0);
				matrix.setElement(12,(Lx * Sx) -Lx); 	matrix.setElement(13,(Ly * Sy) - Ly);		matrix.setElement(14,(Lz * Sz) -Lz);		matrix.setElement(15,1);
				System.out.println("Matriz Global para escala com um ponto fixo.");
				break;
			*/
			case KeyEvent.VK_5:
				
				Lx = -15.0; 	
				Ly = -15.0;

				Sx = 2;		
				Sy = 2;
				
				matrix.setElement(0, 0, Sx); 			matrix.setElement(0, 1, 0);				matrix.setElement(0, 2, 0);				matrix.setElement(0, 3, 0);
				matrix.setElement(1, 0, 0); 			matrix.setElement(1, 1, Sy);			matrix.setElement(1, 2, 0);				matrix.setElement(1, 3, 0);
				matrix.setElement(2, 0,0);				matrix.setElement(2, 1,0);				matrix.setElement(2, 2,Sz);				matrix.setElement(2, 3,0);
				matrix.setElement(3, 0,(Lx * Sx) -Lx);	matrix.setElement(3, 1,(Ly * Sy) - Ly);	matrix.setElement(3, 2,(Lz * Sz) -Lz);	matrix.setElement(3, 3,1);

				break;
		}

		object[0] = matrix.transformPoint(object[0]);
		object[1] = matrix.transformPoint(object[1]);
		object[2] = matrix.transformPoint(object[2]);
		object[3] = matrix.transformPoint(object[3]);
		
//		System.out.println(" --- keyPressed ---");
		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
//		System.out.println(" --- reshape ---");
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi alterado.
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
