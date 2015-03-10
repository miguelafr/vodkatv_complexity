package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelAccessType;
import com.lambdastream.tahiti.model.configuration.contentcategory.to.ContentCategoryTO;
import com.lambdastream.tahiti.model.configuration.parentalrating.to.ParentalRatingTO;

@XmlRootElement(name="channel")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChannelDataDetailsTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String vodkatvChannelId;

    private String channelType;

    private String name;

    private String description;

    private String logoUrl;

    private String languageCode;

    private String countryCode;

    @XmlElement(name = "parentalRating")
    private ParentalRatingTO parentalRatingTO;

    private boolean active;

    @XmlElement(name = "metaservers")
    private ChannelMetaserversTO metaserversTO;

    @XmlElement(name = "netPVR")
    private ChannelNetPVRTO netPVRTO;

    private boolean pvrSupported;

    private Boolean localPVRSupported;

    private ChannelAccessType accessType;

    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "category")
    private Collection<ContentCategoryTO> categoryTOs;

    @XmlElementWrapper(name = "networkConfiguration")
    @XmlElement(name = "playInfo")
    private Collection<ChannelDataPlayInfoDetailsTO> playInfoTOs;

    public ChannelDataDetailsTO() {
        ;
    }

    public ChannelDataDetailsTO(Long id) {
        this.id = id;
    }

    public ChannelDataDetailsTO(Long id, String vodkatvChannelId,
            String channelType, String name,
            String description, String logoUrl, String languageCode,
            String countryCode, ParentalRatingTO parentalRatingTO, boolean active,
            ChannelMetaserversTO metaserversTO,
            ChannelNetPVRTO netPVRTO, boolean pvrSupported,
            Boolean localPVRSupported, ChannelAccessType accessType,
            Collection<ContentCategoryTO> categoryTOs,
            Collection<ChannelDataPlayInfoDetailsTO> playInfoTOs) {
        this.id = id;
        this.vodkatvChannelId = vodkatvChannelId;
        this.channelType = channelType;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.parentalRatingTO = parentalRatingTO;
        this.active = active;
        this.metaserversTO = metaserversTO;
        this.netPVRTO = netPVRTO;
        this.pvrSupported = pvrSupported;
        this.localPVRSupported = localPVRSupported;
        this.accessType = accessType;
        this.categoryTOs = categoryTOs;
        this.playInfoTOs = playInfoTOs;
    }

    public Long getId() {
        return id;
    }

    public String getVodkatvChannelId() {
        return vodkatvChannelId;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public ParentalRatingTO getParentalRatingTO() {
        return parentalRatingTO;
    }

    public boolean isActive() {
        return active;
    }

    public ChannelMetaserversTO getMetaserversTO() {
        return metaserversTO;
    }

    public ChannelNetPVRTO getNetPVRTO() {
        return netPVRTO;
    }

    public boolean isPvrSupported() {
        return pvrSupported;
    }

    public Boolean getLocalPVRSupported() {
        return localPVRSupported;
    }

    public ChannelAccessType getAccessType() {
        return accessType;
    }

    public Collection<ContentCategoryTO> getCategoryTOs() {
        return categoryTOs;
    }

    public Collection<ChannelDataPlayInfoDetailsTO> getPlayInfoTOs() {
        return playInfoTOs;
    }

}
