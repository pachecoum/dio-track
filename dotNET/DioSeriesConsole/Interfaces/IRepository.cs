
namespace DioSeriesConsole.Interfaces;
public interface IRepository<T>
{
    List<T> Lista { get; }

    T RetornaPorId(int id);

    void Insere(T entidade);

    void Exclui(int id);

    void Atualiza(int id, T entidade);

    int ProximoId();

}
