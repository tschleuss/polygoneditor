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
	
	private double angle;
	private double rotatedX;
	private double rotatedY;
	
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
	public double[] rotate(double orignX, double orignY, double currentX, double currentY)
	{
		//normaliza os pontos utilizando a cordenada da origem
		final double normalizedX = currentX - orignX;
		final double normalizedY = currentY - orignY;
		
		//rotaciona, a partir do ponto de origem
		this.rotateXY(normalizedX, normalizedY);

		//converte a cordenada recebida para um ponto no espaço não normalizado
		final double newX = rotatedX - normalizedX;
		final double newY = rotatedY - normalizedY;
		
		return new double []{ newX, newY };
	}
	
	/**
	 * A partir do ponto de origem, rotaciona um ponto 2D
	 * @param x
	 * @param y
	 * @return
	 */
	private void rotateXY (double x, double y)
	{
		final double radian = (this.angle * Math.PI) / 180;
		final double cosAngle = Math.cos(radian);
		final double sinAngle = Math.sin(radian);
		
		rotatedX = ((x * cosAngle) - (y * sinAngle));
		rotatedY = ((y * cosAngle) + (x * sinAngle));
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}
}
