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

import org.furb.cg.model.GLBezier;
import org.furb.cg.model.GLPoint;

import com.sun.opengl.util.GLUT;

public class Canvas implements GLEventListener, KeyListener, MouseMotionListener, MouseListener {
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;

	private float workspaceWidth;
	private float espaceRightTop;
	private float espaceLeftBottom;
	
	private GLPoint selected;
	private GLBezier bezierTool;
	
	private int screenSize;
	private int glFont;
	
	private boolean configPointGroup = true;	
	
	public Canvas(int screenSize)
	{
		this.screenSize = screenSize;
	}
	
	public void init(GLAutoDrawable drawable) {
		
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		
		workspaceWidth = 30.0f;
		espaceRightTop = 40.0f;
		espaceLeftBottom = -10.0f;
		
		glFont = GLUT.BITMAP_HELVETICA_10;
		
		bezierTool = new GLBezier();
		selected = null;
	}


	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();

		 // configurar window
		 glu.gluOrtho2D( espaceLeftBottom,  espaceRightTop,  espaceLeftBottom,  espaceRightTop);

		 // configurar cor de desenho (valores r, g, b)
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 
		 
		 //linhas verticais e horizontais
		 gl.glBegin(GL.GL_LINES);
		 
		 	//Y
		 	gl.glVertex2f(0, 0);
		 	gl.glVertex2f(0, workspaceWidth);

		 	//seta Y
		 	gl.glVertex2f(-1, workspaceWidth-1);
		 	gl.glVertex2f(0, workspaceWidth);
		 	gl.glVertex2f(1, workspaceWidth-1);
		 	gl.glVertex2f(0, workspaceWidth);
		 	
		 	//X
		 	gl.glVertex2f(0, 0);
		 	gl.glVertex2f(workspaceWidth, 0);
		 	
		 	//seta X
		 	//seta
		 	gl.glVertex2f(workspaceWidth-1, -1);
		 	gl.glVertex2f(workspaceWidth, 0);
		 	gl.glVertex2f(workspaceWidth-1, 1);
		 	gl.glVertex2f(workspaceWidth, 0);
		 gl.glEnd();
		
		 displayMessages();
		 
		 if(bezierTool.getPointsCount() > 0)
			 bezierTool.draw(glDrawable);
		 
		 gl.glFlush();
		 
	}
	
	private void displayMessages() {
		 
		String selecteGroup = String.valueOf(bezierTool.getPointGroupSelected());
		String selectedPoint = String.valueOf(bezierTool.getPointSelected());
		
		if(selecteGroup.equals("-1"))
			selecteGroup = "NENHUM";
		
		if(selectedPoint.equals("-1"))
			selectedPoint = "NENHUM";
		
		 //se está configurando o grupo, seta as letras como vermelhas
		 if(configPointGroup)
			 gl.glColor3f(1.0f, 0.0f, 0.0f);
		 
		 gl.glRasterPos2f(0,-2);
		 glut.glutBitmapString(glFont, "GRUPO DE PONTOS SELECIONADO: " + selecteGroup);

		 //se está configurando o ponto, seta as letras como vermelhas
		 if(!configPointGroup){
			 gl.glColor3f(1.0f, 0.0f, 0.0f);
		 }else
		 {
			 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 }

		 gl.glRasterPos2f(0,-4);
		 glut.glutBitmapString(glFont, "PONTO SELECIONADO: " + selectedPoint);
		 
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 gl.glRasterPos2f(0,-7);
		 glut.glutBitmapString(glFont, "PRESSIONE 'ESPAÇO' PARA ALTERNAR ENTRE GRUPO E PONTO");
		 
		 gl.glRasterPos2f(-5,-9);
		 glut.glutBitmapString(glFont, "PRESSIONE O BOTÃO DIRETO PARA EDITAR A POSIÇÃO DO PONTO SELECIONADO");
	}
	
	public void keyPressed(KeyEvent key) {
		
		int selectedPoint;
		
		switch(key.getKeyChar())
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

				selectedPoint = Integer.parseInt(String.valueOf(key.getKeyChar())); 
				
				if(configPointGroup){
					
					bezierTool.setPointGroupSelected(selectedPoint);
				}
				else
				{
					bezierTool.setPointSelected(selectedPoint);
				}
				
				glDrawable.display();

				break;
			default:
				selectedPoint = Integer.parseInt(String.valueOf(key.getKeyChar())); 
				
				if(configPointGroup){
					
					bezierTool.setPointGroupSelected(selectedPoint);
				}
				glDrawable.display();;
		}
	}
	
	private int calcX(int baseX){
		float maxX = 480f;
		return (int) (baseX * (50f/maxX)) - 9;
	}
	
	private int calcY(int baseY){
		float maxY = screenSize - espaceRightTop;
		return (int) (40 - (baseY * (50f/maxY)));
	}
	
	public void mousePressed(MouseEvent e) {
		
	  int x = calcX(e.getX());
	  int y = calcY(e.getY());
		
	  //botão direito
	  if (e.getButton() == MouseEvent.BUTTON3)
	  {	
		  selected = bezierTool.getSelectedPoint();
		  if(selected != null){
			  selected.setX(x);
			  selected.setY(y);
		  }
	  }	
	  //botão esquerdo
	  else
	  {
			bezierTool.addPoint(x, y);
			
			if(bezierTool.getPointsCount() == 1){
				bezierTool.setPointGroupSelected(1);
				bezierTool.setPointSelected(1);
			}
	  }
	  
	  glDrawable.display();

	}
	
	public void mouseDragged(MouseEvent e) {

	}
	
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent arg0) {
	}
	
	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
	public void mouseClicked(MouseEvent arg0) {
	}
	
	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
	
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {	
	}
	
}
