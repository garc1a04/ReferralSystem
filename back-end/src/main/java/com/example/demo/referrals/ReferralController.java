package com.example.demo.referrals;

import com.example.demo.user.UsersServices;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ref")
@Tag(name = "Indicações (Referrals)", description = "Endpoints para consulta de indicações entre usuários")
public class ReferralController {

	private final UsersServices usersService;
	private final ReferralsServices referralsService;

	@Autowired
	public ReferralController(UsersServices usersService, ReferralsServices referralsService) {
		this.usersService = usersService;
		this.referralsService = referralsService;
	}

	@Operation(
			summary = "Lista as indicações de um usuário",
			description = "Retorna a lista de usuários ou pontos de indicação vinculados ao ID do usuário informado."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Lista de indicações retornada com sucesso"
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro ao buscar indicações do usuário",
					content = @Content(schema = @Schema(implementation = String.class))
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUsersReferr(
			@Parameter(description = "ID do usuário indicador", example = "1")
			@PathVariable Long id) {
		try {
			Users user = new Users();
			user.setIdUser(id);
			return new ResponseEntity<>(referralsService.getReferralsByUser(user), HttpStatus.OK);
		} catch (Exception err) {
			return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}