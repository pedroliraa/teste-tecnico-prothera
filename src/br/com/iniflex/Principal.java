package br.com.iniflex;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"),
                "Operador"
        ));

        funcionarios.add(new Funcionario(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"
        ));

        funcionarios.add(new Funcionario(
                "Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador"
        ));

        funcionarios.add(new Funcionario(
                "Miguel",
                LocalDate.of(1988, 10, 14),
                new BigDecimal("19119.88"),
                "Diretor"
        ));

        funcionarios.add(new Funcionario(
                "Alice",
                LocalDate.of(1995, 1, 5),
                new BigDecimal("2234.68"),
                "Recepcionista"
        ));

        funcionarios.add(new Funcionario(
                "Heitor",
                LocalDate.of(1999, 11, 19),
                new BigDecimal("1582.72"),
                "Operador"
        ));

        funcionarios.add(new Funcionario(
                "Arthur",
                LocalDate.of(1993, 3, 31),
                new BigDecimal("4071.84"),
                "Contador"
        ));

        funcionarios.add(new Funcionario(
                "Laura",
                LocalDate.of(1994, 7, 8),
                new BigDecimal("3017.45"),
                "Gerente"
        ));

        funcionarios.add(new Funcionario(
                "Heloísa",
                LocalDate.of(2003, 5, 24),
                new BigDecimal("1606.85"),
                "Eletricista"
        ));

        funcionarios.add(new Funcionario(
                "Helena",
                LocalDate.of(1996, 9, 2),
                new BigDecimal("2799.93"),
                "Gerente"
        ));

     // 3.2 - Remover João
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 - Imprimir funcionários
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoSalario = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
        formatoSalario.setMinimumFractionDigits(2);
        formatoSalario.setMaximumFractionDigits(2);

        System.out.println("FUNCIONÁRIOS:");
        for (Funcionario f : funcionarios) {
            System.out.println(
                    "Nome: " + f.getNome()
                    + " | Nascimento: " + f.getDataNascimento().format(formatoData)
                    + " | Salário: R$ " + formatoSalario.format(f.getSalario())
                    + " | Função: " + f.getFuncao()
            );
        }

        // 3.4 - Aumentar salário em 10%
        for (Funcionario f : funcionarios) {
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }

        // 3.5 - Agrupar por função
        Map<String, List<Funcionario>> funcionariosPorFuncao =
                funcionarios.stream()
                        .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir agrupados por função
        System.out.println("\nFUNCIONÁRIOS POR FUNÇÃO:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println(entry.getKey() + ":");

            for (Funcionario f : entry.getValue()) {
                System.out.println("  " + f.getNome());
            }
        }

        // 3.8 - Aniversariantes de outubro e dezembro
        System.out.println("\nANIVERSARIANTES DE OUTUBRO E DEZEMBRO:");

        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();

            if (mes == 10 || mes == 12) {
                System.out.println(f.getNome() + " - "
                        + f.getDataNascimento().format(formatoData));
            }
        }

        // 3.9 - Funcionário mais velho
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(
                    maisVelho.getDataNascimento(),
                    LocalDate.now()
            ).getYears();

            System.out.println("\nFUNCIONÁRIO MAIS VELHO:");
            System.out.println(maisVelho.getNome() + " - " + idade + " anos");
        }

        // 3.10 - Funcionários em ordem alfabética
        System.out.println("\nFUNCIONÁRIOS EM ORDEM ALFABÉTICA:");

        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 - Total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("\nTOTAL DOS SALÁRIOS:");
        System.out.println("R$ " + formatoSalario.format(totalSalarios));

        // 3.12 - Quantidade de salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println("\nQUANTIDADE DE SALÁRIOS MÍNIMOS:");

        for (Funcionario f : funcionarios) {
            BigDecimal quantidade = f.getSalario()
                    .divide(salarioMinimo, 2, java.math.RoundingMode.HALF_UP);

            System.out.println(
                    f.getNome() + " - " + quantidade.toString().replace(".", ",") + " salários mínimos"
            );
        }
    }
}