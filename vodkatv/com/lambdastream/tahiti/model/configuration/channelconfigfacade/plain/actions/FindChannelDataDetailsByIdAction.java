package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataDetailsTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.SimplePlainAction;

public class FindChannelDataDetailsByIdAction implements SimplePlainAction<ChannelDataDetailsTO> {

    private Long id;

    public FindChannelDataDetailsByIdAction(Long id) {
        this.id = id;
    }

    @Override
    public ChannelDataDetailsTO execute() throws InstanceNotFoundException,
            InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        try {
            ChannelDataTO channelDataTO =
                    channelConfigFacadeDelegate.findChannelDataById(id);

            Collection<ChannelDataTO> channelDataTOs = new ArrayList<ChannelDataTO>();
            channelDataTOs.add(channelDataTO);
            return Transforms.toChannelDataDetailsTOs(channelDataTOs).iterator().next();

        } catch (InstanceNotFoundException infe) {
            throw new InstanceNotFoundException(infe.getKey(),
                    ChannelDataDetailsTO.class.getName(), infe.getFieldName());
        }

    }


}
