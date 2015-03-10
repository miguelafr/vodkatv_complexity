/**
 * Filename: UpdateChannelsDataAction.java
 * Created on 03/06/2009
 * @author Miguel Ángel Francisco Fernández <miguel.francisco@lambdastream.com>
 */
package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelCategoryTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.contentcategory.dao.SQLContentCategoryDAO;
import com.lambdastream.tahiti.model.configuration.contentcategory.dao.SQLContentCategoryDAOFactory;
import com.lambdastream.tahiti.model.configuration.contentcategory.to.ContentCategoryTO;
import com.lambdastream.tahiti.model.configuration.videoservergroup.dao.SQLVideoServerGroupDAO;
import com.lambdastream.tahiti.model.configuration.videoservergroup.dao.SQLVideoServerGroupDAOFactory;
import com.lambdastream.tahiti.model.configuration.videoservergroup.to.VideoServerGroupTO;
import com.lambdastream.tahiti.model.media.mediafacade.delegate.MediaFacadeDelegate;
import com.lambdastream.tahiti.model.media.mediafacade.delegate.MediaFacadeDelegateFactory;
import com.lambdastream.tahiti.model.util.exceptions.DuplicateChannelAudioLanguageInstanceException;
import com.lambdastream.tahiti.model.util.notifier.ChangesNotifier;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

/**
 * @author miguelafr
 *
 */
public class UpdateChannelDataAction implements TransactionalPlainAction<Object> {

    private ChannelDataTO channelDataTO;

    public UpdateChannelDataAction(ChannelDataTO channelDataTO) {
        this.channelDataTO = channelDataTO;
    }

    public Object execute(DataConnection connection)
        throws InstanceNotFoundException, InternalErrorException,
            DuplicateChannelAudioLanguageInstanceException{

        MediaFacadeDelegate mediaFacadeDelegate =
            MediaFacadeDelegateFactory.getDelegate();

        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();
        SQLContentCategoryDAO sqlContentCategoryDAO =
                SQLContentCategoryDAOFactory.getDAO();
        SQLVideoServerGroupDAO sqlVideoServerGroupDAO =
                SQLVideoServerGroupDAOFactory.getDAO();

        /*
         * Check if the categories exist
         */
        if(channelDataTO.getChannelCategoryTOs() != null) {
            for(ChannelCategoryTO channelCategoryTO : channelDataTO.getChannelCategoryTOs()) {
                if(!sqlContentCategoryDAO.exists(connection.getConnection(), channelCategoryTO.getCategoryId())) {
                    throw new InstanceNotFoundException(
                            channelCategoryTO.getCategoryId(),
                            ContentCategoryTO.class.getName());
                }
            }
        }

        /*
         * Check if the video server groups exist
         */
        if(channelDataTO.getChannelDataPlayInfoTOs() != null) {
            for(ChannelDataPlayInfoTO channelDataPlayInfoTO :
                    channelDataTO.getChannelDataPlayInfoTOs()) {

                if(channelDataPlayInfoTO.getVideoServerGroupsEdge() != null) {
                    for(VideoServerGroupTO videoServerGroupTO :
                            channelDataPlayInfoTO.getVideoServerGroupsEdge()) {
                        if(!sqlVideoServerGroupDAO.exists(connection.getConnection(),
                                videoServerGroupTO.getId())) {
                            throw new InstanceNotFoundException(
                                    videoServerGroupTO.getId(),
                                    VideoServerGroupTO.class.getName());
                        }
                    }
                }

                if(channelDataPlayInfoTO.getVideoServerGroupsOrigin() != null) {
                    for(VideoServerGroupTO videoServerGroupTO :
                            channelDataPlayInfoTO.getVideoServerGroupsOrigin()) {
                        if(!sqlVideoServerGroupDAO.exists(connection.getConnection(),
                                videoServerGroupTO.getId())) {
                            throw new InstanceNotFoundException(
                                    videoServerGroupTO.getId(),
                                    VideoServerGroupTO.class.getName());
                        }
                    }
                }
            }
        }

        /*
         * Create the channel
         */
        sqlChannelDataDAO.updateByVoDKATVChannelId(connection.getConnection(), channelDataTO);

        /*
         * Refresh cache
         */
        mediaFacadeDelegate.refreshCache();
        ChangesNotifier.getInstance().notifyUpdateChannel(channelDataTO);

        return null;
    }
}
