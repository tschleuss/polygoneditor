package org.furb.cg.render;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;

/**
 * Classe responsavel pelo desenho de uma boundbox
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */
public class BoundBox extends Base{

	
	public BoundBox(GL gl) {
		super(gl);
	}

	/**
	 * Metodo utilizado para desenhar a bound
	 * box dos poligonos na tela do usuario.
	 */
	@Override
	public void draw(Poligono poligon)
	{
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0x00FF); 
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d(poligon.getMinX(),  poligon.getMinY());	
			gl.glVertex2d(poligon.getMaxX(),  poligon.getMinY());	
			gl.glVertex2d(poligon.getMaxX(),  poligon.getMaxY());	
			gl.glVertex2d(poligon.getMinX(),  poligon.getMaxY());	
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
		
		drawCenterPoint(poligon);
	}
	
	/**
	 * Metodo utilizado para desenhar a bound
	 * box dos poligonos selecionados.
	 */
	public void drawOnSelctedPoligons(Poligono poligon)
	{
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glLineStipple(1, (short) 0x00FF); 
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
			gl.glVertex2d(poligon.getMinX(),  poligon.getMinY());
			gl.glVertex2d(poligon.getMaxX(),  poligon.getMinY());
			gl.glVertex2d(poligon.getMaxX(),  poligon.getMaxY());
			gl.glVertex2d(poligon.getMinX(),  poligon.getMaxY());
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
		
		drawCenterPoint(poligon);
	}
	
	private void drawCenterPoint(Poligono poligon)
	{
		 gl.glPointSize(3.0f);
		 gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(poligon.getCenterX() ,poligon.getCenterY());
		 gl.glEnd();
	}
}
