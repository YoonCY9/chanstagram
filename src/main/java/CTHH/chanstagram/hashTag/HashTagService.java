package CTHH.chanstagram.hashTag;

import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public void create(String name) {
        hashTagRepository.save(new HashTag(name));
    }

    public boolean existsHashTag(String name) {
        hashTagRepository.findIdByName(name).orElse(null);
        return true;
    }
}
