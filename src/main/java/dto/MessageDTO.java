package dto;

import com.example.diplom.ximss.response.FolderMessage;
import com.example.diplom.ximss.response_request.Email;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private Long uid;

    public String userLogin;

    public String title;

    public String text;

    public boolean isSelected;

    public MessageDTO(final FolderMessage folderMessage) {
        this.userLogin = folderMessage.getEmail().getFrom();
        this.title = folderMessage.getEmail().getSubject();
        this.text = folderMessage.getEmail().getMime().getMessageText();
        this.uid = folderMessage.getUid();
        this.isSelected=false;
    }
}
