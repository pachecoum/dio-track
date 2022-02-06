package bancodigital.models;


public class ContaPoupanca extends Conta
{
    public ContaPoupanca(int numero, int agencia, double depositoInicial, Pessoa cliente){
        super(numero, agencia, depositoInicial, cliente);
    }

    public void rende(){
        this.saldo *= 1.05;
    }
}
