package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.SimplePlainAction;

public class FindChannelDataInfoByVoDKATVChannelIdAction
		implements SimplePlainAction<ChannelDataInfoTO> {

	private String vodkatvChannelId;
	
	public FindChannelDataInfoByVoDKATVChannelIdAction(String vodkatvChannelId) {
		this.vodkatvChannelId = vodkatvChannelId;
	}
	
	@Override
	public ChannelDataInfoTO execute() throws InstanceNotFoundException,
	InternalErrorException {
		ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
				ChannelConfigFacadeDelegateFactory.getDelegate();
		ChannelDataTO channelDataTO =
				channelConfigFacadeDelegate.findChannelDataByVoDKATVChannelId(
						vodkatvChannelId);
		return Transforms.toChannelDataInfoTO(channelDataTO);
	}

}
