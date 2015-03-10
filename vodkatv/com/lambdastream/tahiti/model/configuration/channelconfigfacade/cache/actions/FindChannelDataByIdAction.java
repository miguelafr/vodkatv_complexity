package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;

public class FindChannelDataByIdAction
implements CacheProcessorAction<ChannelDataTO> {

    private ChannelConfigFacadeDelegate delegate;

    private Long id;

    public FindChannelDataByIdAction(
            ChannelConfigFacadeDelegate delegate, Long id) {
        this.delegate = delegate;
        this.id = id;
    }
    public ChannelDataTO execute() throws InstanceNotFoundException, InternalErrorException {
        return delegate.findChannelDataById(id);
    }

}
