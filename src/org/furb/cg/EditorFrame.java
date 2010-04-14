package org.furb.cg;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class EditorFrame extends JFrame{

	private static final long serialVersionUID = 3172688540921699213L;
	private GLCanvas canvas = null;
	
	public EditorFrame() {
		super.setTitle("Programa");
		super.setBounds(50, 100, 500, 500);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.getContentPane().setLayout(new BorderLayout());
		this.init();
	}
	
	private void init() {
		GLCapabilities c = new GLCapabilities();
		c.setRedBits(8);
		c.setBlueBits(8);
		c.setGreenBits(8);
		c.setAlphaBits(8);
		
		Canvas jogl = new Canvas(500);
		this.canvas = new GLCanvas(c);
		this.getContentPane().add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(jogl);        
		canvas.addKeyListener(jogl);
		canvas.addMouseListener(jogl);
		canvas.addMouseMotionListener(jogl);
		canvas.requestFocus();		
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
}
