package dao;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public void insertTag(int id, String tagName) {
        List<String> tagsRecord = dsl
                .selectFrom(TAGS)
                .where(TAGS.NAME.eq(tagName))
                .and(TAGS.RECEIPT_ID.eq(id))
                .fetch(TAGS.NAME);


        if (tagsRecord.isEmpty()) {
            dsl.insertInto(TAGS, TAGS.NAME, TAGS.RECEIPT_ID).values(tagName, id).execute();
        } else {
            dsl.delete(TAGS)
                    .where(TAGS.NAME.eq(tagName)).and(TAGS.RECEIPT_ID.eq(id)).execute();


        }
    }

    public List<Integer> getReceiptsIdFromTag(String tagName) {
        return dsl
                .selectFrom(TAGS)
                .where(TAGS.NAME.eq(tagName))
                .fetch(TAGS.RECEIPT_ID);
    }

    public List<String> tagsReceiptFromId(int receiptId) {
        return dsl
                .selectFrom(TAGS)
                .where(TAGS.RECEIPT_ID.eq(receiptId))
                .fetch(TAGS.NAME);
    }


}