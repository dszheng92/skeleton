package dao;

//import com.sun.javadoc.Tag;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;


import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;


public class TaoDao {
    DSLContext dsl;

    public TaoDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }


    public int insert(String tagname, int id) {
        TagsRecord tagsRecord = dsl.insertInto(TAGS,TAGS.TAGNAME,TAGS.ID).values(tagname, id).returning(TAGS.ID).fetchOne();
        checkState(tagsRecord != null && tagsRecord.getId() != null, "Insert failed");

        return tagsRecord.getId();
    }





    public void modify(String tagname, int id) {
        List<String> tagsRecord = dsl
                .selectFrom(TAGS)
                .where(TAGS.TAGNAME.eq(tagname))
                .and(TAGS.ID.eq(id))
                .fetch(TAGS.TAGNAME);


        if (tagsRecord.isEmpty()) {
            dsl.insertInto(TAGS, TAGS.TAGNAME, TAGS.ID).values(tagname, id).execute();
        } else {
            dsl.delete(TAGS)
                    .where(TAGS.TAGNAME.eq(tagname)).and(TAGS.ID.eq(id)).execute();


        }


    }



    public List<ReceiptsRecord> jointable(String tagname) {
        List<ReceiptsRecord> receiptsRecords = dsl
                .select()
                .from(RECEIPTS)
                .join(TAGS).on(TAGS.ID.eq(RECEIPTS.ID))
                .where(TAGS.TAGNAME.eq(tagname))
                .fetchInto(RECEIPTS);

        return receiptsRecords;


    }

    public List<TagsRecord> getAllTags() {
        return dsl.selectFrom(TAGS).fetch();
    }
}
