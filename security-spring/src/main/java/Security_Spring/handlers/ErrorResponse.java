package Security_Spring.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int status; // Http status
    private String error; // error code
    private Instant timeStamp; // heure a laquelle l'erreur c'est produite
    private String message; // message detaille de l'erreur
    private String path; // requete qui a declanche l'erreur
}
