public class Pruebas {
    public static void main (String[] args) {
	
	List<Clausula> clausulas = new Lista<Clausula>();
	boolean ok = clausulas.add(new Clausula(0,2));
	//ok = clausulas.add(new Clausula(0,4));
	ok = clausulas.add(new Clausula(6,2));
	ok = clausulas.add(new Clausula(6,4));
	ok = clausulas.add(new Clausula(1));
	ok = clausulas.add(new Clausula(0));
	
	try {
	    Expresion prueba = new Expresion(clausulas,8);

	    System.out.println(prueba);
	    prueba.simplificar();
	    //System.out.println(prueba.determinarSatisf());
	    System.out.println(prueba);
	} catch (ExcepcionSimboloInvalido err) {
	    System.out.println("wtf");
	}
    }
}