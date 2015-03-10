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

public class UpdateChannelDataDetailsAction implements SimplePlainAction<Object> {

    private ChannelDataDetailsTO channelDataDetailsTO;

    public UpdateChannelDataDetailsAction(
            ChannelDataDetailsTO channelDataDetailsTO) {
        this.channelDataDetailsTO = channelDataDetailsTO;
    }

    @Override
    public Object execute() throws ModelException,
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
         * Update channel data
         */
        channelConfigFacadeDelegate.updateChannelData(channelDataTO);

        /*
         * Return result
         */
        return null;
    }

}
