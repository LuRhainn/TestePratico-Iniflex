package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Funcionario extends Pessoa
{
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao)
    {
        super(nome, dataNascimento);
        this.funcao = funcao;
        this.salario = salario;
    }

    public BigDecimal getSalario()
    {
        return salario;
    }
    public void setSalario(BigDecimal salario)
    {
        this.salario = salario;
    }

    public String getFuncao()
    {
        return funcao;
    }
    public void setFuncao(String funcao)
    {
        this.funcao = funcao;
    }

    //Aplica aumento percentual no salario
    public void aumentarSalario(BigDecimal percentual)
    {
        BigDecimal aumento = salario.multiply(percentual);
        this.salario = salario.add(aumento).setScale(2, RoundingMode.HALF_UP);
    }

    //Formato sobrescrito no toString
    @Override
    public String toString()
    {
        return "Funcionario{nome = '" + getNome() + "', funcao = '" + funcao + "', salario = '" + salario + "'}";
    }
}