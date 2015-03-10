package com.lambdastream.tahiti.model.configuration.channelconfigfacade.plain.actions;

import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegate;
import com.lambdastream.tahiti.model.business.packagefacade.delegate.PackageFacadeDelegateFactory;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegate;
import com.lambdastream.tahiti.model.business.productfacade.delegate.ProductFacadeDelegateFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAO;
import com.lambdastream.tahiti.model.configuration.channeldata.dao.SQLChannelDataDAOFactory;
import com.lambdastream.tahiti.model.configuration.channeldata.to.ChannelDataTO;
import com.lambdastream.tahiti.model.util.notifier.ChangesNotifier;
import com.lambdastream.util.exceptions.InstanceNotFoundException;
import com.lambdastream.util.exceptions.InternalErrorException;
import com.lambdastream.util.sql.DataConnection;
import com.lambdastream.util.sql.TransactionalPlainAction;

public class DeleteChannelDataAction implements TransactionalPlainAction<Object> {

    private Long id;

    public DeleteChannelDataAction(Long id){
        this.id = id;
    }

    public Object execute(DataConnection connection)
    throws InternalErrorException, InstanceNotFoundException {

        ProductFacadeDelegate productFacadeDelegate =
                ProductFacadeDelegateFactory.getDelegate();
        PackageFacadeDelegate packageFacadeDelegate =
                PackageFacadeDelegateFactory.getDelegate();

        SQLChannelDataDAO sqlChannelDataDAO = SQLChannelDataDAOFactory.getDAO();

        ChannelDataTO channelDataTO = sqlChannelDataDAO.findById(
                connection.getConnection(), id);

        sqlChannelDataDAO.remove(connection.getConnection(), id);

        productFacadeDelegate.refreshCache();
        packageFacadeDelegate.refreshCache();

        ChangesNotifier changesNotifier = ChangesNotifier.getInstance();
        changesNotifier.notifyDeleteChannel(channelDataTO.getVodkatvChannelId());

        return null;
    }

}
