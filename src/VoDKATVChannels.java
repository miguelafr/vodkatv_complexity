import java.util.Collection;

import javax.sql.DataSource;

import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelAccessType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelCategoryTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRPauseSupportType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRStartOverSupportType;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegateFactory;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegate;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegateFactory;
import com.lambdastream.tahiti.model.util.naming.GlobalNames;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.scheduler.SchedulerManager;
import com.lambdastream.util.sql.DataSourceLocator;
import com.lambdastream.util.sql.SimpleDataSource;
import com.lambdastream.util.utils.ChunkTO;

public class VoDKATVChannels {

    private static VoDKATVChannels instance;

    private int NUM_CHANNELS = 1000;

    private VoDKATVChannels() {
        ;
    }

    public static VoDKATVChannels getInstance() {
        if(instance == null) {
            instance = new VoDKATVChannels();
        }
        return instance;
    }

    /*=========================================================================
     * METHODS TO TEST
     * ====================================================================== */
    /**
     * Find all TV channels
     */
    public Collection<ChannelTO> findAllChannels()
            throws InternalErrorException {
        MediaChannelsFacadeDelegate mediaChannelsFacadeDelegate =
                MediaChannelsFacadeDelegateFactory.getDelegate();
        return mediaChannelsFacadeDelegate.findAllChannels();
    }

    /**
     * Find N TV channels
     */
    public ChunkTO<ChannelDataTO> findChannelDatas(int numChannels)
            throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing findChannelDatas(" +
            numChannels + ")...");
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        int startIndex = 1;
        int count = numChannels;
        SearchOptions searchOptions = new SearchOptions(
                new Integer(startIndex), new Integer(count));
        return channelConfigFacadeDelegate.findChannelsData(searchOptions);
    }

    /*=========================================================================
     * PREPARE TEST ENVIRONMENT
     * ====================================================================== */
    public void prepareEnvironment() throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing prepareEnvironment...");
        setUp();
        createTestChannels();
    }

    /*=========================================================================
     * SET UP
     * ====================================================================== */
    public void setUp() throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing setUp...");
        initDatasource();
        initMetadataServer();
    }

    /**
     * Finalizes VoDKATV components
     */
    public void tearDown() throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing tearDown...");
        SchedulerManager.getInstance().shutdownAll();
    }

    /*=========================================================================
     * SUBSYSTEMS INITIALIZATION
     * ====================================================================== */
    private void initDatasource() {
        DataSource dataSource = new SimpleDataSource();
        /*
         * Its necessary to add the data source to the data source locator
         * to avoid JNDI searching.
         */
        DataSourceLocator.addDataSource(GlobalNames.TAHITI_DATA_SOURCE,
                dataSource);
    }

    private void initMetadataServer() throws InternalErrorException {
        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        configurationFacadeDelegate.initMetadataServers();
    }

    /*=========================================================================
     * UTILITIES
     * ====================================================================== */
    private void createTestChannels() throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        channelConfigFacadeDelegate.deleteAllChannelDatas();
        for(int i = 0; i < NUM_CHANNELS; i++) {
            Long id = null;
            String vodkatvChannelId = "test_" + i;
            String channelId = null;
            String channelType = "VIDEO";
            String channelName = "Channel " + i;
            String channelDescription = "Description " + i;
            String logoUrl = null;
            String languageCode = null;
            String countryCode = null;
            Long parentalRatingId = null;
            boolean active = true;
            String textChannelId = null;
            NetPVRPauseSupportType netPVRPauseSupported = null;
            String casChannelId = null;
            Integer pauseLiveTVTGapTimeStart = null;
            Integer pauseLiveTVTGapTimeEnd = null;
            NetPVRStartOverSupportType netPVRStartOverSupported = null;
            Integer startOverGapTimeStart = null;
            Integer startOverGapTimeEnd = null;
            String netPVRPreRollAssetId = null;
            Integer netPVRFragmentSize = null;
            Integer netPVRRecordTime = null;
            Integer startOverDefaultTimeStart = null;
            Integer startOverDefaultTimeEnd = null;
            boolean pvrSupported = false;
            Boolean localPVRSupported = null;
            ChannelAccessType accessType = ChannelAccessType.FREE;
            Collection<ChannelCategoryTO> channelCategoryTOs = null;
            Integer availableEPGHours = null;
            String hbbtvUrl = null;
            Collection<ChannelDataPlayInfoTO> channelDataPlayInfoTOs = null;
            ChannelDataTO channelDataTO = new ChannelDataTO(id,
                    vodkatvChannelId, channelId, channelType, channelName,
                    channelDescription, logoUrl, languageCode, countryCode,
                    parentalRatingId, active, textChannelId,
                    netPVRPauseSupported, casChannelId,
                    pauseLiveTVTGapTimeStart, pauseLiveTVTGapTimeEnd,
                    netPVRStartOverSupported, startOverGapTimeStart,
                    startOverGapTimeEnd, netPVRPreRollAssetId,
                    netPVRFragmentSize, netPVRRecordTime,
                    startOverDefaultTimeStart, startOverDefaultTimeEnd,
                    pvrSupported, localPVRSupported, accessType,
                    channelCategoryTOs, availableEPGHours, hbbtvUrl,
                    channelDataPlayInfoTOs);
            try {
                channelConfigFacadeDelegate.createChannelData(channelDataTO);
            } catch (DuplicateInstanceException e) {
                throw new InternalErrorException(e);
            } catch (InstanceNotFoundException e) {
                throw new InternalErrorException(e);
            }
        }
    }

    private void deleteTestChannels() throws InternalErrorException {
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();
        channelConfigFacadeDelegate.deleteAllChannelDatas();
    }

    /*=========================================================================
     * TEST
     * ====================================================================== */
    public static void main(String[] args) {
        try {
            VoDKATVChannels v = VoDKATVChannels.getInstance();
            v.setUp();
            //v.prepareEnvironment();
            System.out.println(v.findChannelDatas(1));
            v.tearDown();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
