package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Coleccion;

public class GraficadorGrafica<T extends Comparable<T>>{
    String cadena = "";
    /**
     * Regresa la represetnacion svg de una grafica.
     * @param elementos, una lista con los elementos de la grafica.
     * @return representacion svg con los elementos dados.
     */
    public String graficaToSvg(Lista<Integer> elementos) {

	boolean searma = false;
	Grafica grafica = new Grafica<>();
	int [] el = new int[elementos.getLongitud()];
	int [] aux = new int[elementos.getLongitud()];
	if(elementos.getLongitud() % 2 == 0){
	    searma = true;
	    int cont = 0;
	    for(int e : elementos){	       	    
		el[cont] = e;
		cont ++;
	    }
	    cont = 0;
	    for(int i = 0; i < el.length; i+=2){
		if(!grafica.contiene(el[i]))
		    grafica.agrega(el[i]);
		if(!grafica.contiene(el[i+1]) && el[i] != el[i+1])
		    grafica.agrega(el[i+1]);
		
		if(el[i] == el[i+1]){
		    aux[cont] = el[i];
		    cont ++;
		}
		else{
		    try{
			grafica.conecta(el[i], el[i+1]);
		    }
		    catch(Exception e){
			continue;
		    }
		}
	    }
	    for(int i = 0; i < aux.length; i++){
		if(grafica.contiene(aux[i])){
		    VerticeGrafica<Integer> v = grafica.vertice(aux[i]);         		    for(VerticeGrafica<?> vecino : v.vecinos())
			grafica.desconecta(v.get(), vecino.get());		
		}
	    }
	    
	    //System.out.println(grafica);
	    
	    int ancho = grafica.getElementos() * 50 + 50;
	    int altura = 700;
	    cadena =  "<?xml version='1.0' encoding='UTF-8' ?> \n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
	    int yarriba = 200;
	    int yabajo = 500;
	    int media = grafica.getElementos()/2;	    
	    Lista<Object> lista = new Lista<>();
	    grafica.paraCadaVertice((v) -> lista.agrega(v.get()));
	    int jj = 1;
	    final int [] j = {0};
	    final int [] x = {0};
	    final int [] arriba = {1};
	    final int [] abajo = {1};
	    final boolean[][] pase = new boolean[grafica.getElementos()][grafica.getElementos()];
	    
	    grafica.paraCadaVertice((v) -> {
		    if(j[0] == media){
			x[0] = 0;
		    }
		    x[0] += 80 ;
		    int y = yarriba;
		    if(j[0] >= media)
			y = yabajo;
		    T e =  (T) v.get();
		    VerticeGrafica<Integer> vertice = grafica.vertice(e);      	
		    for(VerticeGrafica<?> vecino : vertice.vecinos()){
			int i = lista.indiceDe(vecino.get());
			pase[j[0]][i] = true;			
			int y2 = yarriba;			
			int x2 =  80 *(i +1) ;		
			if(i >= media)
			    y2 = yabajo;
			if(i >= media)
			    x2 =  80*(i-media +1 );
			int ycurva1 =altura / 2 + 5;
			//double ycurva2 =altura / 2 - 5;
			int xcurva1 = x[0] + 10;
			int xcurva2 =altura / 2 - 5;
			
			if(pase[i][j[0]]== false){
			    if(i == j[0] + 1 || i == j[0] - 1)			
				cadena += "\t<line x1='"+x[0]+"' y1='"+y+"' x2='"+x2+"' y2='"+y2+"'"+" stroke='black' stroke-width='2'/>\n";
			    else{
				if (y != y2) 
				    cadena +=" \t<line x1='"+x[0]+"' y1='"+y+"' x2='"+x2+"' y2='"+y2+"'"+" stroke='black' stroke-width='2'/>\n";
				if (y == y2){
				    if (y == yarriba){
					ycurva1 = y - arriba[0] - 50;
					xcurva1 = x[0];
					xcurva2 = x[0];
					int ps = arriba[0];
					ps +=10;
					arriba[0] = ps;
				    }
				    if(y == yabajo){
					ycurva1 = y + abajo[0] + 50;
					xcurva1 += x[0];
					xcurva2 = x2;
					int ps = arriba[0];
					ps +=10;
					arriba[0] = ps;				    				}
			    }
				cadena += "<path class='SamplePath' opacity ='0.3' d='M"+x[0]+" "+y+" C "+xcurva1+" "+ycurva1+" "+xcurva2+" "+ycurva1+" "+x2+" "+y2+" M 190 150 z' stroke='black' stroke-width='4' fill='white' />";
			    }			    
			}		      
		    }
		    cadena += "\t<circle cx='"+x[0]+"' cy='"+y+"' r='20' stroke='black' fill='white' /> \n\t<text fill='black' font-family='sans-serif' font-size='20' x='"+x[0]+"' y='"+y+"' text-anchor='middle'>"+ v.get().toString() +"</text>\n";
		    int auxAng = j[0];
		    auxAng += 1;
		    j[0] = auxAng;
		});	    
	}

	cadena += "\t</g>\n </svg>\n";
	return (searma == true)? cadena : "El numero de elementos ingresados no fue par.";
    }    
}
