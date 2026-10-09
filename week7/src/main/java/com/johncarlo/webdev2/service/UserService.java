package main.java.com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public User createUser(String username, String email, java.time.LocalDateTime createdAt) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setCreatedAt(createdAt);
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public void detachUser(User user) {
        entityManager.detach(user);
    }

    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }
}
