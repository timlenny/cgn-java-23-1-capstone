package com.timlenny.backend.service.user;

import com.timlenny.backend.model.user.MongoUser;
import com.timlenny.backend.model.user.MongoUserDTO;
import com.timlenny.backend.repository.MongoUserRepository;
import com.timlenny.backend.service.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoUserService {
    private final MongoUserRepository mongoUserRepository;
    private final IdService idService;
    private final PasswordEncoder passwordEncoder;

    public MongoUser createNewUser(MongoUserDTO userDTO) {

        MongoUser user = new MongoUser(null, userDTO.username(), userDTO.password(), null, List.of());

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
                "BASIC",
                List.of()
        );

        MongoUser out = mongoUserRepository.save(mongoUser);

        return new MongoUser(out.id(), out.username(), null, out.role(), out.topicIds());
    }

    public MongoUser loadMongoUserFromDB(Principal principal) {
        MongoUser me = mongoUserRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return new MongoUser(
                me.id(),
                me.username(),
                null,
                me.role(),
                me.topicIds()
        );
    }

    public Optional<MongoUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return mongoUserRepository.findByUsername(userDetails.getUsername());
        } else {
            return Optional.empty();
        }
    }

    public List<String> loadTopicsFromCurrentUser() {
        Optional<MongoUser> user = getCurrentUser();
        if (user.isPresent()) {
            return user.get().topicIds();
        } else {
            throw new NoSuchElementException("Current user could not be determined. Topics could not be loaded.");
        }
    }

    public void addTopicIdToUser(String id) {
        Optional<MongoUser> user = getCurrentUser();
        if (user.isPresent()) {
            MongoUser userToUpdate = user.get();
            List<String> topicIds = userToUpdate.topicIds();
            topicIds.add(id);
            mongoUserRepository.save(new MongoUser(userToUpdate.id(), userToUpdate.username(), userToUpdate.password(), userToUpdate.role(), userToUpdate.topicIds()));
        } else {
            throw new NoSuchElementException("Current user could not be determined. Could not add topic ID.");
        }
    }

    public void removeTopicIdFromUser(String id) {
        Optional<MongoUser> user = getCurrentUser();
        if (user.isPresent()) {
            MongoUser userToUpdate = user.get();
            List<String> topicIds = userToUpdate.topicIds();
            topicIds.remove(id);
            mongoUserRepository.save(new MongoUser(userToUpdate.id(), userToUpdate.username(), userToUpdate.password(), userToUpdate.role(), userToUpdate.topicIds()));
        } else {
            throw new NoSuchElementException("Current user could not be determined. Could not add topic ID.");
        }
    }
}
