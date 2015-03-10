package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.lambdastream.cas.connector.casconnectorfacade.to.ChannelCASTO;
import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.sertext.connector.textchannel.to.TextChannelTO;

@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelMetaserversTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "epgChannel")
    private ChannelTO channelTO;

    @XmlElement(name = "textChannel")
    private TextChannelTO textChannelTO;

    @XmlElement(name = "casChannel")
    private ChannelCASTO channelCASTO;

    private Integer availableEPGHours;

    private String hbbtvUrl;

    public ChannelMetaserversTO() {
        ;
    }

    public ChannelMetaserversTO(ChannelTO channelTO, TextChannelTO textChannelTO,
            ChannelCASTO channelCASTO, Integer availableEPGHours,
            String hbbtvUrl) {
        this.channelTO = channelTO;
        this.textChannelTO = textChannelTO;
        this.channelCASTO = channelCASTO;
        this.availableEPGHours = availableEPGHours;
        this.hbbtvUrl = hbbtvUrl;
    }

    public ChannelTO getChannelTO() {
        return channelTO;
    }

    public TextChannelTO getTextChannelTO() {
        return textChannelTO;
    }

    public ChannelCASTO getChannelCASTO() {
        return channelCASTO;
    }

    public Integer getAvailableEPGHours() {
        return availableEPGHours;
    }

    public String getHbbtvUrl() {
        return hbbtvUrl;
    }

}
