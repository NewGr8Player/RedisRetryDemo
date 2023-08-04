package com.xavier.retryable.redisretrydemo.entity;

import com.alibaba.fastjson.JSON;
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
public class MailInfoEntity implements Serializable {

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
     * 重试备注
     */
    private String retryComment;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("to", to)
                .append("subject", subject)
                .append("content", content)
                .append("retryComment", retryComment)
                .toString();
    }
}
