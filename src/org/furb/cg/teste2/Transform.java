package org.furb.cg.teste2;

import org.furb.cg.model.Matrix;

//             			[ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// transformMatrix = 	[ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//             			[ matrix[2] matrix[6] matrix[10] matrix[14] ]
//             			[ matrix[3] matrix[7] matrix[11] matrix[15] ]

public final class Transform {
	
	private Matrix transformMatrix;

	public Transform() {
		
		double[][] matrix = new double[][]{
											{1,0,0,0},
											{0, 1, 0, 0},
											{0, 0, 1, 0},
											{0, 0, 0, 1}
		};
		
		this.transformMatrix = new Matrix(matrix);
		
	}

	public Point transformPoint(Point point) {
		Point pointResult = new Point(
				transformMatrix.getAt(0, 0)*point.getX()  + transformMatrix.getAt(1, 0)*point.getY() + transformMatrix.getAt(2, 0)*point.getZ()  + transformMatrix.getAt(3, 0)*point.getW(),
				transformMatrix.getAt(0, 1)*point.getX()  + transformMatrix.getAt(1, 1)*point.getY() + transformMatrix.getAt(2, 1)*point.getZ()  + transformMatrix.getAt(3, 1)*point.getW(),
				transformMatrix.getAt(0, 2)*point.getX()  + transformMatrix.getAt(1, 2)*point.getY() + transformMatrix.getAt(2, 2)*point.getZ() + transformMatrix.getAt(3, 2)*point.getW(),
				transformMatrix.getAt(0, 3)*point.getX()  + transformMatrix.getAt(1, 3)*point.getY() + transformMatrix.getAt(2, 3)*point.getZ() + transformMatrix.getAt(3, 3)*point.getW());
		return pointResult;
	}
	
	public double getElement(int  linha, int coluna ) {
		return this.transformMatrix.getAt(linha, coluna);
	}
	
	public void setElement(int  linha, int coluna, double value) {
		this.transformMatrix.setAt(linha, coluna, value);
	}

}
