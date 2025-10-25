package huynh.tdt.clinicbookingsystem.controller;

import huynh.tdt.clinicbookingsystem.entity.User;
import huynh.tdt.clinicbookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/password-generator")
public class PasswordGeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(PasswordGeneratorController.class);

    @Autowired(required = false)
    private UserRepository userRepository;

    @GetMapping("/generate")
    @ResponseBody
    public String generatePassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        StringBuilder html = new StringBuilder();
        html.append("<h1>BCrypt Password Hash Generator</h1>");
        html.append("<p>Generated hashes for your test users:</p>");
        html.append("<table border='1' style='border-collapse: collapse; width: 100%; margin: 20px 0;'>");
        html.append("<tr><th>Password</th><th>BCrypt Hash</th><th>Verify</th></tr>");

        String[] passwords = {"admin123", "password123"};

        for (String password : passwords) {
            String hash = encoder.encode(password);
            boolean matches = encoder.matches(password, hash);

            html.append("<tr>");
            html.append("<td>").append(password).append("</td>");
            html.append("<td style='font-family: monospace; font-size: 12px; word-break: break-all;'>").append(hash).append("</td>");
            html.append("<td style='color: ").append(matches ? "green" : "red").append(";'><strong>").append(matches).append("</strong></td>");
            html.append("</tr>");
        }

        html.append("</table>");

        html.append("<h2>SQL Update Statements:</h2>");
        html.append("<pre style='background: #f0f0f0; padding: 10px; border-radius: 5px;'>");

        String admin123 = encoder.encode("admin123");
        String password123 = encoder.encode("password123");

        html.append("UPDATE clinic_db.users SET password = '").append(admin123).append("' WHERE username = 'admin';\n\n");
        html.append("UPDATE clinic_db.users SET password = '").append(password123).append("' WHERE username = 'drsmith';\n\n");
        html.append("UPDATE clinic_db.users SET password = '").append(password123).append("' WHERE username = 'patient01';\n\n");
        html.append("UPDATE clinic_db.users SET enabled = TRUE;\n\n");
        html.append("SELECT id, username, password FROM clinic_db.users;");

        html.append("</pre>");

        html.append("<h2>Or Click Below to Fix Automatically:</h2>");
        html.append("<form action='/password-generator/reset-admin' method='POST' style='margin: 20px 0;'>");
        html.append("<button type='submit' style='padding: 10px 20px; font-size: 16px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;'>");
        html.append("Fix Admin Passwords Now");
        html.append("</button>");
        html.append("</form>");

        return html.toString();
    }

    @PostMapping("/reset-admin")
    @ResponseBody
    public Map<String, Object> resetAdminPasswords() {
        Map<String, Object> response = new HashMap<>();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        try {
            if (userRepository == null) {
                response.put("success", false);
                response.put("message", "UserRepository not available");
                return response;
            }

            // Reset admin user
            var adminUser = userRepository.findByUsername("admin");
            if (adminUser.isPresent()) {
                User user = adminUser.get();
                user.setPassword(encoder.encode("admin123"));
                user.setEnabled(true);
                userRepository.save(user);
                logger.info("Reset password for admin user");
            }

            // Reset drsmith user
            var doctorUser = userRepository.findByUsername("drsmith");
            if (doctorUser.isPresent()) {
                User user = doctorUser.get();
                user.setPassword(encoder.encode("password123"));
                user.setEnabled(true);
                userRepository.save(user);
                logger.info("Reset password for drsmith user");
            }

            // Reset patient01 user
            var patientUser = userRepository.findByUsername("patient01");
            if (patientUser.isPresent()) {
                User user = patientUser.get();
                user.setPassword(encoder.encode("password123"));
                user.setEnabled(true);
                userRepository.save(user);
                logger.info("Reset password for patient01 user");
            }

            response.put("success", true);
            response.put("message", "All user passwords have been reset successfully!");
            response.put("admin_password", "admin123");
            response.put("other_password", "password123");
        } catch (Exception e) {
            logger.error("Error resetting passwords: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
        }

        return response;
    }
}
