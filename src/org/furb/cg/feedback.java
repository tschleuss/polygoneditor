package org.furb.cg;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;

import java.nio.*;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*; 

/**
 * This program demonstrates use of OpenGL feedback. First, a lighting
 * environment is set up and a few lines are drawn. Then feedback mode is
 * entered, and the same lines are drawn. The results in the feedback buffer are
 * printed.
 * 
 * @author Kiet Le (Java conversion)
 */
public class feedback
  extends JFrame
    implements GLEventListener, KeyListener
{
  private GLCapabilities caps;
  private GLCanvas canvas;
  private FPSAnimator animator;
  private GLU glu;
  private GLUT glut;

  public feedback()
  {
    super("feedback");

	  super.setMinimumSize(	new Dimension(500, 500));
    
    canvas = new GLCanvas();
    canvas.addGLEventListener(this);
    canvas.addKeyListener(this);

    getContentPane().add(canvas);
  }

  public void run()
  {
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    canvas.requestFocusInWindow();
  }

  public static void main(String[] args)
  {
    new feedback().run();
  }

  private GLAutoDrawable glDrawable;
  
  private GL gl;
  
  public void init(GLAutoDrawable drawable)
  {
	glDrawable = drawable;
    gl = drawable.getGL();
    glu = new GLU();
    glut = new GLUT();

    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
  }

  public void display(GLAutoDrawable drawable)
  {
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(0.0, 100.0, 0.0, 100.0, 0.0, 1.0);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    teste();

  }
  
  private void teste(){
  
    float feedBuffer[] = new float[1024];
    FloatBuffer feedBuf = BufferUtil.newFloatBuffer(1024);
    int size;
    
    gl.glFeedbackBuffer(1024, GL.GL_2D, feedBuf);
    
    drawGeometry();

    size = gl.glRenderMode(GL.GL_RENDER);
    feedBuf.get(feedBuffer);
    printBuffer(gl, size, feedBuffer);
	    
  }
  
  int rotate = 0;
  void drawGeometry()
  {

	int teste[] = new int []{GL.GL_RENDER, GL.GL_FEEDBACK};	 
	
	for (int i : teste) {
		
		gl.glRenderMode(i);
		
	    //gl.glRotatef(rotate, 0, 0, 1);
	    
	    gl.glBegin(GL.GL_LINE_STRIP);
	    gl.glNormal3f(0.0f, 0.0f, 1.0f);
	    gl.glVertex3f(30.0f, 30.0f, 0.0f);
	    gl.glVertex3f(50.0f, 60.0f, 0.0f);
	    gl.glVertex3f(70.0f, 40.0f, 0.0f);
	    gl.glEnd();
	    
	    if (i == GL.GL_FEEDBACK) gl.glPassThrough(1.0f);
	    gl.glBegin(GL.GL_POINTS);
	    gl.glVertex3f(-100.0f, -100.0f, -100.0f); /* will be clipped */
	    gl.glEnd();
	    
	    if (i == GL.GL_FEEDBACK) gl.glPassThrough(2.0f);
	    
	    gl.glBegin(GL.GL_POINTS);
	    gl.glNormal3f(0.0f, 0.0f, 1.0f);
	    gl.glVertex3f(50.0f, 50.0f, 0.0f);
	    gl.glEnd();
	}

  }

  /* Write contents of one vertex to stdout. */
  void print3DcolorVertex(int size, int count, float[] buffer)
  {
    int i;

    System.out.println("  ");
    for (i = 0; i < 7; i++)
    {
    	//if(buffer[size - count] != 0)
    		System.out.println(" "+ buffer[size - count]);
    		
      count = count - 1;
    }
    System.out.println();
  }

  /* Write contents of entire buffer. (Parse tokens!) */
  private void printBuffer(GL gl, int size, float[] buffer)
  {
    int count;
    float token;

    count = size;
    while (count > 0)
    {
      token = buffer[size - count];
      count--;
      /*
      if (token == GL.GL_PASS_THROUGH_TOKEN)
      {
        System.out.println("GL.GL_PASS_THROUGH_TOKEN");
        System.out.println("\t " + buffer[size - count]);
        count--;
      }
    */
      if (token == GL.GL_POINT_TOKEN)
      {
        System.out.println("GL.GL_POINT_TOKEN");
        print3DcolorVertex(size, count, buffer);
      } 
      else if (token == GL.GL_LINE_TOKEN)
      {
        System.out.println("GL.GL_LINE_TOKEN ");
        print3DcolorVertex(size, count, buffer);
        //print3DcolorVertex(size, count, buffer);
      }
      /*
      else if (token == GL.GL_LINE_RESET_TOKEN)
      {
        System.out.println("GL.GL_LINE_RESET_TOKEN ");
        print3DcolorVertex(size, count, buffer);
        print3DcolorVertex(size, count, buffer);
      }*/
    }
  }

  public void keyTyped(KeyEvent key)
  {
  }

  public void keyPressed(KeyEvent key)
  {
	  
    switch (key.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        System.exit(0);
        break;
      case KeyEvent.VK_F:
    	  rotate +=5;
    	  glDrawable.display();
    	  
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent key)
  {
  }


  public void reshape(GLAutoDrawable drawable, int x, int y, int width,
      int height)
  {
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged)
  {
  }

  /*
   * Draw a few lines and two points, one of which will be clipped. If in
   * feedback mode, a passthrough token is issued between the each primitive.
   */
  
  
}