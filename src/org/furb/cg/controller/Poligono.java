package org.furb.cg.controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

public interface Poligono extends KeyListener, MouseMotionListener, MouseListener {

	void init(GL gl, GLU glu, GLUT glut, GLAutoDrawable glDrawable);
	void draw();
	
}
