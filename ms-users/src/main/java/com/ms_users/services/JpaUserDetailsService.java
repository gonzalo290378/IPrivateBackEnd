//package com.ms_users.services;
//
//import com.ms_users.exceptions.EmailNotFoundException;
//import com.ms_users.models.entity.User;
//import com.ms_users.repositories.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class JpaUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public JpaUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Transactional(readOnly = true)
//    //ACA INGRESA POR EL POST DE ANGULAR EL USERNAME
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            throw new EmailNotFoundException(String.format("Email %s not found", email));
//        }
//        User user = userOptional.orElseThrow();
//
//        List<GrantedAuthority> authorities = user
//                .getRoles()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.getIsEnabled(),
//                true,
//                true,
//                true,
//                authorities);
//
//    }
//
//}
//
