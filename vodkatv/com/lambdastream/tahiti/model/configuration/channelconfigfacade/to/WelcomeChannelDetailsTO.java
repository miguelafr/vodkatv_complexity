package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.tahiti.model.configuration.welcomechannel.to.WelcomeChannelTO;

public class WelcomeChannelDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private WelcomeChannelTO welcomeChannelTO;

    private ChannelTO channelTO;

    @Deprecated
    private String channelName;

    public WelcomeChannelDetailsTO(WelcomeChannelTO welcomeChannelTO,
            ChannelTO channelTO, String channelName) {
        this.welcomeChannelTO = welcomeChannelTO;
        this.channelTO = channelTO;
        this.channelName = channelName;
    }

    public WelcomeChannelTO getWelcomeChannelTO() {
        return welcomeChannelTO;
    }

    public ChannelTO getChannelTO() {
        return channelTO;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof WelcomeChannelDetailsTO))
            return false;
        WelcomeChannelDetailsTO castOther = (WelcomeChannelDetailsTO) other;
        return new EqualsBuilder()
                .append(welcomeChannelTO, castOther.welcomeChannelTO)
                .append(channelTO, castOther.channelTO)
                .append(channelName, castOther.channelName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(welcomeChannelTO)
                .append(channelTO)
                .append(channelName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("welcomeChannelTO", welcomeChannelTO)
                .append("channelTO", channelTO)
                .append("channelName", channelName)
                .toString();
    }

}
