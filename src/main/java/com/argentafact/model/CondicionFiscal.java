package com.argentafact.model;

public enum CondicionFiscal {
  RESPONSABLE_INSCRIPTO("Responsable Inscripto"),
  MONOTRIBUTISTA("Monotributista"),
  EXENTO("Exento"),
  CONSUMIDOR_FINAL("Consumidor Final");

  private String descripcion;

  CondicionFiscal(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return this.descripcion;
  }
}
