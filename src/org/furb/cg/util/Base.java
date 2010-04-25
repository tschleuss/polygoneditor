package org.furb.cg.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Classe base responsavel por guardar
 * funcoes de uso constante, guardar
 * atributos comuns do programa e que
 * devem ficar armazenados independete
 * do estado do programa (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 17/04/2010
 */
public class Base {

	private static Base		base = new Base();
	private float			screenWidth;
	private float			screenHeight;
	private float			left;
	private float			right;
	private float			bottom;
	private float			top;
	
	private float 			panX;
	private float 			panY;
	
	private Base() {
		super();
	}
	
	/**
	 * Retorna a Instancia da classe Base
	 * @return
	 */
	public static Base getInstace()
	{
		if( base == null ) {
			base = new Base();
		}
		
		return base;
	}
	
	/**
	 * Utilizado para normalizar a coordenada X
	 * @param posX
	 * @return
	 */
	public float normalizarX(float x)
	{
		final float xOrigem = screenWidth;		
		final float xDestino = (right - left);
		final float newX = ( x * ( xDestino / xOrigem ) ) + left + panX;
		return newX;
	}
	
	/**
	 * Utilizado para normalizar a coordenada Y
	 * @param posY
	 * @return
	 */
	public float normalizarY(float y)
	{
		//tamanho da toolbar
		final float toolbarFix = 90.0f;
		
		final float yOrigem = screenHeight;		
		final float yDestino = (bottom - (top + toolbarFix) );
		final float newY = ( y * ( yDestino / yOrigem ) ) + top + panY;
		
		return newY;
	}
	
	public float[] rotacionarXY (float x, float y, float angulo)
	{
		double radianos = (angulo * Math.PI) / 180;
		
		double cosAngulo = Math.cos(radianos);
		double sinAngulo = Math.sin(radianos);
		
		float _x = (float) ((x * cosAngulo) - (y * sinAngulo));
		float _y = (float) ((y * cosAngulo) + (x * sinAngulo));
		
		float [] valores = new float[2];
		valores [0] = _x; 
		valores [1] = _y; 
		
		return valores;
	}
	

	/**
	 * Formula usada para calcular a distancia
	 * entre dois pontos.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public float distancia(float[] p1, float[] p2) 
	{
		final float xCalc = Float.valueOf( String.valueOf( pow((p2[0] - p1[0]),2) ) );
		final float yCalc = Float.valueOf( String.valueOf( pow((p2[1] - p1[1]),2) ) );
		return Float.valueOf( String.valueOf( sqrt( xCalc + yCalc ) ) );
	}
	
	public float getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(float screenWidth) {
		this.screenWidth = screenWidth;
	}

	public float getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(float screenHeight) {
		this.screenHeight = screenHeight;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public void setPanX(float panX) {
		this.panX = panX;
	}

	public float getPanX() {
		return panX;
	}

	public void setPanY(float panY) {
		this.panY = panY;
	}

	public float getPanY() {
		return panY;
	}
}
