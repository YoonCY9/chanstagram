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

    public Long findIdByName(String name) {
        Long hashTagId = hashTagRepository.findIdByName(name).orElse(null);
        return hashTagId;
    }
}
