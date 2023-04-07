package ru.mamakapa.ememeemail.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeemail.DTOs.responses.ApiErrorResponse;

@RestController
@RequestMapping("/registerChat")
public class ChatRegistrarController {

    @Operation(summary = "Register new user by chatId from tg or vk messengers specified in the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been registered"),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping("/{messengerType}/{chatId}")
    public void registerChat(@PathVariable String messengerType, @PathVariable Long chatId){

    }

    @Operation(summary = "Delete user by chatId from tg or vk messengers specified in the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been deleted"),
            @ApiResponse(responseCode = "404", description = "This user does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping("/{messengerType}/{chatId}")
    public void deleteChat(@PathVariable String messengerType, @PathVariable Long chatId){

    }
}
