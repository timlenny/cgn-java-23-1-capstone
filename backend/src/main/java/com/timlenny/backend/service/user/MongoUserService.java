package com.timlenny.backend.service.user;

import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.model.user.MongoUserDTO;
import com.timlenny.backend.repository.MongoUserRepository;
import com.timlenny.backend.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MongoUserService {

    private final MongoUserRepository mongoUserRepository;
    private final IdService idService;
    private final PasswordEncoder passwordEncoder;

    public MongoUser createNewUser(MongoUserDTO userDTO) {

        MongoUser user = new MongoUser(null, userDTO.username(), userDTO.password(), null);

        if (mongoUserRepository.existsByUsername(user.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        if (user.username().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must have at least 3 characters");
        }

        if (user.password().length() < 7) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must have at least 7 characters");
        }

        MongoUser mongoUser = new MongoUser(
                idService.generateId(),
                user.username(),
                passwordEncoder.encode(user.password()),
                "BASIC"
        );

        MongoUser out = mongoUserRepository.save(mongoUser);
        return new MongoUser(out.id(), out.username(), null, out.role());
    }


}
