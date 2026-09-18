package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal
{
    private static  final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat FORMATO_MOEDA = criarFormatoMoeda();
    private  static  final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args)
    {
        List<Funcionario> funcionarios = inserirFuncionarios();

        removerFuncionarioPorNome(funcionarios, "João");

        System.out.println("-> 3.3 - Lista Funcioários ");
        imprimirFuncionarios(funcionarios);

        aumentarSalarios(funcionarios, new BigDecimal("0.10"));
        System.out.println("-> 3.4 - Salário após aumento de 10% ");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> porFuncao = agruparPorFuncao(funcionarios);
        System.out.println("-> 3.5/3.6 - Agrupados por função ");
        imprimirAgrupadosPorFuncao(porFuncao);

        System.out.println("-> 3.8 - Aniversariantes de Outubro e Dezembro ");
        imprimirAniversariantes(funcionarios, 10, 12);

        System.out.println("-> 3.9 - Funcionário mais velho ");
        imprimirFuncionarioMaisVelho(funcionarios);

        System.out.println("-> 3.10 - Funcionário por ordem alfabética ");
        imprimirOrdemAlfabetica(funcionarios);

        System.out.println("-> 3.11 - Salário total dos funcionários ");
        imprimirTotalSalarios(funcionarios);

        System.out.println("-> 3.12 - Salário mínimos por funcionários ");
        imprimirSalarioMinimo(funcionarios);
    }

    //Lista dos funcioanrios
    private static List<Funcionario> inserirFuncionarios()
    {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    //Metodo do foramto do Real(Moeda)
    private static NumberFormat criarFormatoMoeda()
    {
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        return formato;
    }

    //Metodo que remove funcionario pelo nome
    private static void removerFuncionarioPorNome(List<Funcionario> funcionarios, String nome)
    {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    //Imprimir lista de funcionarios
    private static void imprimirFuncionarios(List<Funcionario> funcionarios)
    {
        for(Funcionario f : funcionarios)
        {
            System.out.printf("Nome: %-10s Nascimento: %-10s Salário: R$ %-10s Função: %s%n ", f.getNome(), f.getDataNascimento().format(FORMATO_DATA), FORMATO_MOEDA.format(f.getSalario()), f.getFuncao());
        }
    }

    //Aumento com porcentagem do salario
    private  static void aumentarSalarios(List<Funcionario> funcionarios, BigDecimal percentual)
    {
        funcionarios.forEach(f -> f.aumentarSalario(percentual));
    }

    //Agrupa no MAP por Funçao
    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios)
    {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));
    }


    //Imprimir MAP por Funcao
    private static void imprimirAgrupadosPorFuncao(Map<String, List<Funcionario>> porFuncao)
    {
        porFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });
    }

    //Imprimir Aniversariantes
    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses)
    {
        DateTimeFormatter formatoDiaMes = DateTimeFormatter.ofPattern("dd/MM");
        funcionarios.stream().filter(f -> mesEstaNaLista(f.getDataNascimento().getMonthValue(), meses)).forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(formatoDiaMes)));
    }

    //Verifica mes na lista
    private static boolean mesEstaNaLista(int mes, int[] meses)
    {
        for(int m : meses)
        {
            if(m == mes)
            {
                return  true;
            }
        }
        return false;
    }

    //imprimir funcionario mais velho
    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios)
    {
        funcionarios.stream().min(Comparator.comparing(Pessoa::getDataNascimento)).ifPresent(f -> System.out.println("Nome: " + f.getNome() + " | Idade: " + f.getIdade() + " anos"));
    }

    //Imprimir em ordem alfabetica
    private static void imprimirOrdemAlfabetica(List<Funcionario> funcionarios)
    {
        funcionarios.stream().sorted(Comparator.comparing(Pessoa::getNome)).forEach(f -> System.out.println("Nome: " + f.getNome() + " | Nascimento: " + f.getDataNascimento().format(FORMATO_DATA) + " | Salário: R$ " + FORMATO_MOEDA.format(f.getSalario()) + " | Função: " + f.getFuncao()));
    }

    //Imprimir salarios totais
    private static void imprimirTotalSalarios(List<Funcionario> funcionarios)
    {
        BigDecimal total = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: R$ " + FORMATO_MOEDA.format(total));
    }

    //Salario minimo por funcionario
    private static void imprimirSalarioMinimo(List<Funcionario> funcionarios)
    {
        funcionarios.forEach(f -> {
            BigDecimal quantidade = f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + ": " + FORMATO_MOEDA.format(quantidade) + " salários mínimos");
        });
    }

}
