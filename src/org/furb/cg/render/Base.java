package org.furb.cg.render;

import javax.media.opengl.GL;

import org.furb.cg.model.Poligono;

/**
 * Classe abstrata que define o modelo das classes renderizadoras
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */
public abstract class Base {

	protected GL gl;
	
	public Base(GL gl){
		this.gl = gl;
	}
	
	public abstract void draw(Poligono poligon);
	
}
