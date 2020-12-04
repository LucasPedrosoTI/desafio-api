package com.gft.desafioapi.repository.filter;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class ClienteFilter {

  @ApiModelProperty(value = "Nome do cliente")
  private String nome;

  @ApiModelProperty(value = "Email do cliente")
  private String email;

  @ApiModelProperty(value = "Documento do cliente")
  private String documento;

  @ApiModelProperty(value = "Data de cadastro inicial do cliente")
  private LocalDate dataCadastroDe;

  @ApiModelProperty(value = "Data de cadastro final do cliente")
  private LocalDate dataCadastroAte;

  public ClienteFilter() {
  }

  public ClienteFilter(String nome, String email, String documento, LocalDate dataCadastroDe,
      LocalDate dataCadastroAte) {
    this.nome = nome;
    this.email = email;
    this.documento = documento;
    this.dataCadastroDe = dataCadastroDe;
    this.dataCadastroAte = dataCadastroAte;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDocumento() {
    return this.documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public LocalDate getDataCadastroDe() {
    return this.dataCadastroDe;
  }

  public void setDataCadastroDe(LocalDate dataCadastroDe) {
    this.dataCadastroDe = dataCadastroDe;
  }

  public LocalDate getDataCadastroAte() {
    return this.dataCadastroAte;
  }

  public void setDataCadastroAte(LocalDate dataCadastroAte) {
    this.dataCadastroAte = dataCadastroAte;
  }

}
