package bancodigital.models;


public abstract class Conta 
{
    private final int numero;
    private final int agencia;
    protected double saldo;
    private final Pessoa cliente;

    public Conta(int numero, int agencia, double depositoInicial, Pessoa cliente){
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = depositoInicial;
        this.cliente = cliente;
    }

    public int getNumero(){
        return this.numero;
    }

    public int getAgencia(){
        return this.agencia;
    }

    public double getSaldo(){
        return this.saldo;
    }
    
    public void deposito(double valor){
        this.saldo += valor;
    } 
     
    public void pix(Double valor, Conta conta) throws Exception {
        if(valor > saldo){
            throw new Exception("Valor do pix e maior que saldo em conta.");
        }
        this.saldo -= valor;
        conta.deposito(valor);
    }

    public Pessoa getCliente(){
        return this.cliente;
    }
}
