/*
 * Filename: ChannelRedefinitionTO.java
 * Created on 05-dic-2006 8:47:46
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Miguel Ángel Francisco Fernánez
 */
public class ChannelRedefinitionTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long channelRedefinitionId;

    private Long rulePropertyId;

    private String vodkatvChannelId;

    private Integer channelNumber;

    private String newIp;

    private Integer newPort;

    private boolean visible;
    
    public ChannelRedefinitionTO(Long channelRedefinitionId,
            Long rulePropertyId, String vodkatvChannelId, Integer channelNumber,
            String newIp, Integer newPort, boolean visible) {
        this.channelRedefinitionId = channelRedefinitionId;
        this.rulePropertyId = rulePropertyId;
        this.vodkatvChannelId = vodkatvChannelId;
        this.channelNumber = channelNumber;
        this.newIp = newIp;
        this.newPort = newPort;
        this.visible = visible;
    }

    public String getVodkatvChannelId() {
        return vodkatvChannelId;
    }

    public void setVodkatvChannelId(String vodkatvChannelId) {
        this.vodkatvChannelId = vodkatvChannelId;
    }

    public Integer getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(Integer channelNumber) {
        this.channelNumber = channelNumber;
    }

    public Long getChannelRedefinitionId() {
        return channelRedefinitionId;
    }

    public String getNewIp() {
        return newIp;
    }

    public Integer getNewPort() {
        return newPort;
    }

    public Long getRulePropertyId() {
        return rulePropertyId;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ChannelRedefinitionTO))
            return false;

        ChannelRedefinitionTO castOther = (ChannelRedefinitionTO) other;

        return new EqualsBuilder()
        .append(channelRedefinitionId, castOther.channelRedefinitionId)
        .append(rulePropertyId, castOther.rulePropertyId)
        .append(vodkatvChannelId, castOther.vodkatvChannelId)
        .append(channelNumber, castOther.channelNumber)
        .append(newIp, castOther.newIp)
        .append(newPort, castOther.newPort)
        .append(visible, castOther.visible)
        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        .append(channelRedefinitionId)
        .append(rulePropertyId)
        .append(vodkatvChannelId)
        .append(channelNumber)
        .append(newIp)
        .append(newPort)
        .append(visible)
        .toHashCode();
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .appendSuper(super.toString())
        .append("channelRedefinitionId", channelRedefinitionId)
        .append("rulePropertyId", rulePropertyId)
        .append("vodkatvChannelId", vodkatvChannelId)
        .append("channelNumber", channelNumber)
        .append("newIp", newIp)
        .append("newPort", newPort)
        .append("visible", visible)
        .toString();
    }
}
