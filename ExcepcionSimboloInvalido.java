public class ExcepcionSimboloInvalido extends Exception {
    int simbolo;
    Clausula clausula;

    public ExcepcionSimboloInvalido (int simboloErroneo, Clausula clausula) {
	this.simbolo = simboloErroneo;
	this.clausula = clausula;
    }

    public String getErMessage() {
	return "Simbolo '"+simbolo+"' inválido en la cláusula '"+clausula.toString()+"'";
    }
}