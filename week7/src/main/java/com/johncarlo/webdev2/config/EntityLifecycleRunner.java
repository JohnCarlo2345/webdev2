package main.java.com.johncarlo.webdev2.config;

import com.yourname.webdev2.model.User;
import com.yourname.webdev2.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class EntityLifecycleRunner implements CommandLineRunner {

    private final UserService userService;

    public EntityLifecycleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n===== ENTITY LIFECYCLE DEMO =====\n");

        // 1️⃣ TRANSIENT — just created, not in DB
        System.out.println("1️⃣ TRANSIENT: Creating new User object...");
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("original@example.com");
        user.setCreatedAt(LocalDateTime.now());
        System.out.println("   → Not saved yet\n");

        // 2️⃣ MANAGED — saved to database
        System.out.println("2️⃣ MANAGED: Saving to database via Service...");
        User savedUser = userService.createUser(
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt()
        );
        System.out.println("   → Saved! ID = " + savedUser.getId() + "\n");

        // 3️⃣ DETACHED — separate from database
        System.out.println("3️⃣ DETACHED: Detaching entity...");
        userService.detachUser(savedUser);
        System.out.println("   → Detached from persistence context\n");

        // 4️⃣ MODIFY DETACHED — this change WON'T save!
        System.out.println("4️⃣ Changing email to: changed@example.com");
        savedUser.setEmail("changed@example.com");
        System.out.println("   → Changed in memory only — NOT merged back!\n");

        // 5️⃣ CHECK — what's actually in database?
        System.out.println("5️⃣ Checking database...");
        User fromDb = userService.findUserById(savedUser.getId());
        System.out.println("   → Database Email: " + fromDb.getEmail());
        System.out.println("   → Memory Email:  " + savedUser.getEmail());

        if (!fromDb.getEmail().equals(savedUser.getEmail())) {
            System.out.println("\n   ✅ SUCCESS: Detached changes were NOT persisted!");
        }
    }
}
