package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.VideoCodecTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindAllVideoCodecsAction
implements CacheProcessorAction<Collection<VideoCodecTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindAllVideoCodecsAction(ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }
    public Collection<VideoCodecTO> execute() throws InternalErrorException {
        return delegate.findAllVideoCodecs();
    }

}
