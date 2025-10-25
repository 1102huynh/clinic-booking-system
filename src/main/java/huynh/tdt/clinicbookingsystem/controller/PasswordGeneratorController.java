package huynh.tdt.clinicbookingsystem.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/password-generator")
public class PasswordGeneratorController {

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
        html.append("SELECT id, username, password FROM clinic_db.users;");

        html.append("</pre>");

        return html.toString();
    }
}

