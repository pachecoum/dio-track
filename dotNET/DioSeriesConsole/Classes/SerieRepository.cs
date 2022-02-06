using DioSeriesConsole.Interfaces;

namespace DioSeriesConsole.Classes;
class SerieRepository : IRepository<Serie>
{
    public List<Serie> Lista { get; } = new List<Serie>();

    public void Atualiza(int id, Serie objeto) => Lista[id] = objeto;

    public void Exclui(int id) => Lista[id].Excluir();

    public void Insere(Serie entidade) => Lista.Add(entidade);

    public int ProximoId() => Lista.Count;

    public Serie RetornaPorId(int id) => Lista[id];
}
