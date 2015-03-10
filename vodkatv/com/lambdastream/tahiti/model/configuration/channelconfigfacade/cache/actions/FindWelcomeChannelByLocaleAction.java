package com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.actions;

import java.util.Locale;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.cache.CacheProcessorAction;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;


public class FindWelcomeChannelByLocaleAction
implements CacheProcessorAction<ChannelDataTO> {

    private ChannelConfigFacadeDelegate delegate;

    private Locale locale;

    public FindWelcomeChannelByLocaleAction(Locale locale,
            ChannelConfigFacadeDelegate delegate) {
        this.delegate = delegate;
        this.locale = locale;
    }

    public ChannelDataTO execute() throws InternalErrorException,
    InstanceNotFoundException {
        return delegate.findWelcomeChannelByLocale(locale);
    }

}
