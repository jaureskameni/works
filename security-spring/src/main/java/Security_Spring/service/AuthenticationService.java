package Security_Spring.service;

import Security_Spring.payloads.request.AuthenticationRequest;
import Security_Spring.payloads.request.RegisterRequest;
import Security_Spring.payloads.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
