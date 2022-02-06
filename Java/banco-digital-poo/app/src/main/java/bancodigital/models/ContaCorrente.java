package bancodigital.models;


public class ContaCorrente extends Conta
{
    public ContaCorrente(int numero, int agencia, double depositoInicial, Pessoa cliente){
        super(numero, agencia, depositoInicial, cliente);
    }

    public void taxtManutenca(){
        this.saldo *= 0.99;
    }
}
