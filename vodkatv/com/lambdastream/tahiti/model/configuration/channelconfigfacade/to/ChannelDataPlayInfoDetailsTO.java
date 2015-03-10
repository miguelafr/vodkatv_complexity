package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelAudioPidTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelSourceURITO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelVideoPidTO;
import com.lambdastream.tahiti.model.configuration.videoservergroup.to.VideoServerGroupTO;
import com.lambdastream.tahiti.model.provisioning.devicetype.to.DeviceTypeTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelDataPlayInfoDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "id")
    private Long channelDataPlayInfoId;

    @XmlElement(name = "deviceType")
    private DeviceTypeTO deviceTypeTO;

    private String channelUrl;

    private String aclChannelId;

    private String aclChannelProtocol;

    private Integer preBufferMillis;

    private String netPVRUrl;

    private String netPVRChannelName;

    private Integer preBufferNetPVRMillis;

    @XmlElementWrapper(name = "videoPids")
    @XmlElement(name = "videoPid")
    private Collection<ChannelVideoPidTO> channelVideoPidTOs;

    @XmlElementWrapper(name = "audioPids")
    @XmlElement(name = "audioPid")
    private Collection<ChannelAudioPidTO> channelAudioPidTOs;

    @XmlElementWrapper(name = "sourceURIs")
    @XmlElement(name = "sourceURI")
    private Collection<ChannelSourceURITO> channelSourceURIs;

    private String streamName;

    @XmlElementWrapper(name = "videoServerGroupsOrigin")
    @XmlElement(name = "videoServerGroup")
    private Collection<VideoServerGroupTO> videoServerGroupsOrigin;

    @XmlElementWrapper(name = "videoServerGroupsEdge")
    @XmlElement(name = "videoServerGroup")
    private Collection<VideoServerGroupTO> videoServerGroupsEdge;

    protected ChannelDataPlayInfoDetailsTO(){};

    public ChannelDataPlayInfoDetailsTO(Long channelDataPlayInfoId,
            DeviceTypeTO deviceTypeTO, String channelUrl, String aclChannelId,
            String aclChannelProtocol, Integer preBufferMillis, String netPVRUrl,
            String netPVRChannelName, Integer preBufferNetPVRMillis,
            Collection<ChannelVideoPidTO> channelVideoPidTOs,
            Collection<ChannelAudioPidTO> channelAudioPidTOs,
            Collection<ChannelSourceURITO> channelSourceURIs, String streamName,
            Collection<VideoServerGroupTO> videoServerGroupsOrigin,
            Collection<VideoServerGroupTO> videoServerGroupsEdge){
        this.channelDataPlayInfoId = channelDataPlayInfoId;
        this.deviceTypeTO = deviceTypeTO;
        this.channelUrl = channelUrl;
        this.aclChannelId = aclChannelId;
        this.aclChannelProtocol = aclChannelProtocol;
        this.preBufferMillis = preBufferMillis;
        this.netPVRUrl = netPVRUrl;
        this.netPVRChannelName = netPVRChannelName;
        this.preBufferNetPVRMillis = preBufferNetPVRMillis;
        this.channelVideoPidTOs = channelVideoPidTOs;
        this.channelAudioPidTOs = channelAudioPidTOs;
        this.channelSourceURIs = channelSourceURIs;
        this.streamName = streamName;
        this.videoServerGroupsOrigin = videoServerGroupsOrigin;
        this.videoServerGroupsEdge = videoServerGroupsEdge;
    }

    public Long getChannelDataPlayInfoId() {
        return channelDataPlayInfoId;
    }

    public void setChannelDataPlayInfoId(Long channelDataPlayInfoId) {
        this.channelDataPlayInfoId = channelDataPlayInfoId;
    }

    public DeviceTypeTO getDeviceTypeTO() {
        return deviceTypeTO;
    }

    public void setDeviceTypeTO(DeviceTypeTO deviceTypeTO) {
        this.deviceTypeTO = deviceTypeTO;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getAclChannelId() {
        return aclChannelId;
    }

    public String getAclChannelProtocol() {
        return aclChannelProtocol;
    }

    public Integer getPreBufferMillis() {
        return preBufferMillis;
    }

    public void setPreBufferMillis(Integer preBufferMillis) {
        this.preBufferMillis = preBufferMillis;
    }

    public String getNetPVRUrl() {
        return netPVRUrl;
    }

    public void setNetPVRUrl(String netPVRUrl) {
        this.netPVRUrl = netPVRUrl;
    }

    public String getNetPVRChannelName() {
        return netPVRChannelName;
    }

    public void setNetPVRChannelName(String netPVRChannelName) {
        this.netPVRChannelName = netPVRChannelName;
    }

    public Integer getPreBufferNetPVRMillis() {
        return preBufferNetPVRMillis;
    }

    public void setPreBufferNetPVRMillis(Integer preBufferNetPVRMillis) {
        this.preBufferNetPVRMillis = preBufferNetPVRMillis;
    }

    public Collection<ChannelVideoPidTO> getChannelVideoPidTOs() {
        return channelVideoPidTOs;
    }

    public void setChannelVideoPidTOs(
            Collection<ChannelVideoPidTO> channelVideoPidTOs) {
        this.channelVideoPidTOs = channelVideoPidTOs;
    }

    public Collection<ChannelAudioPidTO> getChannelAudioPidTOs() {
        return channelAudioPidTOs;
    }

    public void setChannelAudioPidTOs(
            Collection<ChannelAudioPidTO> channelAudioPidTOs) {
        this.channelAudioPidTOs = channelAudioPidTOs;
    }

    public Collection<ChannelSourceURITO> getChannelSourceURIs() {
        return channelSourceURIs;
    }

    public void setChannelSourceURIs(Collection<ChannelSourceURITO> channelSourceURIs) {
        this.channelSourceURIs = channelSourceURIs;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Collection<VideoServerGroupTO> getVideoServerGroupsOrigin() {
        return videoServerGroupsOrigin;
    }

    public void setVideoServerGroupsOrigin(
            Collection<VideoServerGroupTO> videoServerGroupsOrigin) {
        this.videoServerGroupsOrigin = videoServerGroupsOrigin;
    }

    public Collection<VideoServerGroupTO> getVideoServerGroupsEdge() {
        return videoServerGroupsEdge;
    }

    public void setVideoServerGroupsEdge(
            Collection<VideoServerGroupTO> videoServerGroupsEdge) {
        this.videoServerGroupsEdge = videoServerGroupsEdge;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ChannelDataPlayInfoDetailsTO))
            return false;
        ChannelDataPlayInfoDetailsTO castOther = (ChannelDataPlayInfoDetailsTO) other;
        return new EqualsBuilder()
                .append(deviceTypeTO, castOther.deviceTypeTO)
                .append(channelUrl, castOther.channelUrl)
                .append(aclChannelId, castOther.aclChannelId)
                .append(aclChannelProtocol, castOther.aclChannelProtocol)
                .append(preBufferMillis, castOther.preBufferMillis)
                .append(netPVRUrl, castOther.netPVRUrl)
                .append(netPVRChannelName, castOther.netPVRChannelName)
                .append(preBufferNetPVRMillis, castOther.preBufferNetPVRMillis)
                .append(channelVideoPidTOs, castOther.channelVideoPidTOs)
                .append(channelSourceURIs, castOther.channelSourceURIs)
                .append(streamName, castOther.streamName)
                .append(videoServerGroupsOrigin, castOther.videoServerGroupsOrigin)
                .append(videoServerGroupsEdge, castOther.videoServerGroupsEdge)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(deviceTypeTO)
                .append(channelUrl)
                .append(aclChannelId)
                .append(aclChannelProtocol)
                .append(preBufferMillis)
                .append(netPVRUrl)
                .append(netPVRChannelName)
                .append(preBufferNetPVRMillis)
                .append(channelVideoPidTOs)
                .append(channelAudioPidTOs)
                .append(channelSourceURIs)
                .append(streamName)
                .append(videoServerGroupsOrigin)
                .append(videoServerGroupsEdge)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .appendSuper(super.toString())
                .append("deviceTypeTO", deviceTypeTO)
                .append("channelUrl", channelUrl)
                .append("aclChannelId", aclChannelId)
                .append("aclChannelProtocol", aclChannelProtocol)
                .append("preBufferMillis", preBufferMillis)
                .append("netPVRUrl", netPVRUrl)
                .append("netPVRChannelName", netPVRChannelName)
                .append("preBufferNetPVRMillis", preBufferNetPVRMillis)
                .append("channelVideoPidTOs", channelVideoPidTOs)
                .append("channelAudioPidTOs", channelAudioPidTOs)
                .append("channelSourceURIs", channelSourceURIs)
                .append("streamName", streamName)
                .append("videoServerGroupsOrigin", videoServerGroupsOrigin)
                .append("videoServerGroupsEdge", videoServerGroupsEdge)
                .toString();
    }

}
