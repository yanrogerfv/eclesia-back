package imdl.eclesia.service.utils.mail;

import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public final class SimpleMailMessageBuilder {

    private SimpleMailMessageBuilder() {
    }

    public static Builder from(String from) {
        return new Builder().from(from);
    }

    public static Builder to(String... to) {
        return new Builder().to(to);
    }

    public static Builder cc(String... cc) {
        return new Builder().cc(cc);
    }

    public static Builder bcc(String... bcc) {
        return new Builder().bcc(bcc);
    }

    public static Builder subject(String subject) {
        return new Builder().subject(subject);
    }

    public static Builder text(String text) {
        return new Builder().text(text);
    }

    public static Builder replyTo(String replyTo) {
        return new Builder().replyTo(replyTo);
    }

    public static Builder sentDate(Date sentDate) {
        return new Builder().sentDate(sentDate);
    }

    public static final class Builder {
        private String from;
        private String replyTo;
        private String[] to;
        private String[] cc;
        private String[] bcc;
        private String subject;
        private String text;
        private Date sentDate;

        private Builder() {
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder replyTo(String replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        public Builder to(String... to) {
            this.to = copyOrNull(to);
            return this;
        }

        public Builder cc(String... cc) {
            this.cc = copyOrNull(cc);
            return this;
        }

        public Builder bcc(String... bcc) {
            this.bcc = copyOrNull(bcc);
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder sentDate(Date sentDate) {
            this.sentDate = sentDate == null ? null : new Date(sentDate.getTime());
            return this;
        }

        public SimpleMailMessage build() {
            SimpleMailMessage msg = new SimpleMailMessage();
            if (from != null) msg.setFrom(from);
            if (replyTo != null) msg.setReplyTo(replyTo);
            if (to != null && to.length > 0) msg.setTo(Arrays.copyOf(to, to.length));
            if (cc != null && cc.length > 0) msg.setCc(Arrays.copyOf(cc, cc.length));
            if (bcc != null && bcc.length > 0) msg.setBcc(Arrays.copyOf(bcc, bcc.length));
            if (subject != null) msg.setSubject(subject);
            if (text != null) msg.setText(text);
            if (sentDate != null) msg.setSentDate(new Date(sentDate.getTime()));
            return msg;
        }

        private static String[] copyOrNull(String[] arr) {
            if (arr == null) return null;
            return Arrays.stream(arr)
                    .filter(Objects::nonNull)
                    .toArray(String[]::new);
        }
    }
}
