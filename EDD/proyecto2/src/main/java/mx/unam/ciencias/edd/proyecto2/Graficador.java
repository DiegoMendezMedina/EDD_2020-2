package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Cola;

    /**
     * Clase Graficador.
     * Grafica: pilas, cola y listas.
     */
public class Graficador{

    private static String salida = "";
    private static Lista<String> svg = new Lista<String>();
    private static Pila<Integer> pila;
    private static Cola<Integer> cola;
    private static Lista<Integer> lista;

    /**
     * Metodo que regresa la representacion en svg de una pila.
     * @param pila que se desea graficar.
     * @return representacion en svg de la pila recibida.
     */
    public String pilaToSvg(Pila<Integer> pila){
	this.pila = pila;
	String cadena = "";
	int altura = 0;
	int ancho = 500;
	int cont = 0;
	int x = ancho / 2;
	while(!pila.esVacia()){
	    int e = pila.saca();
	    int xt = x + 30;
	    int yrect = cont * 25 + 25;	    
	    int yt = yrect + 15;
	    String aux = "\t<rect x='"+x+"' y='"+ yrect +"' height='25' width='60' stroke='black' stroke-width='2'fill='white'/>\n \t<text fill='black' font-family='sans-serif' font-size='15' x='"+xt+"' y='"+ yt +"' text-anchor='middle'>"+ e +"</text>\n";
	    svg.agrega(aux);
		cont++;
	}
	altura = 30*cont;
	cadena =  "<?xml version='1.0' encoding='UTF-8' ?> \n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
	for(String s : svg)
	    cadena += s;
	cadena += "\t</g>\n </svg>\n";
	return cadena;
    }

    /**
     * Metodo que regresa la representacion en svg de una cola.
     * @param cola que se desea graficar.
     * @return representacion en svg de la cola recibida.
     */
    public String colaToSvg(Cola<Integer> cola){
	this.cola = cola;
	String cadena = "";
	int altura = 500;
	int ancho = 0;
	int cont = 0;
	int y = altura / 2;
	while(!cola.esVacia()){
	    int e = cola.saca();
	    int yt = y + 15;
	    int xrect = cont * 60 + 25;	    
	    int xt = xrect + 17;
	    String aux = "\t<rect x='"+xrect+"' y='"+ y +"' height='25' width='60' stroke='black' stroke-width='2'fill='white'/>\n \t<text fill='black' font-family='sans-serif' font-size='15' x='"+xt+"' y='"+ yt +"' text-anchor='middle'>"+ e +"</text>\n";
	    svg.agrega(aux);
	    cont++;
	}
	ancho = 80 * cont;
	cadena =  "<?xml version='1.0' encoding='UTF-8' ?> \n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
	for(String s : svg)
	    cadena += s;
	cadena += "\t</g>\n </svg>\n";
	return cadena;
    }

    /**
     * Metodo que regresa la representacion en svg de una lista.
     * @param lista que se desea graficar.
     * @return representacion en svg de la lista recibida.
     */
    public String listaToSvg(Lista<Integer> lista){
	this.lista = lista;	
	int ancho = 80 * lista.getLongitud() + 20*lista.getLongitud();
	int altura = 500;
	String cadena = cadena =  "<?xml version='1.0' encoding='UTF-8' ?> \n <svg width='"+ancho+"' height='"+altura+"'> \n \t<g>\n";
	int cont = 0;
	int xrect = 0;
	int y = altura / 2;
	int xt = 0;
	int xx = 0;
	int yt = y + 15;
	for(int i : lista){
	    xrect += 80;
	    xx = xrect + 70;	    
	    xt = xrect + 30;
	    cadena += "\t<rect x='"+xrect+"' y='"+ y +"' height='25' width='60' stroke='black' stroke-width='2'fill='white'/>\n \t<text fill='black' font-family='sans-serif' font-size='15' x='"+xt+"' y='"+ yt +"' text-anchor='middle'>"+ i +"</text>\n";
	    if (cont + 1 < lista.getLongitud())
	    cadena +="\t<text fill='black' font-family='sans-serif' font-size='20' x='"+xx+"' y='"+yt+"' text-anchor='middle'>&#x2194</text>\n";
	    cont++;
	}
	cadena += "\t</g>\n </svg>\n";
	return cadena;
    }
}
