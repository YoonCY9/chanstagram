package CTHH.chanstagram.hashTag;

import jakarta.persistence.*;

@Entity
public class HashTag {

    @Column(unique = true)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected HashTag() {

    }

    public HashTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
