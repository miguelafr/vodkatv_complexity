package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.VideoCodecTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class ExistsVideoCodecAction implements NonTransactionalPlainAction<Boolean> {

    String videoCodec;
    public ExistsVideoCodecAction(String videoCodec){
        this.videoCodec = videoCodec;
    }

    public Boolean execute(DataConnection connection)
            throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        Collection<VideoCodecTO> videoCodecTOs =
                channelConfigFacadeDelegate.findAllVideoCodecs();

        for(VideoCodecTO videoCodecTO : videoCodecTOs){
            if(videoCodecTO.getCodec().equals(videoCodec)){
                return new Boolean(true);
            }
        }

        return new Boolean(false);
    }
}