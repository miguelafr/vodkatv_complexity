/*
 * Filename: ConfigurationFacadeDelegateFactory.java
 * Created on 24-oct-2006 16:44:14
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.cache.CacheChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.PlainChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.util.facade.FacadeDataSource;
import com.lambdastream.util.configuration.ConfigurationParametersManager;
import com.lambdastream.util.configuration.MissingConfigurationParameterException;
import com.lambdastream.util.exceptions.InternalErrorException;

/**
 * @author Miguel Ángel Francisco Fernández
 */
public final class ChannelConfigFacadeDelegateFactory {

    private static boolean useCache;

    static {
        try {
            useCache = new Boolean(ConfigurationParametersManager.getParameter(
                    "FacadeDelete/caches")).booleanValue();
        } catch (MissingConfigurationParameterException e) {
            useCache = true;
        }
    }

    private static ChannelConfigFacadeDelegate delegate;

    private ChannelConfigFacadeDelegateFactory() {}

    public static ChannelConfigFacadeDelegate getDelegate()
            throws InternalErrorException{
        return getDelegate(null);
    }

    public static ChannelConfigFacadeDelegate getDelegate(
            FacadeDataSource facadeDataSource) throws InternalErrorException{

        if(delegate == null) {
            if(useCache) {
                return new CacheChannelConfigFacadeDelegate(
                        new PlainChannelConfigFacadeDelegate(facadeDataSource));
            } else {
                return new PlainChannelConfigFacadeDelegate(facadeDataSource);
            }
        } else {
            return delegate;
        }
    }

    public static void setDelegate(
            ChannelConfigFacadeDelegate delegate) {
        ChannelConfigFacadeDelegateFactory.delegate = delegate;
    }

}
