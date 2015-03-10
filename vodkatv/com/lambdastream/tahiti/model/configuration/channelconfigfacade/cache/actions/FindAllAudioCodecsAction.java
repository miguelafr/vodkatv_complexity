package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.AudioCodecTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindAllAudioCodecsAction implements CacheProcessorAction<Collection<AudioCodecTO>> {

    private ChannelConfigFacadeDelegate delegate;

    public FindAllAudioCodecsAction(ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
    }
    public Collection<AudioCodecTO> execute() throws InternalErrorException {
        return delegate.findAllAudioCodecs();
    }

}
