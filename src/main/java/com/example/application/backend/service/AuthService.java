//package com.example.application.backend.service;
//import com.example.application.backend.entity.Role;
//import com.example.application.backend.entity.Member;
//import com.example.application.backend.repository.UserRepository;
//import com.example.application.views.list.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.router.RouteConfiguration;
//import com.vaadin.flow.server.VaadinSession;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class AuthService {
//
//
//
//    private final UserRepository userRepository;
////    private final MailSender mailSender;
//
//
//    public AuthService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
////    public void authenticate(String username, String password) throws AuthException {
////        Member member = userRepository.getByUsername(username);
////        if (member != null && member.checkPassword(password)) {
////            VaadinSession.getCurrent().setAttribute(Member.class, member);
////            createRoutes(member.getRole());
////        } else {
////            throw new AuthException();
////        }
////    }
//
////    private void createRoutes(Role role) {
////        getAuthorizedRoutes(role).stream()
////                .forEach(route ->
////                        RouteConfiguration.forSessionScope().setRoute(route.route, route.view));
////    }
//
////    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
////        var routes = new ArrayList<AuthorizedRoute>();
////
//////        if (role.equals(Role.USER)) {
//////            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
//////            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
////
////        if (role.equals(Role.ADMIN)) {
////            routes.add(new AuthorizedRoute("admin", "Admin", AdminView.class));
////            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
////        }
////
////        return routes;
////    }
//
////    public void register(String name, String password) {
////        userRepository.save(new Member(name, password, Role.ADMIN));
////    }
//
////    public void register(String email, String password) {
////        User user = userRepository.save(new User(email, password, Role.USER));
////        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
////        SimpleMailMessage message = new SimpleMailMessage();
////        message.setFrom("noreply@example.com");
////        message.setSubject("Confirmation email");
////        message.setText(text);
////        message.setTo(email);
////        mailSender.send(message);
//    }
//
////    public void activate(String activationCode) throws AuthException {
////        User user = userRepository.getByActivationCode(activationCode);
////        if (user != null) {
////            user.setActive(true);
////            userRepository.save(user);
////        } else {
////            throw new AuthException();
////        }
////    }
