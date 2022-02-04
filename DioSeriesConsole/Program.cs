using static System.Console;
using DioSeriesConsole.Classes;
using DioSeriesConsole.Enum;

SerieRepository repositorioSeries = new SerieRepository();

string? op;
do
{
    op = GetUsuarioOpcao();
    switch (op)
    {
        case "1":
            ListarSeries();
            break;
        case "2":
            InserirSerie();
            break;
        case "3":
            AtualizarSerie();
            break;
        case "4":
            ExcluirSerie();
            break;
        case "5":
            VisualiarSerie();
            break;
        case "X":
            break;
        case "C":
            Clear();
            break;
        default:
            throw new ArgumentOutOfRangeException();
    }
} while (op != "X");
WriteLine("Good Bye!");
ReadLine();

static string? GetUsuarioOpcao()
{
    WriteLine();
    WriteLine("1 - Listar a séries");
    WriteLine("2 - Inserir uma série");
    WriteLine("3 - Atualizar uma série");
    WriteLine("4 - Excluir uma série");
    WriteLine("5 - Visulizar informações sobre uma série");
    WriteLine("C - Limpar o Console");
    WriteLine("X - Sair");
    WriteLine();
    Write("Digite uma opção: ");
    return ReadLine()?.ToUpper();
}

void ListarSeries()
{
    var series = repositorioSeries.Lista;
    if (series.Count == 0)
    {
        WriteLine();
        WriteLine("Lista de séries está vazia.");
        return;
    }

    WriteLine();
    WriteLine("MINHAS SÉRIES");
    foreach (var serie in series)
    {
        if (serie.Excluido)
            continue;

        WriteLine($"#ID {serie.Id} - {serie.Titulo}");
    }
}

void InserirSerie()
{

    WriteLine();
    WriteLine("INSERIR SÉRIE");
    foreach (int i in Enum.GetValues(typeof(Genero)))
    {
        WriteLine($"#ID {i} - {Enum.GetName(typeof(Genero), i)}");
    }

    WriteLine("Digite uma das opções acima para o gênero: ");
    int genero = int.Parse(ReadLine() ?? throw new NullReferenceException());

    WriteLine("Digite o titulo da série: ");
    string titulo = ReadLine() ?? throw new ArgumentException("Entrada inválida");

    WriteLine("Informe uma descrição para a série: ");
    string descricao = ReadLine() ?? throw new ArgumentException("Entrada inválida");

    WriteLine("Digite o ano de inicio da série: ");
    int ano = int.Parse(ReadLine() ?? throw new NullReferenceException());

    repositorioSeries.Insere(new Serie(repositorioSeries.ProximoId(), (Genero)genero, titulo, descricao, ano));
}

void AtualizarSerie()
{
    ListarSeries();
    if(repositorioSeries.Lista.Count ==  0)
        return;
    WriteLine();
    WriteLine("Informe o #ID da série que deseja atualizar: ");
    int id = int.Parse(ReadLine() ?? throw new NullReferenceException());

    var serie = repositorioSeries.RetornaPorId(id);

    WriteLine();
    WriteLine("DEIXE EM BRANCO OS CAMPOS QUE DESEJA ATUALIZAR!");
    WriteLine("ATUALIZAR SÉRIE");

    foreach (int i in Enum.GetValues(typeof(Genero)))
    {
        WriteLine($"#ID {i} - {Enum.GetName(typeof(Genero), i)}");
    }

    WriteLine($"Digite uma das opções acima para o gênero atual({serie.Genero}): ");
    string? entradaGenero = ReadLine()?.Trim();

    WriteLine($"Digite o titulo da série atual({serie.Titulo}): ");
    string? entradaTitulo = ReadLine()?.Trim();

    WriteLine($"Informe uma descrição para a série atual({serie.Descricao}): ");
    string? entradaDescricao = ReadLine()?.Trim();

    WriteLine($"Digite o ano de inicio da série atual({serie.Ano}): ");
    string? entradaAno = ReadLine()?.Trim();

    if (!string.IsNullOrEmpty(entradaGenero))
        serie.Genero = (Genero)int.Parse(entradaGenero);

    if (!string.IsNullOrEmpty(entradaTitulo))
        serie.Titulo = entradaTitulo;

    if (!string.IsNullOrEmpty(entradaDescricao))
        serie.Descricao = entradaDescricao;

    if (!string.IsNullOrEmpty(entradaGenero))
        serie.Genero = (Genero)int.Parse(entradaGenero);
}

void ExcluirSerie()
{

    ListarSeries();
    if(repositorioSeries.Lista.Count ==  0)
        return;
    WriteLine();
    WriteLine("Informe o #ID da série que deseja atualizar: ");
    int id = int.Parse(ReadLine() ?? throw new NullReferenceException());
    repositorioSeries.Exclui(id);
}


void VisualiarSerie()
{
    ListarSeries();
    if(repositorioSeries.Lista.Count ==  0)
        return;
    WriteLine();
    WriteLine("Informe o #ID da série que deseja atualizar: ");
    int id = int.Parse(ReadLine() ?? throw new NullReferenceException());

    var serie = repositorioSeries.RetornaPorId(id);

    WriteLine();
    WriteLine(serie);
    WriteLine();
}












