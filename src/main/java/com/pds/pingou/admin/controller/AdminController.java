package com.pds.pingou.admin.controller;

import com.pds.pingou.admin.service.AdminService;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.findAll();
    }

    @GetMapping("/users/search")
    public ResponseEntity<User> getByEmail(@RequestParam String email) {
        return adminService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable Long id, @RequestParam String role) {
        try {
            UserRole newRole = UserRole.valueOf(role);
            adminService.changeUserRole(id, newRole);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}