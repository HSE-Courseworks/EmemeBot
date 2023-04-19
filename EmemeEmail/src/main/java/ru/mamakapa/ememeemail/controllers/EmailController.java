package ru.mamakapa.ememeemail.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.mamakapa.ememeemail.DTOs.requests.AddNewEmailRequest;
import ru.mamakapa.ememeemail.DTOs.requests.DeleteEmailRequest;
import ru.mamakapa.ememeemail.DTOs.responses.AllEmailsResponse;
import ru.mamakapa.ememeemail.DTOs.responses.ApiErrorResponse;
import ru.mamakapa.ememeemail.DTOs.responses.EmailResponse;
import ru.mamakapa.ememeemail.services.ImapEmailService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/emails")
public class EmailController {

    final ImapEmailService imapEmailService;

    public EmailController(ImapEmailService imapEmailService) {
        this.imapEmailService = imapEmailService;
    }

    @Operation(summary = "Add new email to subscription of user with chatId from request parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email has been subscribed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "This user does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping()
    public EmailResponse addNewEmail(@RequestParam(required = true) long chatId,
                                     @RequestBody @Valid AddNewEmailRequest addNewLinkRequest){
        var imapEmail = imapEmailService.add(chatId,
                addNewLinkRequest.address(), addNewLinkRequest.appPassword(), addNewLinkRequest.host());
        return EmailResponse.builder()
                .address(imapEmail.getEmail())
                .appPassword(imapEmail.getAppPassword())
                .host(imapEmail.getHost())
                .build();
    }

    @Operation(summary = "Delete existing email which is subscribed by user with chatId from request parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email has been deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailResponse.class))}),
            @ApiResponse(responseCode = "404", description = "This email or user do not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    }
    )
    @DeleteMapping()
    public EmailResponse deleteEmail(@RequestParam(required = true) long chatId,
                                     @RequestBody @Valid DeleteEmailRequest deleteEmailRequest){
        var imapEmail = imapEmailService.remove(chatId, deleteEmailRequest.address());
        return EmailResponse.builder()
                .address(imapEmail.getEmail())
                .host(imapEmail.getHost())
                .appPassword(imapEmail.getAppPassword())
                .build();
    }

    @Operation(summary = "Get all emails which are subscribed by user with chatId from request parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Emails has been received",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllEmailsResponse.class))}),
            @ApiResponse(responseCode = "404", description = "This email or user do not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Inappropriate request parameters",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping()
    public AllEmailsResponse getAllUserEmails(@RequestParam(required = true) long chatId){
        var emails = imapEmailService.getAllEmailsForChatId(chatId).stream()
                .map(e -> new EmailResponse(e.getEmail(), e.getAppPassword(), e.getHost()))
                .toList();
        return new AllEmailsResponse(emails);
    }
}
