package org.furb.cg.model;

/**
 * Classe utilizada para manipulacao de matrizes
 * 
 */
public class Matrix {

	private double[][] elements;

	private final int size = 4;
	
	/**
	 * Inicializa a matriz no contrutor
	 * 
	 * @param matrix
	 */
	public Matrix(double[][] matrix) {
		elements = matrix;
	}

	/**
	 * Retorna uma nova matrix resultante da soma das duas matrizes
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix add(Matrix matrix)
	{
		return new Matrix(this.addElements(matrix));
	}

	/**
	 * Retorna uma nova matrix resultante da subtracao das duas matrizess
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix subtract(Matrix matrix)
	{
		return new Matrix(this.subtractElements(matrix));
	}

	/**
	 * Retorna uma nova matrix resultante da multiplicacao das duas matrizes
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix product(Matrix matrix)
	{
		return new Matrix(this.productElements(matrix));
	}

	/**
	 * Retorna uma nova matrix transposta da matriz atual
	 * 
	 * @return
	 */
	public Matrix transpose() 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[n][m];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				newComponents[j][i] = this.getAt(i, j);
			}
		}

		return new Matrix(newComponents);
	}
	
	/**
	 * Retorna uma nova matrix com a diagonal principal zerada.
	 * 
	 * @return
	 */
	public Matrix zeroMainDiagonal()
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				if( i == j ) {
					newComponents[i][j] = 0.0;
				} else {
					newComponents[i][j] = this.getAt(i, j);
				}
			}
		}
		
		return new Matrix(newComponents);
	}
	
	/**
	 * Retorna uma nova matrix com todos os valores do vetor zerados
	 * 
	 * @return
	 */
	public Matrix zeroMatrix() 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				newComponents[i][j] = 0.0;
			}
		}
		
		return new Matrix(newComponents);
	}
	
	/**
	 * Valida e retorna <code>true</code>
	 * caso as matrizes sejam iguais,
	 * retorna <code>false</code> caso
	 * contrario.
	 * @param matrix
	 * @return
	 */
	public boolean equals(Matrix matrix)
	{
		final int m = getRows();
		final int n = getColumns();
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				final double elA = this.getAt(i, j);
				final double elB = matrix.getAt(i, j);
				
				if( elA != elB ) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Retorna true se a matriz for uma matriz quadrada, retorna false caso
	 * contrario.
	 * 
	 * @return
	 */
	public boolean isSquare() 
	{
		return this.getRows() == this.getColumns();
	}

	/**
	 * Retorna uma nova matrix resultante da multiplicacao de cada elemento da
	 * matriz atual pelo valor informado por parametro
	 * 
	 * @param value
	 * @return
	 */
	public Matrix product(double value) 
	{
		return new Matrix(this.productComponents(value));
	}

	/**
	 * Retorna um vetor com os valores da multiplicacao
	 * 
	 * @param vetor
	 * @return
	 */
	public double[] product(double[] vetor)
	{
		final int m = this.getColumns();

		return secureProduct(vetor);
	}
	
	/**
	 * Retorna o maior elemento existente na matrix
	 * 
	 * @return
	 */
	public double max() 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double maxElement = this.getAt(0, 0);
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				final double atual = this.getAt(i, j);
				
				if( atual > maxElement ) {
					maxElement = atual;
				}
			}
		}
		
		return maxElement;
	}
	
	/**
	 * Retorna uma nova matrix onde os elementos maiores que
	 * <code>value</code> ficarao com os valore em <code>value</code>
	 * @return
	 */
	public Matrix max(double value) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				final double atual = this.getAt(i, j);
				
				if( value > atual ) {
					newComponents[i][j] = value;
				} else {
					newComponents[i][j] = atual;
				}
			}
		}
		
		return new Matrix(newComponents);
	}
	
	/**
	 * Retorna uma nova matrix onde os elementos menores que
	 * <code>value</code> ficarao com os valore em <code>value</code>
	 * @return
	 */
	public Matrix min(double value) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				final double atual = this.getAt(i, j);
				
				if( value < atual ) {
					newComponents[i][j] = value;
				} else {
					newComponents[i][j] = atual;
				}
			}
		}
		
		return new Matrix(newComponents);
	}
	
	/**
	 * Retorna uma nova matrix com o valor absoluto de cada elemento
	 * da matrix atual.
	 * 
	 * @return
	 */
	public Matrix abs() 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				newComponents[i][j] = Math.abs( this.getAt(i, j) );
			}
		}
		
		return new Matrix( newComponents );
	}
	
	/**
	 * Retorna uma nova matrix identidade da matrix atual
	 * 
	 * @param n
	 * @return
	 */
    public Matrix identity() 
    {
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];
		
		for (int i = 0; i < m; i++) 
		{
			for( int j = 0; j < n; j++ ) 
			{
				if( i == j ) {
					newComponents[i][i] = 1.0;
				} else {
					newComponents[i][j] = 0.0;
				}
			}
		}
		
		return new Matrix( newComponents );
    }

	/**
	 * Soma os valores da nova matriz
	 * 
	 * @param matrix
	 * @return
	 */
	private double[][] addElements(Matrix matrix) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newElements = new double[m][n];

		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < n; j++) 
			{
				newElements[i][j] = this.getAt(i, j) + matrix.getAt(i, j);
			}
		}

		return newElements;
	}

	/**
	 * Subtrai os valores da nova matriz
	 * 
	 * @param matrix
	 * @return
	 */
	private double[][] subtractElements(Matrix matrix) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newElements = new double[m][n];

		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				newElements[i][j] = this.getAt(i, j) - matrix.getAt(i, j);
			}
		}
		
		return newElements;
	}

	/**
	 * Multiplica os valores da nova matrizs
	 * 
	 * @param matrix
	 * @return
	 */
	private double[][] productElements(Matrix matrix)
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		final int p = matrix.getColumns();
		double[][] newElements = new double[m][p];

		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < p; j++)
			{
				for (int k = 0; k < n; k++) 
				{
					newElements[i][j] += this.getAt(i, k) * matrix.getAt(k, j);
				}
			}
		}

		return newElements;
	}

	/**
	 * Multiplica os valores da matriz pelo valor passado por parametro
	 * 
	 * @param value
	 * @return
	 */
	private double[][] productComponents(double value) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newComponents = new double[m][n];

		for (int i = 0; i < m; i++) 
		{
			for (int j = 0; j < n; j++) 
			{
				newComponents[i][j] = value * this.getAt(i, j);
			}
		}

		return newComponents;
	}

	/**
	 * Multiplica os valores da matriz com o vetor passado por parametro
	 * 
	 * @param v
	 * @return
	 */
	private double[] secureProduct(double[] v) 
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[] vectorComponents = new double[m];

		for (int i = 0; i < m; i++) 
		{
			vectorComponents[i] = 0.0;
			
			for (int j = 0; j < n; j++)
			{
				vectorComponents[i] += this.getAt(i, j) * v[j];
			}
		}

		return vectorComponents;
	}
	
	/**
	 * Retorna uma nova Matrix contento os valores
	 * da coluna especificada.
	 * @param column
	 * @return
	 */
	public Matrix getColumn(int column)
	{
		final int m = this.getRows();
		double[][] vectorComponents = new double[m][1];
		
		for (int i = 0; i < m; i++) 
		{
			final double value = this.getAt(i, column);
			vectorComponents[i][0] = value;
		}
		
		return new Matrix( vectorComponents );
	}
	
	/**
	 * Converte a matrix de NxM para uma matrix
	 * de 1 coluna s—.
	 * @param matrix
	 * @return
	 */
	public Matrix convertInColumn()
	{
		final int m = this.getRows();
		final int n = this.getColumns();
		double[][] newMatrix = new double[m*n][1];
		int row = 0;
		
		for( int i = 0; i < n; i++ )
		{
			for( int j = 0; j < m; j++ )
			{
				newMatrix[row][0] = this.getAt(j, i);
				row++;
			}
		}
		
		return new Matrix(newMatrix);
	}
	
	/**
	 * Converte a matrix de Nx1 para uma matrix
	 * de NxM (7x5)
	 * @return
	 */
	public Matrix convertInMatrix(int m, int n)
	{
		double[][] newElement = new double[m][n];
		int row = 0;
		int col = 0;
		
		for( int i = 0; i < m*n; i++ )
		{
			newElement[row][col] = this.getAt(i, 0);
			
			if( row < m-1 ) {
				row++;
			} else {
				row = 0;
				col++;
			}
		}
		
		return new Matrix(newElement);
	}

	/**
	 * Retorna o numero de linhas da matriz
	 * 
	 * @return
	 */
	public int getRows() 
	{
		return elements.length;
	}

	/**
	 * Retorna o numero de colunas da matriz
	 * 
	 * @return
	 */
	public int getColumns() 
	{
		return elements[0].length;
	}

	/**
	 * Retorna o elemento da matriz existente na posicao i,j
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public double getAt(int i, int j) 
	{
		return elements[i][j];
	}
	
	/**
	 * Seta o valor no elemento da matriz existente na posicao i,j
	 * 
	 * @param i
	 * @param j
	 * @param value
	 * @return
	 */
	public void setAt(int i, int j, double value) 
	{
		this.elements[i][j] = value;
	}

	/**
	 * Retorna a matriz
	 * 
	 * @return
	 */
	public double[][] getElements() 
	{
		return elements;
	}

	/**
	 * Seta a matriz
	 * 
	 * @param elements
	 */
	public void setElements(double[][] elements) 
	{
		this.elements = elements;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder retValue = new StringBuilder();
		
		for( int i = 0; i < this.getRows(); i++ ) 
		{
			for( int j = 0; j < this.getColumns(); j++ ) 
			{ 
				final double value = this.getAt(i, j);
				
				if( value >= 0 ) {
					retValue.append(" ");	
				}
				
				retValue.append(value);
				retValue.append("    ");
			}
			retValue.append("\n");
		}
		
		return retValue.toString();
	}
	
	public String toMatrixString() 
	{
		StringBuilder retValue = new StringBuilder();
		Matrix printMatrix = this.convertInMatrix(size, size);
		
		for( int i = 0; i < printMatrix.getRows(); i++ ) 
		{
			for( int j = 0; j < printMatrix.getColumns(); j++ ) 
			{ 
				final double value = printMatrix.getAt(i, j);
				if( value >= 0 ) {
					retValue.append(" ");	
				}
				
				retValue.append(value);
				retValue.append("    ");
			}
			retValue.append("\n");
		}
		
		return retValue.toString();
	}
}
