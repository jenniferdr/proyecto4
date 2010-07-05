/**
 * Clase que implementa una Pila
 *
 * @author José A. Goncalves y Jennifer Dos Reis
 * @version 1.0
 * @since 1.6
**/
public class Pila<E>{

	private ElemPila primero=null;
	private ElemPila ultimo=null;
	private int tam = 0;

	private class ElemPila<E> {
	    public E elemento;
	    public ElemPila siguiente;

	    public ElemPila(E elem){
		this.elemento= elem;
		this.siguiente= null;
	    }

	    public void setSiguiente(ElemPila sig){
		this.siguiente= sig;
	    }
	}

	public boolean vacia(){
	    return (this.tam==0);
	}

	public void empilar(E elem){
	    ElemPila caja= new ElemPila(elem);
	    if(this.tam==0){  
	    	this.primero= caja;
		this.ultimo= caja;
	    }else{
		caja.setSiguiente(this.primero);
	    	this.primero=caja;
	    }
	    this.tam++; 
	}

	public E desempilar(){
	    if(this.tam==0)
		return null;

	    E primerElem= (E)this.primero.elemento;
	    if(this.tam==1){
	    	this.primero=null;
	    	this.ultimo=null;
	    }else{
	    	this.primero= this.primero.siguiente;
	    }
	    this.tam--;
	    return primerElem;
	} 
	 

}
