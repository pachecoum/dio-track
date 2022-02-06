using DioSeriesConsole.Enum;


namespace DioSeriesConsole.Classes;

class Serie : EntidadeBase
{
    public Genero Genero { get; set; }
    public string Titulo { get; set; }
    public string Descricao { get; set; }
    public int Ano { get; set; }

    public Serie(int id,  Genero genero, string titulo, string descricao, int ano): base(id)
    {
        this.Genero = genero;
        this.Titulo = titulo;
        this.Descricao = descricao;
        this.Ano = ano;
    }

    public override string ToString()
    {
        return $"Titulo: {this.Titulo}\nGênero: {this.Genero}\nDescrição: {this.Descricao}\nAno de Início: {this.Ano}";
    }
}
