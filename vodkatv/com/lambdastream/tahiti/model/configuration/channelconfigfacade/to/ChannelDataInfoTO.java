/**
 * Filename: ChannelDataDetailsTO.java
 * Created on 03/06/2009
 * @author Miguel Ángel Francisco Fernández <miguel.francisco@lambdastream.com>
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.to;

import java.io.Serializable;

import com.lambdastream.cas.connector.casconnectorfacade.to.ChannelCASTO;
import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.sertext.connector.textchannel.to.TextChannelTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.parentalrating.to.ParentalRatingTO;

/**
 * @author miguelafr
 *
 */
public class ChannelDataInfoTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private ChannelTO channelTO;

    private TextChannelTO textChannelTO;

    private ChannelDataTO channelDataTO;

    private ChannelCASTO channelCASTO;
    
    private ParentalRatingTO parentalRatingTO;

    public ChannelDataInfoTO(ChannelTO channelTO,
            TextChannelTO textChannelTO, ChannelDataTO channelDataTO,
            ChannelCASTO channelCASTO, ParentalRatingTO parentalRatingTO) {
        this.channelTO = channelTO;
        this.textChannelTO = textChannelTO;
        this.channelDataTO = channelDataTO;
        this.channelCASTO = channelCASTO;
        this.parentalRatingTO = parentalRatingTO;
    }

    public ChannelTO getChannelTO() {
        return channelTO;
    }

    public TextChannelTO getTextChannelTO() {
        return textChannelTO;
    }

    public ChannelDataTO getChannelDataTO() {
        return channelDataTO;
    }

    public ChannelCASTO getChannelCASTO(){
    	return channelCASTO;
    }

    public ParentalRatingTO getParentalRatingTO() {
    	return parentalRatingTO;
    }
    
    public String toString() {
        return new String("ChannelTO = " + channelTO + " | " +
                "textChannelTO = " + textChannelTO + " | " +
                "channelDataTO = " + channelDataTO + " | " +
                "channelCASTO= " + channelCASTO + " | " +
                "parentalRatingTO = " + parentalRatingTO);
    }

}
