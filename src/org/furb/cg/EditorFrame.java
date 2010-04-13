package org.furb.cg;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class EditorFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static GLCanvas canvas;
	
	public EditorFrame() {

		super("PolygonEditor");
		
		int screenSize = 500;
		
		setBounds(50,100,screenSize,screenSize); 

		Canvas renderer = new Canvas(screenSize);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8); 

		canvas = new GLCanvas(glCaps);
		add(getCanvas(),BorderLayout.CENTER);
		
		canvas.addGLEventListener(renderer);        
		canvas.addKeyListener(renderer);
		canvas.addMouseListener(renderer);
		canvas.addMouseMotionListener(renderer);
		
	}

	public void setCanvas(GLCanvas canvas) {
		this.canvas = canvas;
	}

	public GLCanvas getCanvas() {
		return canvas;
	}
}
