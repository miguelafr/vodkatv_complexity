package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegate;
import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegate;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.util.notifier.ChangesNotifier;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class DeleteChannelDataByVodkatvChannelIdAction implements TransactionalPlainAction<Object> {

    private String vodkatvChannelId;

    public DeleteChannelDataByVodkatvChannelIdAction(String vodkatvChannelId){
        this.vodkatvChannelId = vodkatvChannelId;
    }

    public Object execute(DataConnection connection)
    throws InternalErrorException, InstanceNotFoundException {

        ProductFacadeDelegate productFacadeDelegate =
                ProductFacadeDelegateFactory.getDelegate();
        PackageFacadeDelegate packageFacadeDelegate =
                PackageFacadeDelegateFactory.getDelegate();

        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();
        sqlChannelDataDAO.removeByVoDKATVChannelId(connection.getConnection(),
                vodkatvChannelId);

        productFacadeDelegate.refreshCache();
        packageFacadeDelegate.refreshCache();

        ChangesNotifier changesNotifier = ChangesNotifier.getInstance();
        changesNotifier.notifyDeleteChannel(vodkatvChannelId);

        return null;
    }

}
