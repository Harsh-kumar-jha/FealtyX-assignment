package com.FealtyX_assignment.Student.services.impl;

import com.FealtyX_assignment.Student.clients.IOllamaApi;
import com.FealtyX_assignment.Student.dtos.OllamaRequestDTO;
import com.FealtyX_assignment.Student.dtos.OllamaResponseDTO;
import com.FealtyX_assignment.Student.dtos.StudentResponseDTO;
import com.FealtyX_assignment.Student.exceptions.OllamaServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

import static com.FealtyX_assignment.Student.utils.Constants.MODEL_NAME;
import static com.FealtyX_assignment.Student.utils.HelperMethods.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class OllamaService {

    private final IOllamaApi ollamaApi;

    /**
     * Generates a detailed summary for a given student by interacting with the Ollama API.
     *
     * @param student the `StudentResponseDTO` object containing the student's details
     * @return a `String` containing the generated summary for the student
     * @throws OllamaServiceException if the summary generation fails due to an API error or IO exception
     */
    public String generateStudentSummary(StudentResponseDTO student) {
        log.info("Generating summary for student with ID: {}", student.getId());
        try {
            String prompt = buildPrompt(student);
            log.debug("Generated prompt for student {}: {}", student.getId(), prompt);
            
            OllamaRequestDTO request = OllamaRequestDTO.builder()
                .model(MODEL_NAME)
                .prompt(prompt)
                .stream(false)
                .options(OllamaRequestDTO.Options.builder()
                    .temperature(0.7)
                    .topK(50)
                    .topP(0.7)
                    .build())
                .build();
            log.debug("Sending request to Ollama API for student {}: {}", student.getId(), request);

            Response<OllamaResponseDTO> response = ollamaApi.generateText(request).execute();
            log.debug("Received response from Ollama API for student {}: {}", student.getId(), response.isSuccessful());

            if (!response.isSuccessful() || isEmpty(response.body())) {
                String errorMessage = !isEmpty(response.errorBody()) ?
                    response.errorBody().string() : 
                    "Unknown error occurred";
                log.error("Failed to generate summary for student {}: {}", student.getId(), errorMessage);
                throw new OllamaServiceException("Failed to generate summary: " + errorMessage);
            }

            OllamaResponseDTO responseBody = response.body();
            if (isEmpty(responseBody.getResponse())) {
                log.error("Empty response from Ollama API for student {}", student.getId());
                throw new OllamaServiceException("Failed to generate summary: No response from Ollama API");
            }

            log.info("Successfully generated summary for student {}", student.getId());
            log.debug("Generated summary for student {}: {}", student.getId(), responseBody.getResponse());
            return responseBody.getResponse();
        } catch (IOException e) {
            log.error("IO Exception while generating summary for student {}: {}", student.getId(), e.getMessage(), e);
            throw new OllamaServiceException("Failed to generate summary: " + e.getMessage());
        }
    }

    /**
     * Builds a prompt string for generating a detailed summary of a student.
     *
     * @param student the `StudentResponseDTO` object containing the student's details
     * @return a `String` representing the formatted prompt to be used for summary generation
     */
    private String buildPrompt(StudentResponseDTO student) {
        return String.format(
            "You are a professional student information system. Create a detailed summary for the following student.%n%n" +
            "Student Information:%n" +
            "- Name: %s%n" +
            "- Age: %d%n" +
            "- Email: %s%n" +
            "- Enrolled Since: %s%n%n" +
            "Instructions:%n" +
            "1. Start with a professional introduction%n" +
            "2. Mention their enrollment duration (calculate time since enrollment)%n" +
            "3. Include their contact information%n" +
            "4. Add any relevant academic context based on their age%n%n" +
            "Format the response in a professional, paragraph-style format. " +
            "Keep it concise but informative. " +
            "Do not include the bullet points in the response. " +
            "Make it read naturally.%n%n" +
            "Response should be in third person perspective.",
            student.getName(),
            student.getAge(),
            student.getEmail(),
            student.getCreatedAt()
        );
    }
} 