package smartyflip.accounting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartyflip.accounting.dao.EmailActivationRepository;
import smartyflip.accounting.model.EmailActivationKey;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailActivationRepository emailActivationRepository;

    @Override
    public String createActivationKey(Integer userId) {

        EmailActivationKey emailActivationKey = new EmailActivationKey(userId);
        emailActivationRepository.save(emailActivationKey);

        return emailActivationKey.getId().toString();
    }

    @Override
    public Boolean sendActivationEmail(Integer userId) {
        // TODO Create email sender
        String activationKey = createActivationKey(userId);


        return null;
    }
}
