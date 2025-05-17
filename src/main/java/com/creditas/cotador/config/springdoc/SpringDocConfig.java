package com.creditas.cotador.config.springdoc;

import com.creditas.cotador.api.v1.dto.CotacaoResponseDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {

    private static final String notFoundResponse = "NotFoundResponse";
    private static final String badRequestResponse = "BadRequestResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Simulador de Crédito")
                        .version("v1")
                        .description("API desenvolvida para simulação de crédito que permite aos " +
                                "usuários simular empréstimos, visualizando as condições de pagamento baseadas no valor " +
                                "solicitado, taxa de juros e prazo de pagamento.")
                        .contact(new Contact()
                                .name("Bruno Cavalcanti")
                                .email("bruno.cavalcanti@foop.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com"))
                ).servers(List.of(
                        new Server()
                                .url("https://cotador.foop.com.br/api")
                                .description("Produção"),
                        new Server()
                                .url("http://localhost:8080/api")
                                .description("Desenvolvimento"))
                        )
                .components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );

    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> erroSchema = ModelConverters.getInstance().read(CotacaoResponseDto.class);

        schemaMap.putAll(erroSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<CotacaoResponseDto>().$ref("Error")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui uma representação aceita pelo consumidor")
                .content(content));

        return apiResponseMap;
    }
}
