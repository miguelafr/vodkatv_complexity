package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.SimplePlainAction;

public class FindChannelDataInfoByIdAction implements SimplePlainAction<ChannelDataInfoTO> {

	private Long id;
	
	public FindChannelDataInfoByIdAction(Long id) {
		this.id = id;
	}
	
	@Override
	public ChannelDataInfoTO execute() throws InstanceNotFoundException,
			InternalErrorException {
		ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
				ChannelConfigFacadeDelegateFactory.getDelegate();
		ChannelDataTO channelDataTO = channelConfigFacadeDelegate.findChannelDataById(id);
		return Transforms.toChannelDataInfoTO(channelDataTO);
	}

}
