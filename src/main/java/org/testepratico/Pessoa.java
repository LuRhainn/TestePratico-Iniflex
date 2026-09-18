package org.testepratico;

import java.time.LocalDate;
import java.time.Period;

public class Pessoa
{
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento)
    {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public LocalDate getDataNascimento()
    {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }

    //Pega a idade da pessoa usando data de nascimento e data atual do computador.
    public int getIdade()
    {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}