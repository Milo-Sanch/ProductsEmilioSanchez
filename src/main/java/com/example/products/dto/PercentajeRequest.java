package com.example.products.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PercentajeRequest  {
    @NotNull
    @Min(1)
    @Max(100)
    Double percentaje;

    public PercentajeRequest() {
    }

    public PercentajeRequest(Double percentaje) {
        this.percentaje = percentaje;
    }

    public Double getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(Double percentaje) {
        this.percentaje = percentaje;
    }
}
