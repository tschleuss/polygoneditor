package org.furb.cg.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.furb.cg.model.Poligono;
import org.furb.cg.model.Ponto;

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
	
	
	public List<Poligono> intersectionCheck(List<Poligono> poligonos, double x, double y)
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
			Iterator<Ponto> iterator = poligon.getPontos().iterator();
			Ponto ini = null;
			Ponto end = null;
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
				if( ini.getY() != end.getY() )
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
		
		this.findPoint(selecionados, x, y);
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
	private boolean scanLine(Ponto ini, Ponto end, double x, double y)
	{
		double det = ini.getX() + (end.getX() - ini.getX()) * ( (y - ini.getY()) / (end.getY() - ini.getY()) ); 

		if ( Double.isInfinite(det) ) 
		{
			return false; // nao ha interseccao
		}

		if( det > x && y > Math.min(ini.getY(), end.getY()) && y <= Math.max(ini.getY(), end.getY()) )
		{
			return true; // ha interseccao
		}

		return false;
	}
	
	/**
	 * Verificar se o usuarios clicou em um dos pontos de
	 * controle e o seleciona.
	 * @param poligonos
	 * @param x
	 * @param y
	 * @return
	 */
	public Ponto findPoint(List<Poligono> poligonos, double x, double y)
	{
		//Para todos os poligonos da tela
		for( Poligono poligon : poligonos )
		{
			for( Ponto points : poligon.getPontos() )
			{
				//Teste - "boundbox" do ponto
				if( x >= (points.getX() - 10) && x <= (points.getX() + 10) && 
					y >= (points.getY() - 10) && y <= (points.getY() + 10) )
				{
					points.setSelected(true);
				}	
			}
		}
		
		return null;
	}
}
