package com.xavier.retryable.redisretrydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailInfoEntity implements Serializable {

    /**
     * 原始的数据Id
     */
    private String id;

    /**
     * 邮件接收人
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 重试次数
     */
    private Integer cnt;

    public void increase() {
        if (Objects.isNull(cnt)) {
            cnt = 0;
        }
        cnt++;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("to", to)
                .append("subject", subject)
                .append("content", content)
                .append("cnt", cnt)
                .toString();
    }
}
