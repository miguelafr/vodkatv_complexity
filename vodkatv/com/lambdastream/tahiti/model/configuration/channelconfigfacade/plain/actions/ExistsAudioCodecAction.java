package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.AudioCodecTO;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;

public class ExistsAudioCodecAction implements NonTransactionalPlainAction<Boolean> {

    String audioCodec;
    public ExistsAudioCodecAction(String audioCodec){
        this.audioCodec = audioCodec;
    }

    public Boolean execute(DataConnection connection)
            throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        Collection<AudioCodecTO> audioCodecTOs =
                channelConfigFacadeDelegate.findAllAudioCodecs();

        for(AudioCodecTO audioCodecTO : audioCodecTOs){
            if(audioCodecTO.getCodec().equals(audioCodec)){
                return new Boolean(true);
            }
        }

        return new Boolean(false);
    }
}