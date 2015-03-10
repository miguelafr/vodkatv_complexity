/*
 * Filename: FindChannelsRedefinitionAction.java
 * Created on 05-dic-2006 9:39:02
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelRedefinitionTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ChannelListDetailsTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.util.PropertiesConfiguration;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;

/**
 * @author Miguel Ángel Francisco Fernández
 */
public class FindDefaultChannelsRedefinitionsAction {

    public FindDefaultChannelsRedefinitionsAction(){
        ;
    }

    public Collection<ChannelRedefinitionTO> execute()
    throws InternalErrorException {

        /*
         * TODO: This class is maintained because of compatibility reasons
         */

        /*
         * Facades
         */
        PropertiesConfiguration propertiesConfiguration =
                PropertiesConfiguration.getInstance();

        /*
         * Get default lists
         */
        Long videoChannelListId =
                propertiesConfiguration.getDefaultVideoChannelList();
        Long audioChannelListId =
                propertiesConfiguration.getDefaultAudioChannelList();

        /*
         * Get channels
         */
        Collection<ChannelRedefinitionTO> channelRedefinitionTOs =
                new ArrayList<ChannelRedefinitionTO>();
        channelRedefinitionTOs.addAll(getChannelRedefinitionsByChannelsList(
                videoChannelListId));
        channelRedefinitionTOs.addAll(getChannelRedefinitionsByChannelsList(
                audioChannelListId));

        return channelRedefinitionTOs;
    }

    private Collection<ChannelRedefinitionTO> getChannelRedefinitionsByChannelsList(
            Long channelsListId) throws InternalErrorException {

        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        try {
            if (channelsListId != null) {
                ChannelListDetailsTO channelListDetailsTO =
                        configurationFacadeDelegate.findChannelListDetails(
                                channelsListId);
                if (channelListDetailsTO != null &&
                        channelListDetailsTO.getChannelListItemDetailsTOs() != null) {
                    return Transforms.transform(
                            channelListDetailsTO.getChannelListItemDetailsTOs());
                }
            }
        } catch (InstanceNotFoundException e) {
            ;
        }

        /*
         * By default
         */
        return new ArrayList<ChannelRedefinitionTO>();
    }
}
