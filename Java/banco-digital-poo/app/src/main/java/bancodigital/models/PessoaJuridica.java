package bancodigital.models;

public class PessoaJuridica extends Pessoa
{
    private String cnpj;

    public PessoaJuridica(String nome, String nascimento, String cnpj){
        super(nome, nascimento);
        this.cnpj = cnpj;
    }

    public String getCnpj(){
        return this.cnpj;
    }
}
