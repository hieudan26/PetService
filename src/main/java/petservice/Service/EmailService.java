package petservice.Service;

import org.springframework.stereotype.Component;
import petservice.model.Entity.UserEntity;

@Component
public interface EmailService {
    public void sendActiveMessage(UserEntity user);
    public void sendForgetPasswordMessage(UserEntity user,String newpassword);

    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment);
}
