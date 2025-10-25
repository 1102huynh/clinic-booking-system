package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/debug")
public class DebugController {
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    @ResponseBody
    public String debugUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Database Users Debug Info</h2>");
        sb.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
        sb.append("<tr><th>ID</th><th>Username</th><th>Enabled</th><th>Role</th><th>Password Hash (first 50 chars)</th></tr>");

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            sb.append("<tr><td colspan='5'>NO USERS FOUND IN DATABASE</td></tr>");
        }

        for (User user : users) {
            sb.append("<tr>");
            sb.append("<td>").append(user.getId()).append("</td>");
            sb.append("<td>").append(user.getUsername()).append("</td>");
            sb.append("<td>").append(user.isEnabled()).append("</td>");
            sb.append("<td>").append(user.getRole()).append("</td>");
            sb.append("<td>").append(user.getPassword().substring(0, Math.min(50, user.getPassword().length()))).append("...</td>");
            sb.append("</tr>");
        }

        sb.append("</table>");

        // Test password verification
        sb.append("<h2>Password Verification Test</h2>");
        sb.append("<p>Testing if BCrypt passwords would match...</p>");
        sb.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
        sb.append("<tr><th>Username</th><th>Test Password</th><th>Matches</th></tr>");

        for (User user : users) {
            if ("admin".equals(user.getUsername())) {
                boolean matches = passwordEncoder.matches("admin123", user.getPassword());
                sb.append("<tr><td>").append(user.getUsername()).append("</td><td>admin123</td><td><strong style='color: ")
                        .append(matches ? "green" : "red").append("'>").append(matches).append("</strong></td></tr>");
            } else if ("drsmith".equals(user.getUsername()) || "patient01".equals(user.getUsername())) {
                boolean matches = passwordEncoder.matches("password123", user.getPassword());
                sb.append("<tr><td>").append(user.getUsername()).append("</td><td>password123</td><td><strong style='color: ")
                        .append(matches ? "green" : "red").append("'>").append(matches).append("</strong></td></tr>");
            }
        }

        sb.append("</table>");

        // Instructions
        sb.append("<h2>Instructions</h2>");
        sb.append("<ol>");
        sb.append("<li>If 'NO USERS FOUND IN DATABASE' - You need to insert users into the database</li>");
        sb.append("<li>If password matches shows 'false' - The passwords are not BCrypt encoded correctly</li>");
        sb.append("<li>If 'Enabled' shows 'false' - User accounts are disabled</li>");
        sb.append("</ol>");

        return sb.toString();
    }

    @GetMapping("/generate-password")
    @ResponseBody
    public String generatePassword(String password) {
        String encoded = passwordEncoder.encode(password != null ? password : "admin123");
        return "<h2>BCrypt Password Generator</h2>" +
                "<p>Original: <strong>" + (password != null ? password : "admin123") + "</strong></p>" +
                "<p>Encoded: <strong>" + encoded + "</strong></p>" +
                "<p>Test matching: " + passwordEncoder.matches(password != null ? password : "admin123", encoded) + "</p>";
    }
}

