package CTHH.chanstagram.hashTag;

import org.springframework.stereotype.Service;

@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }
}
