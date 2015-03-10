package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindAllChannelsDataIfRecordingActiveAction
implements CacheProcessorAction<Collection<ChannelDataTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindAllChannelsDataIfRecordingActiveAction(
            ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }

    public Collection<ChannelDataTO> execute() throws InstanceNotFoundException,
    InternalErrorException {
        return delegate.findAllChannelsDataIfRecordingActive();
    }

}
