package CTHH.chanstagram.post.postHashTag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostHashTag is a Querydsl query type for PostHashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostHashTag extends EntityPathBase<PostHashTag> {

    private static final long serialVersionUID = -1155458046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostHashTag postHashTag = new QPostHashTag("postHashTag");

    public final CTHH.chanstagram.hashTag.QHashTag hashTag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final CTHH.chanstagram.post.QPost post;

    public QPostHashTag(String variable) {
        this(PostHashTag.class, forVariable(variable), INITS);
    }

    public QPostHashTag(Path<? extends PostHashTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostHashTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostHashTag(PathMetadata metadata, PathInits inits) {
        this(PostHashTag.class, metadata, inits);
    }

    public QPostHashTag(Class<? extends PostHashTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashTag = inits.isInitialized("hashTag") ? new CTHH.chanstagram.hashTag.QHashTag(forProperty("hashTag")) : null;
        this.post = inits.isInitialized("post") ? new CTHH.chanstagram.post.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

