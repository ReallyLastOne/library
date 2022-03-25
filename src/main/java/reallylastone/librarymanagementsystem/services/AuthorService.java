package reallylastone.librarymanagementsystem.services;

import org.apache.openjpa.lib.util.StringUtil;
import org.springframework.stereotype.Service;
import reallylastone.librarymanagementsystem.models.entities.Author;

@Service
public class AuthorService {

    public boolean isAuthorCorrect(Author author) {
        return author == null
                || StringUtil.isBlank(author.getFName())
                || StringUtil.isBlank(author.getLName());
    }
}
