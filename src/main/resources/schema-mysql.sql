-- Esquema de Spring Batch

CREATE TABLE IF NOT EXISTS BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID
            BIGINT
                         NOT
                             NULL
        PRIMARY
            KEY,
    VERSION
            BIGINT,
    JOB_NAME
            VARCHAR(100) NOT NULL,
    JOB_KEY VARCHAR(32)  NOT NULL,
    constraint JOB_INST_UN unique
        (
         JOB_NAME,
         JOB_KEY
            )
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID
                 BIGINT
                             NOT
                                 NULL
        PRIMARY
            KEY,
    VERSION
                 BIGINT,
    JOB_INSTANCE_ID
                 BIGINT
                             NOT
                                 NULL,
    CREATE_TIME
                 DATETIME(6) NOT NULL,
    START_TIME   DATETIME(6) DEFAULT NULL,
    END_TIME     DATETIME(6) DEFAULT NULL,
    STATUS       VARCHAR(10),
    EXIT_CODE    VARCHAR(2500),
    EXIT_MESSAGE VARCHAR(2500),
    LAST_UPDATED DATETIME(6),
    constraint JOB_INST_EXEC_FK foreign key
        (
         JOB_INSTANCE_ID
            )
        references BATCH_JOB_INSTANCE
            (
             JOB_INSTANCE_ID
                )
) ENGINE = InnoDB;

