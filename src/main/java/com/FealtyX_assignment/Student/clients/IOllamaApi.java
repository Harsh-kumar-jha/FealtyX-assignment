package com.FealtyX_assignment.Student.clients;

import com.FealtyX_assignment.Student.dtos.OllamaRequestDTO;
import com.FealtyX_assignment.Student.dtos.OllamaResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IOllamaApi {
    @POST("/api/generate")
    Call<OllamaResponseDTO> generateText(@Body OllamaRequestDTO request);
}
