package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;

/**
 * @author Daniel Rodríguez Baleato
 */
public class FindAllChannelsDataIfRecordingActiveAction {

    public FindAllChannelsDataIfRecordingActiveAction() {
        ;
    }

    public Collection<ChannelDataTO> execute() throws InternalErrorException {

        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        /*
         * Find all channelDataTOs
         */
        Collection<ChannelDataTO> channelDataTOs =
                channelConfigFacadeDelegate.findAllChannelsData();

        /*
         * Build result
         */
        Collection<ChannelDataTO> channelDataRestulsTOs =
            new ArrayList<ChannelDataTO>();
        // Filter by Net PVR recording active
        for(ChannelDataTO channelDataTO : channelDataTOs) {
            if(channelDataTO.isNetPVRStartOverSupportActive()){
                channelDataRestulsTOs.add(channelDataTO);
            }
        }

        /*
         * Return result
         */
        return channelDataRestulsTOs;
    }

}
