package sn.root.backend_service_mongp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.root.backend_service_mongp.entities.User;
import sn.root.backend_service_mongp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Gestion des users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Récupération des utilisateurs", description = "Récupération de la liste des utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<List<User>> index(){
        List<User> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupération d'un utilisateur", description = "Récupération des détails d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<Optional<User>> show(@PathVariable("id") Long id){
        Optional<User> user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Création d'un utilisateur", description = "Création d'un utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<User> create(@RequestBody User user){
        User userCreated = userService.saveUser(user);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Suppression d'un utilisateur", description = "Suppression d'un utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update d'un utilisateur", description = "Update d'un utilisateur dans le système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Error Server")
    })
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user){
        Optional<User> userById = userService.getUserById(id);
        if (userById.isPresent())
        {
            User userToUpdated = userById.get();
            userToUpdated.setEmail(user.getEmail());
            userToUpdated.setPassword(user.getPassword());
            userToUpdated.setRole(user.getRole());
            User userUpdated = userService.saveUser(userToUpdated);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        }else {
            throw new RuntimeException("Error id:" + id + "not found");
        }

    }

}
