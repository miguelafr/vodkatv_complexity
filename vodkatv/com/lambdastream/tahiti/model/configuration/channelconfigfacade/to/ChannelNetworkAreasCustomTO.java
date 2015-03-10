package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lambdastream.tahiti.model.configuration.networkarea.to.NetworkAreaTO;

@XmlRootElement(name="channelNetworkArea")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelNetworkAreasCustomTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tagTreeNodeId;

    private String vodkatvChannelId;

    @XmlElementWrapper(name = "networkAreas")
    @XmlElement(name = "networkArea")
    private Collection<NetworkAreaTO> networkAreas;

    public ChannelNetworkAreasCustomTO(){ ; }

    public ChannelNetworkAreasCustomTO(Long tagTreeNodeId,
            String vodkatvChannelId,
            Collection<NetworkAreaTO> networkAreas) {
        super();
        this.tagTreeNodeId = tagTreeNodeId;
        this.vodkatvChannelId = vodkatvChannelId;
        this.networkAreas = networkAreas;
    }

    public Long getTagTreeNodeId() {
        return tagTreeNodeId;
    }

    public void setTagTreeNodeId(Long tagTreeNodeId) {
        this.tagTreeNodeId = tagTreeNodeId;
    }

    public String getVodkatvChannelId() {
        return vodkatvChannelId;
    }

    public void setVodkatvChannelId(String vodkatvChannelId) {
        this.vodkatvChannelId = vodkatvChannelId;
    }

    public Collection<NetworkAreaTO> getNetworkAreas() {
        return networkAreas;
    }

    public void setNetworkAreas(Collection<NetworkAreaTO> networkAreas) {
        this.networkAreas = networkAreas;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ChannelNetworkAreasCustomTO))
            return false;
        ChannelNetworkAreasCustomTO castOther = (ChannelNetworkAreasCustomTO) other;
        return new EqualsBuilder()
        .append(tagTreeNodeId, castOther.tagTreeNodeId)
        .append(vodkatvChannelId, castOther.vodkatvChannelId)
        .append(networkAreas, castOther.networkAreas)
        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
        .append(tagTreeNodeId)
        .append(vodkatvChannelId)
        .append(networkAreas)
        .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("tagTreeNodeId", tagTreeNodeId)
        .append("vodkatvChannelId", vodkatvChannelId)
        .append("networkAreas", networkAreas)
        .toString();
    }
}
