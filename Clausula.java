/**
 * Esta clase representa a una disyunción entre dos variables
 * (una cláusula en 2CNF) o a una disyunción entre una variable y false
 * (una cláusula unitaria). Las variables se representan como un natural
 */
public class Clausula {
    //Representación de los símbolos de esta clausula
    int simbolo1;
    int simbolo2;

    /**
     * Devuelve una cláusula unitaria que contiene al símbolo especificado.
     * @param simbolo: Un numero natural que representa a una variable en esta disyunción.
     *                Tiene que ser un número mayor o igual a 0.
     */
    public Clausula (int simbolo) {
	// Los constructores no pueden devolver null deberiamos lanzar una excepcion o dejar la precondicion
	/*if (simbolo<0) {
	    return null;
	}*/
	this.simbolo1 = simbolo;
	this.simbolo2 = -1;
    }

    /**
     * Devuelve una cláusula con dos variables para usarse en expresiones 2CNF
     * @param simbolo1 Un natural que representa a una variable en este disyunción.
     *                debe ser un número mayor o igual a 0.
     * @param simbolo2 Un natural que representa a una variable en este disyunción.
     *                debe ser un número mayor o igual a 0.
     * Devuelve null si alguno de los simbolos es menor a cero.
     */
    public Clausula (int simbolo1, int simbolo2) {
	/*if (simbolo1<0 || simbolo2<0) {
	    return null;
	}*/
	this.simbolo1 = simbolo1;
	this.simbolo2 = simbolo2;
    }

    /**
     * Devuelve true si esta cláusula es unitaria, false si tiene dos variables
     */
    public boolean esUnitaria() {
	return simbolo2 == -1;
    }
    
    /**
     * Devuelve una copia de esta cláusula
     */
    public Clausula clone() {
	if (this.esUnitaria()) {
	    return new Clausula(this.simbolo1);
	} else {
	    return new Clausula(this.simbolo1,this.simbolo2);
	}
    }

    public int getSimbolo1() {
	return simbolo1;
    }

    public int getSimbolo2() {
	return simbolo2;
    }

    public String toString() {
	String salida = simbolo1 + " v " + simbolo2;
	return salida;
    }
}
