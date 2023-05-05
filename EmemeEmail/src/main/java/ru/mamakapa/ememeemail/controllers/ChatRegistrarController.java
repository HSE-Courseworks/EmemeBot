package ru.mamakapa.ememeemail.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.DTOs.responses.ApiErrorResponse;
import ru.mamakapa.ememeemail.services.BotUserService;

@RestController
@RequestMapping("/registerChat")
public class ChatRegistrarController {

    final BotUserService botUserService;

    public ChatRegistrarController(BotUserService botUserService) {
        this.botUserService = botUserService;
    }

    @Operation(summary = "Register new user by chatId from tg or vk messengers specified in the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been registered"),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping("/{messengerType}/{chatId}")
    public void registerChat(@PathVariable MessengerType messengerType, @PathVariable Long chatId){
<<<<<<< HEAD
=======

>>>>>>> telegramBot
        botUserService.register(chatId, messengerType);
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
    public void deleteChat(@PathVariable MessengerType messengerType, @PathVariable Long chatId){
        botUserService.delete(chatId, messengerType);
<<<<<<< HEAD
=======

>>>>>>> telegramBot
    }
}
