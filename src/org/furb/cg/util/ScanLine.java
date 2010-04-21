package org.furb.cg.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.furb.cg.model.Poligono;

/**
 * Classe responsavel por realizar
 * os cálculos necessários para verificar intersecções 
 * entre polígonos (Singleton).
 * @author Thyago Schleuss
 * @author Luiz Diego Aquino
 * @since 21/04/2010
 */

public class ScanLine {

	private static ScanLine	scanLine = new ScanLine();
	
	private ScanLine() {
		super();
	}
	
	/**
	 * Retorna a Instancia da classe BezierTool
	 * @return
	 */
	public static ScanLine getInstace()
	{
		if( scanLine == null ) {
			scanLine = new ScanLine();
		}
		
		return scanLine;
	}
	
	
	public List<Poligono> intersectionCheck(List<Poligono> poligonos, float x, float y)
	{
		//Cria um array para armazenar o poligonos pre-selcionados
		List<Poligono> preSelecteds = new ArrayList<Poligono>();
		List<Poligono>	selecionados = new ArrayList<Poligono>();
		
		//Para todos os poligonos da tela
		for( Poligono poligon : poligonos )
		{
			//Nenhum esta selecionado ainda
			poligon.setSelected(false);
			
			//Se o ponto do clique esta dentro de sua boundbox
			if( poligon.isOverBoundBox(x, y) )
			{
				//Pre-selciona o poligono
				preSelecteds.add(poligon);
			}
		}
		
		//Para todos os poligonos pre-selecionados
		for( Poligono poligon : preSelecteds )
		{
			//Recupera todos os pares de pontos x,y do poligono pre-selecionado
			Iterator<float[]> iterator = poligon.getPontos().iterator();
			float[] ini = null;
			float[] end = null;
			boolean last = false;
			int count = 0;
			
			while( true ) 
			{
				//Se comecou agora
				if( ini == null ) 
				{
					//Recupera x,y do primeiro ponto
					ini = iterator.next();
				}
				
				//Se ainda ha pontos
				if( iterator.hasNext() )
				{
					//Recupera o proximo x,y do segundo ponto
					end = iterator.next();
				}
				else
				{
					//Senao so ha 1 ponto
					end = poligon.getPontos().get(0);
					last = true;
				}
				
				//Se o ponto inicial x1,y1 for diferente do ponto final x2,y2
				if( ini[1] != end[1] )
				{
					//Utiliza scan line para verifica se o ponto intersecta
					if( this.scanLine(ini, end, x, y) )
					{
						//Soma a quantidade de interseccoes
						count++;
					}
				}
				
				//Finaliza a linha
				ini = end;
				
				//Se for a ultina, termina!
				if( last )
				{
					break;
				}
			}
			
			//Se colidiu com valor impar de pontos
			if( (count % 2) != 0 )
			{
				//Seleciona o poligono e adiciona na lista de selecionados
				poligon.setSelected(true);
				selecionados.add(poligon);
			}
		}
		
		return selecionados;
	}
	
	/**
	 * Verifica interseccao 2D
	 * @param ini
	 * @param end
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean scanLine(float[] ini, float[] end, float x, float y)
	{
		float det = ini[0] + (end[0] - ini[0]) * ( (y - ini[1]) / (end[1] - ini[1]) ); 

		if ( Float.isInfinite(det) ) 
		{
			return false; // nao ha interseccao
		}

		if( det > x && y > Math.min(ini[1], end[1]) && y <= Math.max(ini[1], end[1]) )
		{
			return true; // ha interseccao
		}

		return false;
	}
	
}
