package bancodigital.models;

public class Pessoa
{
    private String nome;
    private String nascimento;

    public Pessoa(String nome, String nascimento)
    {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public String getNome()
    {
        return this.nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public String getNascimento()
    {
        return this.nascimento;
    }

    public void setNascimento(String nascimento)
    {
        this.nascimento = nascimento;
    }


}
