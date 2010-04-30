package org.furb.cg.teste2;

// Internal matrix element organization reference
//             [ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// Transform = [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//             [ matrix[2] matrix[6] matrix[10] matrix[14] ]
//             [ matrix[3] matrix[7] matrix[11] matrix[15] ]

public final class Transform {
	private double[] matrix = {	1, 0, 0, 0,
			0, 1, 0, 0,
			0, 0, 1, 0,
			0, 0, 0, 1};

	public Transform() {
	}

	public Point transformPoint(Point point) {
		Point pointResult = new Point(
				matrix[0]*point.getX()  + matrix[4]*point.getY() + matrix[8]*point.getZ()  + matrix[12]*point.getW(),
				matrix[1]*point.getX()  + matrix[5]*point.getY() + matrix[9]*point.getZ()  + matrix[13]*point.getW(),
				matrix[2]*point.getX()  + matrix[6]*point.getY() + matrix[10]*point.getZ() + matrix[14]*point.getW(),
                matrix[3]*point.getX()  + matrix[7]*point.getY() + matrix[11]*point.getZ() + matrix[15]*point.getW());
		return pointResult;
	}

	public Transform transformMatrix(Transform t) {
		Transform result = new Transform();
	    for (int i=0; i < 16; ++i)
        result.matrix[i] =
              matrix[i%4]    *t.matrix[i/4*4]  +matrix[(i%4)+4] *t.matrix[i/4*4+1]
            + matrix[(i%4)+8]*t.matrix[i/4*4+2]+matrix[(i%4)+12]*t.matrix[i/4*4+3];
		return result;
	}
	
	public double getElement(int index) {
		return matrix[index];
	}
	
	public void setElement(int index, double value) {
		matrix[index] = value;
	}

}
