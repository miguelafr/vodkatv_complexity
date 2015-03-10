package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.provisioning.devicetype.to.DeviceTypeTO;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegate;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegateFactory;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.utils.StringUtils;

public class FindChannelDataPlayInfoByUserAgentAction {

    private ChannelDataTO channelDataTO;

    private String userAgent;

    public FindChannelDataPlayInfoByUserAgentAction(ChannelDataTO channelDataTO,
            String userAgent) {
        this.channelDataTO = channelDataTO;
        this.userAgent = userAgent;
    }

    public ChannelDataPlayInfoTO execute() throws InternalErrorException {

        Collection<ChannelDataPlayInfoTO> channelDataPlayInfoTOs =
                channelDataTO.getChannelDataPlayInfoTOs();

        if (channelDataPlayInfoTOs != null && !channelDataPlayInfoTOs.isEmpty()){

            Map<Long, DeviceTypeTO> deviceTypes = getDeviceTypes();

            for (ChannelDataPlayInfoTO channelDataPlayInfoTO : channelDataPlayInfoTOs){
                if (channelDataPlayInfoTO.getDeviceTypeId() != null){

                    if(deviceTypes.containsKey(
                            channelDataPlayInfoTO.getDeviceTypeId())) {
                        DeviceTypeTO deviceTypeTO = deviceTypes.get(
                                channelDataPlayInfoTO.getDeviceTypeId());
                        if ((userAgent == null && StringUtils.emptyOrNull(deviceTypeTO.getUserAgent())) ||
                                (userAgent != null && userAgent.matches(deviceTypeTO.getUserAgent()))){
                            return channelDataPlayInfoTO;
                        }
                    }

                } else {

                    /*
                     * Default channelDataPlayInfoTO
                     */
                    return channelDataPlayInfoTO;

                }
            }
        }

        return new ChannelDataPlayInfoTO(null,
                channelDataTO.getVodkatvChannelId(),
                null, ChannelDataPlayInfoTO.INVALID_POSITION, "",
                null, null, null, null, null, null, null, null, null,
                null, null, null);
    }

    private Map<Long, DeviceTypeTO> getDeviceTypes()
            throws InternalErrorException {
        MappingFacadeDelegate mappingFacadeDelegate =
                MappingFacadeDelegateFactory.getDelegate();
        Collection<DeviceTypeTO> deviceTypeTOs =
                mappingFacadeDelegate.findAllDeviceTypes();

        Map<Long, DeviceTypeTO> result = new HashMap<Long, DeviceTypeTO>();
        for(DeviceTypeTO deviceTypeTO : deviceTypeTOs) {
            result.put(deviceTypeTO.getDeviceTypeId(), deviceTypeTO);
        }
        return result;
    }


}
