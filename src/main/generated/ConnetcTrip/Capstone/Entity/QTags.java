package ConnetcTrip.Capstone.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTags is a Querydsl query type for Tags
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTags extends EntityPathBase<Tags> {

    private static final long serialVersionUID = -1032614390L;

    public static final QTags tags = new QTags("tags");

    public final SetPath<PostTag, QPostTag> postTag = this.<PostTag, QPostTag>createSet("postTag", PostTag.class, QPostTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public final StringPath tagName = createString("tagName");

    public QTags(String variable) {
        super(Tags.class, forVariable(variable));
    }

    public QTags(Path<? extends Tags> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTags(PathMetadata metadata) {
        super(Tags.class, metadata);
    }

}

