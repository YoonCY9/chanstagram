package CTHH.chanstagram.hashTag;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashTagRestController {

    private final HashTagService hashTagService;

    public HashTagRestController(HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

}
