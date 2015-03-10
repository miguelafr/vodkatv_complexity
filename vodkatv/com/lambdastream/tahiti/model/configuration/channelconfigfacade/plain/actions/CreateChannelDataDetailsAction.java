package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.exceptions.ModelException;
import com.lambdastream.util.sql.SimplePlainAction;

public class CreateChannelDataDetailsAction implements SimplePlainAction<ChannelDataDetailsTO> {

    private ChannelDataDetailsTO channelDataDetailsTO;

    public CreateChannelDataDetailsAction(
            ChannelDataDetailsTO channelDataDetailsTO) {
        this.channelDataDetailsTO = channelDataDetailsTO;
    }

    @Override
    public ChannelDataDetailsTO execute() throws ModelException,
            InternalErrorException {

        /*
         * Facade
         */
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        /*
         * Get channel data to create
         */
        Collection<ChannelDataDetailsTO> channelDataDetailsTOs =
                new ArrayList<ChannelDataDetailsTO>();
        channelDataDetailsTOs.add(channelDataDetailsTO);
        ChannelDataTO channelDataTO = Transforms.toChannelDataTOs(
                channelDataDetailsTOs).iterator().next();

        /*
         * Create channel data
         */
        ChannelDataTO createdChannelDataTO =
                channelConfigFacadeDelegate.createChannelData(channelDataTO);

        /*
         * Get created channel data
         */
        Collection<ChannelDataTO> createdChannelDataTOs =
                new ArrayList<ChannelDataTO>();
        createdChannelDataTOs.add(createdChannelDataTO);
        ChannelDataDetailsTO createdChannelDataDetailsTO = Transforms.toChannelDataDetailsTOs(
                createdChannelDataTOs).iterator().next();

        /*
         * Return result
         */
        return createdChannelDataDetailsTO;
    }


}
