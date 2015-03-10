package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.to.ChannelDataInfoTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.util.Transforms;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.videoserverfacade.delegate.VideoServerFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.videoserverfacade.delegate.VideoServerFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.videoservergroupchanneldataplayinfo.to.VideoServerGroupChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.util.facade.PlainFacadeDataSource;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.NonTransactionalPlainAction;
import com.lambdastream.util.utils.CollectionUtils;

public class FindActiveChannelsByVideoServerGroupsOriginAction
implements NonTransactionalPlainAction<Collection<ChannelDataInfoTO>>{

    private Collection<Long> videoServerGroupIds;

    public FindActiveChannelsByVideoServerGroupsOriginAction(
            Collection<Long> videoServerGroupIds) {
        this.videoServerGroupIds = videoServerGroupIds;
    }

    @Override
    public Collection<ChannelDataInfoTO> execute(DataConnection connection)
    		throws InternalErrorException {

        /*
         * Facades
         */
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate(
                        new PlainFacadeDataSource(connection));
        VideoServerFacadeDelegate videoServerFacadeDelegate =
                VideoServerFacadeDelegateFactory.getDelegate(
                        new PlainFacadeDataSource(connection));

        Collection<ChannelDataTO> result = new ArrayList<ChannelDataTO>();

        Collection<VideoServerGroupChannelDataPlayInfoTO> videoServerGroupChannelDataPlayInfoTOs=
                videoServerFacadeDelegate.findVideoServerGroupChannelDataPlayInfosByVideoServerGroupIds(
                        videoServerGroupIds);

        if (!CollectionUtils.emptyOrNull(videoServerGroupChannelDataPlayInfoTOs)){

            /*
             * Get channel data play infos
             */
            Collection<Long> channelDataPlayInfoIds = new ArrayList<Long>();
            for(VideoServerGroupChannelDataPlayInfoTO videoServerGroupChannelDataPlayInfoTO :
                    videoServerGroupChannelDataPlayInfoTOs) {
                channelDataPlayInfoIds.add(
                        videoServerGroupChannelDataPlayInfoTO.getChannelDataPlayInfoId());
            }

            /*
             * Get channels
             */
            Collection<ChannelDataTO> channelTOs =
                    channelConfigFacadeDelegate.findAllActiveChannelsData();

            /*
             * Build result
             */
            for(ChannelDataTO channelDataTO : channelTOs) {
                Collection<ChannelDataPlayInfoTO> channelDataPlayInfoTOs =
                        new ArrayList<ChannelDataPlayInfoTO>();
                for(ChannelDataPlayInfoTO channelDataPlayInfoTO :
                            channelDataTO.getChannelDataPlayInfoTOs()) {
                    if(channelDataPlayInfoIds.contains(
                            channelDataPlayInfoTO.getChannelDataPlayInfoId())) {
                        channelDataPlayInfoTOs.add(channelDataPlayInfoTO);
                    }
                }
                if (!CollectionUtils.emptyOrNull(channelDataPlayInfoTOs)){
                    result.add(new ChannelDataTO(
                            channelDataTO.getId(),
                            channelDataTO.getVodkatvChannelId(),
                            channelDataTO.getChannelId(),
                            channelDataTO.getChannelType(),
                            channelDataTO.getChannelName(),
                            channelDataTO.getChannelDescription(),
                            channelDataTO.getLogoUrl(),
                            channelDataTO.getLanguageCode(),
                            channelDataTO.getCountryCode(),
                            channelDataTO.getParentalRatingId(),
                            channelDataTO.isActive(),
                            channelDataTO.getTextChannelId(),
                            channelDataTO.getNetPVRPauseSupported(),
                            channelDataTO.getCasChannelId(),
                            channelDataTO.getPauseLiveTVTGapTimeStart(),
                            channelDataTO.getPauseLiveTVTGapTimeEnd(),
                            channelDataTO.getNetPVRStartOverSupported(),
                            channelDataTO.getStartOverGapTimeStart(),
                            channelDataTO.getStartOverGapTimeEnd(),
                            channelDataTO.getNetPVRPreRollAssetId(),
                            channelDataTO.getNetPVRFragmentSize(),
                            channelDataTO.getNetPVRRecordTime(),
                            channelDataTO.getStartOverDefaultTimeStart(),
                            channelDataTO.getStartOverDefaultTimeEnd(),
                            channelDataTO.isPvrSupported(),
                            channelDataTO.getLocalPVRSupported(),
                            channelDataTO.getAccessType(),
                            channelDataTO.getChannelCategoryTOs(),
                            channelDataTO.getAvailableEPGHours(),
                            channelDataTO.getHbbtvUrl(),
                            channelDataPlayInfoTOs));
                }
            }
        }

        /*
         * Return result
         */
        return Transforms.toChannelDataInfoTOs(result);

    }
}
