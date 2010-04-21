package org.furb.cg.render;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;
/**
 * Classe responsavel pelo desenho de um
 * preview da linha que será adicionada ao polígono
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */
public class PreviewLine extends Base {

	public PreviewLine(GL gl) {
		super(gl);
	}

	/**
	 * Desenha o preview da linha enquanto
	 * o usuario move o mouse.
	 */
	@Override
	public void draw(Poligono line) 
	{
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
		
		for( float[] point : line.getPontos() )
		{
			gl.glVertex2f(point[0], point[1]);
		}
		
		gl.glEnd();
	}

}
