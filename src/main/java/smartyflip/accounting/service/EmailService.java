package smartyflip.accounting.service;

public interface EmailService {

    String createActivationKey(Integer userId);

    Boolean sendActivationEmail(Integer userId);

}
