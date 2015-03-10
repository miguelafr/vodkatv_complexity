package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.WelcomeChannelDetailsTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;


public class FindWelcomeChannelsDetailsAction
implements CacheProcessorAction<Collection<WelcomeChannelDetailsTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindWelcomeChannelsDetailsAction(ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }

    public Collection<WelcomeChannelDetailsTO> execute()
            throws InternalErrorException {
        return delegate.findWelcomeChannelsDetails();
    }

}
