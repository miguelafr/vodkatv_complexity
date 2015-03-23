import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.sql.DataSource;

import com.lambdastream.metadata.connector.channelsfacade.delegate.ChannelsFacadeDelegateType;
import com.lambdastream.metadata.connector.channelsfacade.to.ChannelTO;
import com.lambdastream.tahiti.http.controller.session.AccountDeviceTO;
import com.lambdastream.tahiti.model.accounting.account.to.AccountDetailsTO;
import com.lambdastream.tahiti.model.accounting.accountremoteaccess.to.AccountRemoteAccessTO;
import com.lambdastream.tahiti.model.accounting.channelaccesstypesubscription.to.ChannelAccessTypeSubscriptionTO;
import com.lambdastream.tahiti.model.accounting.credit.to.CreditTO;
import com.lambdastream.tahiti.model.accounting.guest.to.GuestTO;
import com.lambdastream.tahiti.model.accounting.outputsprotection.to.OutputsProtectionTO;
import com.lambdastream.tahiti.model.accounting.room.to.RoomTO;
import com.lambdastream.tahiti.model.accounting.sessionaccounting.to.SessionAccountingTO;
import com.lambdastream.tahiti.model.accounting.usersession.to.UserSessionTO;
import com.lambdastream.tahiti.model.business.basicpackage.to.BasicPackageTO;
import com.lambdastream.tahiti.model.business.currency.to.CurrencyTO;
import com.lambdastream.tahiti.model.business.currencyfacade.delegate.CurrencyFacadeDelegate;
import com.lambdastream.tahiti.model.business.currencyfacade.delegate.CurrencyFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.customization.to.CustomizationTO;
import com.lambdastream.tahiti.model.business.desktopconfig.to.DesktopConfigTO;
import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegate;
import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.packagefacade.to.BasicPackageDetailsTO;
import com.lambdastream.tahiti.model.business.product.to.ProductTO;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegate;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.productfacade.to.ProductDetailsTO;
import com.lambdastream.tahiti.model.business.productitem.to.ProductItemAllChannelsTO;
import com.lambdastream.tahiti.model.business.productitem.to.ProductItemTO;
import com.lambdastream.tahiti.model.business.purchasefacade.delegate.AdminPurchaseFacadeDelegate;
import com.lambdastream.tahiti.model.business.purchasefacade.delegate.AdminPurchaseFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.subscription.SubscriptionLimitType;
import com.lambdastream.tahiti.model.business.subscription.SubscriptionRenovationType;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.channelconfigfacade.delegate.ChannelConfigFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelAccessType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelCategoryTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataPlayInfoTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRPauseSupportType;
import com.lambdastream.tahiti.model.configuration.channeldata.to.NetPVRStartOverSupportType;
import com.lambdastream.tahiti.model.configuration.channellist.to.ChannelListTO;
import com.lambdastream.tahiti.model.configuration.channellistitem.to.ChannelListItemTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegate;
import com.lambdastream.tahiti.model.configuration.configurationfacade.delegate.ConfigurationFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.configurationfacade.exceptions.DuplicateChannelNumberException;
import com.lambdastream.tahiti.model.configuration.configurationfacade.exceptions.IncompatibleChannelTypeException;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.AuxCacheServerTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ChannelListDetailsTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.ChannelListItemDetailsTO;
import com.lambdastream.tahiti.model.configuration.configurationfacade.to.MetaServerConfigurationTO;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegate;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.delegate.MediaChannelsFacadeDelegateFactory;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.to.ChannelInformationTO;
import com.lambdastream.tahiti.model.media.mediachannelsfacade.util.ChannelSearch;
import com.lambdastream.tahiti.model.provisioning.device.to.DeviceTO;
import com.lambdastream.tahiti.model.provisioning.deviceclass.to.DeviceClass;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegate;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.delegate.MappingFacadeDelegateFactory;
import com.lambdastream.tahiti.model.provisioning.mappingfacade.to.RoomSTBSessionTO;
import com.lambdastream.tahiti.model.util.exceptions.ReferencedObjectNotFoundException;
import com.lambdastream.tahiti.model.util.naming.GlobalNames;
import com.lambdastream.tahiti.model.util.searches.SearchOptions;
import com.lambdastream.util.dao.I18NAttribute;
import com.lambdastream.util.dao.image.ImageAttribute;
import com.lambdastream.util.exceptions.DuplicateInstanceException;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.exceptions.RequiredFieldException;
import com.lambdastream.util.memcached.MemcachedClientManager;
import com.lambdastream.util.scheduler.SchedulerManager;
import com.lambdastream.util.sql.DataSourceLocator;
import com.lambdastream.util.sql.SimpleDataSource;
import com.lambdastream.util.utils.CalendarUtils;
import com.lambdastream.util.utils.ChunkTO;

public class VoDKATVChannels {

    private static VoDKATVChannels instance;

    private String roomId;
    private String sessionAccountingId;
    private Long deviceId;
    private Long accountId;
    private Long channelListId;
    private Long packageId;
    private Long productId;

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

    public ChunkTO<ChannelInformationTO> findChannelsInformation(int numChannels)
            throws InternalErrorException {
        if(deviceId != null && accountId != null) {
            System.out.println("[VoDKATVChannels] - Executing findChannelsInformation(" +
                    numChannels + ")...");
            try {
                MediaChannelsFacadeDelegate mediaChannelsFacadeDelegate =
                        MediaChannelsFacadeDelegateFactory.getDelegate();

                AccountDeviceTO accountDeviceTO = AccountDeviceTO.createInstance(
                        deviceId, accountId);
                ChannelSearch channelSearch = new ChannelSearch();
                int startIndex = 1;
                int count = numChannels;
                int maxNextEvents = 20;
                List<Locale> locales = null;

                return mediaChannelsFacadeDelegate.findChannelsInformation(
                        accountDeviceTO, channelSearch, startIndex, count,
                        maxNextEvents, locales);
            } catch(InstanceNotFoundException infe) {
                throw new InternalErrorException(infe);
            }
        } else {
            System.out.println("[VoDKATVChannels] - Warning: " +
                    "deviceId = " + deviceId + " - " +
                    "accountId = " + accountId);
            return null;
        }
    }

    /*=========================================================================
     * SET UP
     * ====================================================================== */
    public void globalSetUp(boolean useMemcached) throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing globalSetUp(" +
                useMemcached + ") ...");
        initDatasource();
        if(useMemcached) {
            initMemcached();
        }
    }

    public void globalTearDown(boolean useMemcached) throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing globalTearDown(" +
                useMemcached + ") ...");
        if(useMemcached) {
            stopMemcached();
        }
    }

    public void setUp(int numEPGChannels) throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing setUp(" +
                numEPGChannels + ") ...");
        initMetadataServer();
        createTestChannels(numEPGChannels);
        initAccountData();
        initPackagesAndProducts();
        initChannelsList();
    }

    /**
     * Finalizes VoDKATV components
     */
    public void tearDown() throws InternalErrorException {
        System.out.println("[VoDKATVChannels] - Executing tearDown...");
        deleteChannelsList();
        deletePackagesAndProducts();
        deleteAccountData();
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

    private void initMemcached() throws InternalErrorException {
        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        Collection<AuxCacheServerTO> auxCacheServerTOs =
                new ArrayList<AuxCacheServerTO>();
        auxCacheServerTOs.add(new AuxCacheServerTO("10.121.55.32", 11211));
        configurationFacadeDelegate.updateAuxCacheServers(auxCacheServerTOs);
    }

    private void initMetadataServer() throws InternalErrorException {
        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();

        String hostMetadataServer = null;
        Integer portMetadataServer = null;
        String loginMetadataServer = null;
        String passwordMetadataServer = null;
        String hostAnnotationsServer = null;
        Integer portAnnotationsServer = null;
        String urlLimgenServer = null;
        String hostTeletextServer = null;
        Integer portTeletextServer = null;
        String hostNetPVRServer = null;
        Integer portNetPVRServer = null;
        String hostCouchDB = null;
        Integer portCouchDB = null;
        String dbCouchDB = null;
        String protocolCouchDB = null;
        String dataQueryUrl = null;
        String tokenCouchDB = null;
        String hostQoECouchDB = null;
        Integer portQoECouchDB = null;
        String dbQoECouchDB = null;
        String hostEPGCouchDB = "epg-interoud.interoud.net";
        Integer portEPGCouchDB = new Integer(5984);
        String dbEPGCouchDB = "aka";
        Integer availableEpgHours = new Integer(240);
        Integer availableFutureEpgHours = new Integer(336);
        String tokenEPGCouchDB = null;
        ChannelsFacadeDelegateType channelsFacadeDelegateType =
                ChannelsFacadeDelegateType.COUCH_DB;
        MetaServerConfigurationTO metaServerConfigurationTO =
                new MetaServerConfigurationTO(hostMetadataServer,
                        portMetadataServer, loginMetadataServer,
                        passwordMetadataServer, hostAnnotationsServer,
                        portAnnotationsServer, urlLimgenServer,
                        hostTeletextServer, portTeletextServer,
                        hostNetPVRServer, portNetPVRServer,
                        hostCouchDB, portCouchDB, dbCouchDB,
                        protocolCouchDB, dataQueryUrl, tokenCouchDB,
                        hostQoECouchDB, portQoECouchDB, dbQoECouchDB,
                        hostEPGCouchDB, portEPGCouchDB, dbEPGCouchDB,
                        availableEpgHours, availableFutureEpgHours,
                        tokenEPGCouchDB, channelsFacadeDelegateType);
        configurationFacadeDelegate.updateMetaServerConfiguration(
                metaServerConfigurationTO);

        configurationFacadeDelegate.initMetadataServers();
    }

    private void stopMemcached() throws InternalErrorException {
        MemcachedClientManager.getInstance().stopMemcached();
    }

    /*=========================================================================
     * ACCOUNT
     * ====================================================================== */
    private void initAccountData() throws InternalErrorException {
        MappingFacadeDelegate mappingFacadeDelegate =
                MappingFacadeDelegateFactory.getDelegate();

        /*
         * Data
         */
        String userId = getRandomName("test-user");
        String physicalId = getRandomName("physicalId");
        String roomDescription = null;
        String deviceDescription = null;
        Long tagTreeNodeId = null;
        DeviceClass deviceClass = DeviceClass.STBHED;
        Calendar now = CalendarUtils.getCalendar();
        Locale locale = new Locale("en", "EN");
        Collection<ChannelAccessTypeSubscriptionTO> channelAccessTypeSubscriptionTOs =
                new ArrayList<ChannelAccessTypeSubscriptionTO>();
        for(ChannelAccessType cat: ChannelAccessType.values()){
            channelAccessTypeSubscriptionTOs.add(
                    new ChannelAccessTypeSubscriptionTO(null, null, cat));
        }

        /*
         * Room
         */
        RoomTO roomTO = new RoomTO(userId, roomDescription, tagTreeNodeId);

        /*
         * Session
         */
        SessionAccountingTO sessionAccountingTO = new SessionAccountingTO(
                userId, roomTO.getRoomId(), now, null, false, false);
        sessionAccountingId = sessionAccountingTO.getSessionAccountingId();

        /*
         * Guest
         */
        GuestTO guestTO = new GuestTO(null, userId, locale);

        /*
         * Access
         */
        AccountRemoteAccessTO accountRemoteAccessTO =
                new AccountRemoteAccessTO(null, null, userId, null);

        /*
         * Account
         */
        AccountDetailsTO accountDetailsTO = new AccountDetailsTO(
                null, null, userId, now, null,
                new CreditTO(null, 0),
                channelAccessTypeSubscriptionTOs,
                new OutputsProtectionTO(null, null),
                null, null, accountRemoteAccessTO, null);

        Collection<AccountDetailsTO> accountDetailsTOs =
                new ArrayList<AccountDetailsTO>();
        accountDetailsTOs.add(accountDetailsTO);

        /*
         * Devices
         */
        Collection<DeviceTO> deviceTOs = new ArrayList<DeviceTO>();
        DeviceTO deviceTO = new DeviceTO(null, physicalId, roomTO.getRoomId(),
                deviceDescription, tagTreeNodeId, deviceClass);
        deviceTOs.add(deviceTO);

        /*
         * User session
         */
        UserSessionTO userSessionTO = new UserSessionTO(
                sessionAccountingTO, guestTO, accountDetailsTOs);

        /*
         * RoomSTBSession
         */
        RoomSTBSessionTO roomSTBSessionTO =
                new RoomSTBSessionTO(roomTO, deviceTOs, userSessionTO);

        /*
         * Create data
         */
        try {
            roomSTBSessionTO = mappingFacadeDelegate.createRoomSTBSession(
                    roomSTBSessionTO);
            userSessionTO = roomSTBSessionTO.getCurrentUserSessionTO();
            accountDetailsTO =
                    userSessionTO.getAccountDetailsTOs().iterator().next();
            deviceTO =
                    roomSTBSessionTO.getStbTOs().iterator().next();
            roomId = roomSTBSessionTO.getRoomTO().getRoomId();
            accountId = accountDetailsTO.getAccountId();
            deviceId = deviceTO.getId();
        } catch (DuplicateInstanceException die) {
            throw new InternalErrorException(die);
        } catch (InstanceNotFoundException infe) {
            throw new InternalErrorException(infe);
        }
    }

    private void deleteAccountData() throws InternalErrorException {
        MappingFacadeDelegate mappingFacadeDelegate =
                MappingFacadeDelegateFactory.getDelegate();
        try {
            mappingFacadeDelegate.deleteRoomSTBSession(roomId);
        } catch (InstanceNotFoundException e) {
            ;
        }
    }

    /*=========================================================================
     * PACKAGES AND PRODUCTS
     * ====================================================================== */
    private void initPackagesAndProducts() throws InternalErrorException {

        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        PackageFacadeDelegate packageFacadeDelegate =
                PackageFacadeDelegateFactory.getDelegate();
        ProductFacadeDelegate productFacadeDelegate =
                ProductFacadeDelegateFactory.getDelegate();
        AdminPurchaseFacadeDelegate purchaseFacadeDelegate =
                AdminPurchaseFacadeDelegateFactory.getDelegate();
        CurrencyFacadeDelegate currencyFacadeDelegate =
                CurrencyFacadeDelegateFactory.getDelegate();

        try {
            configurationFacadeDelegate.updateDefaultBasicPackage(null);

            CurrencyTO currencyTO = new CurrencyTO(null, getRandomName("currency"));
            currencyTO = currencyFacadeDelegate.createCurrency(currencyTO);
            String packageName = getRandomName("package");
            String productName = getRandomName("package");
            I18NAttribute nameI18n = null;
            I18NAttribute descriptionI18n = null;
            I18NAttribute shortDescriptionI18n = null;
            ImageAttribute logoPath = null;
            Float price = new Float(1);
            SubscriptionLimitType subscriptionType =
                    SubscriptionLimitType.UNLIMITED;
            SubscriptionRenovationType renovationType =
                    SubscriptionRenovationType.NONE;
            Long tagTreeNodeId = null;
            Float initialPrice = null;
            Integer initialPriceDurationDays = null;
            Calendar startTime = null;
            Calendar finishTime = null;
            boolean active = true;
            Long currencyId = currencyTO.getCurrencyId();
            Float discount = null;
            Float registrationFee = null;
            Integer trialDays = null;
            Long stbCustomizationId = null;
            Long pcCustomizationId = null;
            ImageAttribute imageFileName = null;
            boolean clientPurchaseDenied = false;
            Integer durationTimeDays = null;

            /*
             * Product
             */
            ProductTO productTO = new ProductTO(null, productName, nameI18n,
                    descriptionI18n, shortDescriptionI18n, logoPath, price,
                    subscriptionType, renovationType, tagTreeNodeId,
                    initialPrice, initialPriceDurationDays, startTime,
                    finishTime, active, currencyId, discount, registrationFee,
                    trialDays, imageFileName, clientPurchaseDenied);

            Collection<ProductItemTO> productItemTOs =
                    new ArrayList<ProductItemTO>();
            productItemTOs.add(new ProductItemAllChannelsTO(null, null,
                    startTime, finishTime, durationTimeDays));
            ProductDetailsTO productDetailsTO = new ProductDetailsTO(productTO,
                    currencyTO, productItemTOs, null, null);

            productDetailsTO = productFacadeDelegate.createProduct(
                    productDetailsTO);
            productId = productDetailsTO.getSubscriptionTO().getId();

            /*
             * Package
             */
            BasicPackageTO basicPackageTO = new BasicPackageTO(null, packageName,
                    nameI18n, descriptionI18n, shortDescriptionI18n, logoPath,
                    price, subscriptionType, renovationType, tagTreeNodeId,
                    initialPrice, initialPriceDurationDays, startTime,
                    finishTime, active, currencyId, discount, registrationFee,
                    trialDays, stbCustomizationId, pcCustomizationId,
                    imageFileName, clientPurchaseDenied);

            CustomizationTO stbCustomizationTO = null;
            CustomizationTO pcCustomizationTO = null;
            DesktopConfigTO desktopConfigTO = null;
            Collection<ProductTO> includedProductTOs = new ArrayList<ProductTO>();
            includedProductTOs.add(productDetailsTO.getSubscriptionTO());
            Collection<ProductTO> productDependenciesTOs = null;
            Collection<ProductDetailsTO> includedProductDetailsTOs = null;
            Collection<ProductDetailsTO> productDependenciesDetailsTOs = null;
            BasicPackageDetailsTO basicPackageDetailsTO = new BasicPackageDetailsTO(
                    basicPackageTO, currencyTO, stbCustomizationTO,
                    pcCustomizationTO, desktopConfigTO, includedProductTOs,
                    productDependenciesTOs, includedProductDetailsTOs,
                    productDependenciesDetailsTOs);
            basicPackageDetailsTO = packageFacadeDelegate.createBasicPackage(
                    basicPackageDetailsTO);
            packageId = basicPackageDetailsTO.getSubscriptionTO().getId();

            /*
             * Purchase
             */
            purchaseFacadeDelegate.purchasePackage(packageId, sessionAccountingId);

        } catch (DuplicateInstanceException e) {
            throw new InternalErrorException(e);
        } catch (RequiredFieldException e) {
            throw new InternalErrorException(e);
        } catch (ReferencedObjectNotFoundException e) {
            throw new InternalErrorException(e);
        } catch (InstanceNotFoundException e) {
            throw new InternalErrorException(e);
        }

    }

    private void deletePackagesAndProducts() throws InternalErrorException {
        PackageFacadeDelegate packageFacadeDelegate =
                PackageFacadeDelegateFactory.getDelegate();
        ProductFacadeDelegate productFacadeDelegate =
                ProductFacadeDelegateFactory.getDelegate();

        try {
            productFacadeDelegate.deleteProductById(productId);
            packageFacadeDelegate.deleteBasicPackageById(packageId);
        } catch (InstanceNotFoundException e) {
            throw new InternalErrorException(e);
        }
    }

    /*=========================================================================
     * CHANNELS LIST
     * ====================================================================== */
    private void initChannelsList() throws InternalErrorException {

        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        String channelsListName = getRandomName("test");
        ChannelListTO channelListTO = new ChannelListTO(null, channelsListName,
                ChannelDataType.VIDEO);
        Collection<ChannelListItemDetailsTO> channelListItemDetailsTOs =
                new ArrayList<ChannelListItemDetailsTO>();
        Collection<ChannelDataTO> channelDataTOs =
                channelConfigFacadeDelegate.findAllChannelsData();
        int i = 1;
        for(ChannelDataTO channelDataTO : channelDataTOs) {
            ChannelListItemTO channelListItemTO = new ChannelListItemTO(null,
                    null, channelDataTO.getId(), i, true);
            channelListItemDetailsTOs.add(new ChannelListItemDetailsTO(
                    channelListItemTO, channelDataTO));
            i++;
        }
        ChannelListDetailsTO channelListDetailsTO = new ChannelListDetailsTO(
                channelListTO, channelListItemDetailsTOs);
        try {

            channelListDetailsTO =
                    configurationFacadeDelegate.createChannelListDetails(
                            channelListDetailsTO);
            channelListId = channelListDetailsTO.getChannelListTO().getId();
            configurationFacadeDelegate.updateDefaultChannelList(
                    channelListDetailsTO.getChannelListTO().getId(),
                    ChannelDataType.VIDEO);
        } catch (DuplicateInstanceException e) {
            throw new InternalErrorException(e);
        } catch (InstanceNotFoundException e) {
            throw new InternalErrorException(e);
        } catch (DuplicateChannelNumberException e) {
            throw new InternalErrorException(e);
        } catch (IncompatibleChannelTypeException e) {
            throw new InternalErrorException(e);
        }

    }

    private void deleteChannelsList() throws InternalErrorException {
        ConfigurationFacadeDelegate configurationFacadeDelegate =
                ConfigurationFacadeDelegateFactory.getDelegate();
        try {
            configurationFacadeDelegate.deleteChannelList(channelListId);
        } catch (InstanceNotFoundException e) {
            ;
        }
    }

    /*=========================================================================
     * CHANNELS
     * ====================================================================== */
    private void createTestChannels(int numEPGChannels)
            throws InternalErrorException {

        MediaChannelsFacadeDelegate mediaChannelsFacadeDelegate =
                MediaChannelsFacadeDelegateFactory.getDelegate();
        ChannelConfigFacadeDelegate channelConfigFacadeDelegate =
                ChannelConfigFacadeDelegateFactory.getDelegate();

        /*
         * Delete existing channels
         */
        channelConfigFacadeDelegate.deleteAllChannelDatas();

        /*
         * Get channels from EPG server
         */
        List<ChannelTO> channelTOs =
                new ArrayList<ChannelTO>(
                        mediaChannelsFacadeDelegate.findAllChannels());

        int currentChannelsWithEPG = 0;

        /*
         * Create new channels
         */
        for(int i = 0; i < NUM_CHANNELS; i++) {

            Long id = null;
            String vodkatvChannelId = getRandomName("test_" + i);
            String channelId = null;
            if(!channelTOs.isEmpty() &&
                    currentChannelsWithEPG < numEPGChannels) {
                ChannelTO channelTO = channelTOs.get(Math.abs(
                        new Random().nextInt(channelTOs.size())));
                channelId = channelTO.getChannelId();
                currentChannelsWithEPG = currentChannelsWithEPG + 1;
            }
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
     * UTILITIES
     * ====================================================================== */
    private String getRandomName(String base) {
        return base + new Random().nextInt();
    }

    /*=========================================================================
     * TEST
     * ====================================================================== */
    public static void main(String[] args) {
        boolean useMemcached = true;
        VoDKATVChannels v = VoDKATVChannels.getInstance();
        try {
            v.globalSetUp(useMemcached);
            v.setUp(1000);
            Date d1 = new Date();
            v.findChannelsInformation(10);
            Date d2 = new Date();
            v.findChannelsInformation(10);
            Date d3 = new Date();
            v.findChannelsInformation(10);
            Date d4 = new Date();
            System.out.println((d4.getTime() - d3.getTime()) + " | " +
                    (d3.getTime() - d2.getTime()) + " | " +
                    (d2.getTime() - d1.getTime()));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                v.tearDown();
            } catch (InternalErrorException e) {
                e.printStackTrace();
            }
            try {
                v.globalTearDown(useMemcached);
            } catch (InternalErrorException e) {
                e.printStackTrace();
            }
        }
    }

}
