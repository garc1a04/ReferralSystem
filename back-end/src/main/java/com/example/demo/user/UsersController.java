package com.example.demo.user;

import com.example.demo.referrals.ReferralsServices;
import com.example.demo.user.dto.UserResponseDTO;
import com.example.demo.user.entity.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoints para cadastro, autenticação e consulta de usuários")
public class UsersController {

    private final UsersServices usersService;
    private final ReferralsServices referralsService;

    @Autowired
    public UsersController(UsersServices usersService, ReferralsServices referralsService) {
        this.usersService = usersService;
        this.referralsService = referralsService;
    }

    @Operation(
            summary = "Registra um novo usuário",
            description = "Cadastra um novo usuário no sistema, permitindo opcionalmente vincular um código de indicação (referral)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = Users.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de cadastro inválidos",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(
            @RequestBody Users user,
            @Parameter(description = "Código de indicação (opcional)", example = "REF12345")
            @RequestParam(required = false) String ref) {
        try {
            Users response = usersService.registerUser(user, ref);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Autentica um usuário",
            description = "Valida as credenciais do usuário e retorna seus dados de sessão."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Credenciais inválidas ou erro no servidor",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Users user) {
        try {
            System.out.println(user.getEmail());
            UserResponseDTO loggedUser = usersService.loginUser(user);
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Busca detalhes do usuário por ID",
            description = "Retorna os dados do usuário informando seu ID, incluindo seus pontos de indicação."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Usuário não encontrado ou erro interno",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/{id}")
    public ResponseEntity<Object> getUser(
            @Parameter(description = "ID único do usuário", example = "1")
            @PathVariable long id) {
        try {
            Users user = usersService.getUserById(id).get();
            return new ResponseEntity<>(
                    new UserResponseDTO(user, "", referralsService.getReferralsPoints(user)),
                    HttpStatus.OK
            );
        } catch (Exception err) {
            return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}