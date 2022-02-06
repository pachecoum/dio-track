package bancodigital.models;

public class PessoaFisica extends Pessoa
{
    private String cpf;

    public PessoaFisica(String nome, String nascimento, String cpf){
        super(nome, nascimento);
        this.cpf = cpf;
    }

    public String getCpf(){
        return this.cpf;
    }
}
