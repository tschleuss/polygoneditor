package org.furb.cg.util;
/**
 * Classe base responsavel por guardar
 * funcoes que envolvam o calculo de rotação 
 * de um ponto 2D (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 24/04/2010
 */
public class Rotation {

	private static Rotation rotation = new Rotation();
	
	private float angle;
	private float rotatedX, rotatedY;
	
	private Rotation() {
		super();
	}
	
	/**
	 * Retorna a Instancia da classe Rotate
	 * @return
	 */
	public static Rotation getInstace()
	{
		if( rotation == null ) {
			rotation = new Rotation();
		}
		
		return rotation;
	}
	
	/**
	 * Utilizado para rotacionar um ponto 2D não normalizado
	 * @param orignX
	 * @param orignY
	 * @param currentX
	 * @param currentY
	 * @return
	 */
	public float[] rotate(float orignX, float orignY, float currentX, float currentY){
		
		//normaliza os pontos utilizando a cordenada da origem
		float normalizedX = currentX - orignX;
		float normalizedY = currentY - orignY;
		
		//rotaciona, a partir do ponto de origem
		this.rotateXY(normalizedX, normalizedY);

		//converte a cordenada recebida para um ponto no espaço não normalizado
		float newX = rotatedX - normalizedX;
		float newY = rotatedY - normalizedY;
		
		return new float []{newX, newY};
		
	}
	
	/**
	 * A partir do ponto de origem, rotaciona um ponto 2D
	 * @param x
	 * @param y
	 * @return
	 */
	private void rotateXY (float x, float y)
	{
		double radian = (this.angle * Math.PI) / 180;
		
		double cosAngle = Math.cos(radian);
		double sinAngle = Math.sin(radian);
		
		rotatedX = (float) ((x * cosAngle) - (y * sinAngle));
		rotatedY = (float) ((y * cosAngle) + (x * sinAngle));
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngle() {
		return angle;
	}
	
}
