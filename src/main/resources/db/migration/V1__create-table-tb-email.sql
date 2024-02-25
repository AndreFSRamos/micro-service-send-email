CREATE TABLE tb_email
(
    email_id bigint NOT NULL AUTO_INCREMENT,
    owner_ref       VARCHAR(255) NOT NULL,
    email_from      VARCHAR(255) NOT NULL,
    email_to        VARCHAR(255) NOT NULL,
    subject         VARCHAR(255) NOT NULL,
    text            TEXT NULL,
    send_date_email DATETIME     NOT NULL,
    origin          VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    status_email    ENUM('PROCESSING', 'SENT', 'ERROR') NOT NULL,
    PRIMARY KEY (email_id)
);