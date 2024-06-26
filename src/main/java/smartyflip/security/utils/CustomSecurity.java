package smartyflip.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import smartyflip.accounting.dao.UserRepository;
import smartyflip.accounting.model.UserAccount;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CustomSecurity {

    private final UserRepository userRepository;

    public boolean hasUserAccessToUserId(String userName, Integer userId) {
        UserAccount userAccount = userRepository.findByUsernameIgnoreCase(userName).orElse(null);
        return userAccount != null && userAccount.getId().equals(userId);
    }

    public boolean isUserAdmin(Supplier<Authentication> authentication) {
        return authentication.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"));
    }
}

