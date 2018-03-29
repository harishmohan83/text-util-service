CREATE TABLE IF NOT EXISTS utils.text_util (
        text_id UUID,
        arbitrary_text TEXT,
        create_time TIMESTAMP,
        last_upd_time TIMESTAMP,
      PRIMARY KEY (text_id));