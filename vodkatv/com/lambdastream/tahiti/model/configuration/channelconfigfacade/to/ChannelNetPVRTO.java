package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRPauseSupportType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRStartOverSupportType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelNetPVRTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private NetPVRPauseSupportType netPVRPauseSupported;

    private Integer pauseLiveTVTGapTimeStart;

    private Integer pauseLiveTVTGapTimeEnd;

    private NetPVRStartOverSupportType netPVRStartOverSupported;

    private Integer startOverGapTimeStart;

    private Integer startOverGapTimeEnd;

    private String netPVRPreRollAssetId;

    private Integer netPVRFragmentSize;

    private Integer netPVRRecordTime;

    private Integer startOverDefaultTimeStart;

    private Integer startOverDefaultTimeEnd;

    public ChannelNetPVRTO() {
        ;
    }

    public ChannelNetPVRTO(NetPVRPauseSupportType netPVRPauseSupported,
            Integer pauseLiveTVTGapTimeStart, Integer pauseLiveTVTGapTimeEnd,
            NetPVRStartOverSupportType netPVRStartOverSupported,
            Integer startOverGapTimeStart, Integer startOverGapTimeEnd,
            String netPVRPreRollAssetId, Integer netPVRFragmentSize,
            Integer netPVRRecordTime, Integer startOverDefaultTimeStart,
            Integer startOverDefaultTimeEnd) {
        this.netPVRPauseSupported = netPVRPauseSupported;
        this.pauseLiveTVTGapTimeStart = pauseLiveTVTGapTimeStart;
        this.pauseLiveTVTGapTimeEnd = pauseLiveTVTGapTimeEnd;
        this.netPVRStartOverSupported = netPVRStartOverSupported;
        this.startOverGapTimeStart = startOverGapTimeStart;
        this.startOverGapTimeEnd = startOverGapTimeEnd;
        this.netPVRPreRollAssetId = netPVRPreRollAssetId;
        this.netPVRFragmentSize = netPVRFragmentSize;
        this.netPVRRecordTime = netPVRRecordTime;
        this.startOverDefaultTimeStart = startOverDefaultTimeStart;
        this.startOverDefaultTimeEnd = startOverDefaultTimeEnd;
    }

    public NetPVRPauseSupportType getNetPVRPauseSupported() {
        return netPVRPauseSupported;
    }

    public Integer getPauseLiveTVTGapTimeStart() {
        return pauseLiveTVTGapTimeStart;
    }

    public Integer getPauseLiveTVTGapTimeEnd() {
        return pauseLiveTVTGapTimeEnd;
    }

    public NetPVRStartOverSupportType getNetPVRStartOverSupported() {
        return netPVRStartOverSupported;
    }

    public Integer getStartOverGapTimeStart() {
        return startOverGapTimeStart;
    }

    public Integer getStartOverGapTimeEnd() {
        return startOverGapTimeEnd;
    }

    public String getNetPVRPreRollAssetId() {
        return netPVRPreRollAssetId;
    }

    public Integer getNetPVRFragmentSize() {
        return netPVRFragmentSize;
    }

    public Integer getNetPVRRecordTime() {
        return netPVRRecordTime;
    }

    public Integer getStartOverDefaultTimeStart() {
        return startOverDefaultTimeStart;
    }

    public Integer getStartOverDefaultTimeEnd() {
        return startOverDefaultTimeEnd;
    }

}
