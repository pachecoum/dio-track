
namespace DioSeriesConsole.Classes;
abstract class EntidadeBase
{
    public int Id { get; protected set; }
    public bool Excluido { get;  protected set; }

    public EntidadeBase(int id)
    {
        this.Id = id;
        this.Excluido = false;
    }

    public void Excluir() => this.Excluido = true;
    
}
