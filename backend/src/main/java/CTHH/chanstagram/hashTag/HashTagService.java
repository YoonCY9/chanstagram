package CTHH.chanstagram.hashTag;

import org.springframework.stereotype.Service;

@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public HashTagResponse create(String name) {
        HashTag hashTag = hashTagRepository.save(new HashTag(name));
        return new HashTagResponse(hashTag.getName(), hashTag.getId());
    }

    public Long findIdByName(String name) {
        HashTag hashTag = hashTagRepository.findByName(name).orElse(null);
        if (hashTag == null) {
            return null;
        }
        return hashTag.getId();
    }
}
