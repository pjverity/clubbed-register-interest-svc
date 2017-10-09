package uk.co.vhome.clubbed.services;

import org.springframework.stereotype.Component;
import uk.co.vhome.clubbed.notifications.BalanceUpdatedNotification;
import uk.co.vhome.clubbed.notifications.LowBalanceNotification;
import uk.co.vhome.clubbed.notifications.NewInterestRegisteredNotification;
import uk.co.vhome.clubbed.notifications.NewUserNotification;
import uk.co.vhome.clubbed.notifications.metadatasuppliers.MailMetaData;
import uk.co.vhome.clubbed.notifications.metadatasuppliers.NotificationMetaDataSupplier;

@Component
public class MailNotificationMetaDataSupplier implements NotificationMetaDataSupplier<MailMetaData>
{
	private static final String SUBJECT = "Thanks for registering your interest";

	private static final String TEMPLATE_NAME = "interest-registration.html";


	private static final String FROM_NAME_RJJ = "Reigate Junior Joggers";

	private static final String FROM_NAME_HLJ = "Horsham Ladies Joggers";

	private static final String FROM_NAME_CLJ = "Caterham Ladies Joggers";


//	private static final String ADMIN_ADDRESS_RJJ = "admin@reigatejuniorjoggers.co.uk";
	private static final String ADMIN_ADDRESS_RJJ = "administrator@v-home.co.uk";

//	private static final String ADMIN_ADDRESS_HLJ = "admin@horshamladiesjoggers.co.uk";
	private static final String ADMIN_ADDRESS_HLJ = "administrator@v-home.co.uk";

//	private static final String ADMIN_ADDRESS_CLJ = "admin@caterhamladiesjoggers.co.uk";
	private static final String ADMIN_ADDRESS_CLJ = "administrator@v-home.co.uk";


	private static final MailMetaData RJJ_MAIL_META_DATA = new MailMetaData(ADMIN_ADDRESS_RJJ,
	                                                                        FROM_NAME_RJJ,
	                                                                        TEMPLATE_NAME,
	                                                                        SUBJECT);

	private static final MailMetaData HLJ_MAIL_META_DATA = new MailMetaData(ADMIN_ADDRESS_HLJ,
	                                                                        FROM_NAME_HLJ,
	                                                                        TEMPLATE_NAME,
	                                                                        SUBJECT);

	private static final MailMetaData CLJ_MAIL_META_DATA = new MailMetaData(ADMIN_ADDRESS_CLJ,
	                                                                        FROM_NAME_CLJ,
	                                                                        TEMPLATE_NAME,
	                                                                        SUBJECT);

	@Override
	public MailMetaData metaDataFor(LowBalanceNotification notification)
	{
		return null;
	}

	@Override
	public MailMetaData metaDataFor(BalanceUpdatedNotification notification)
	{
		return null;
	}

	@Override
	public MailMetaData metaDataFor(NewUserNotification notification)
	{
		return null;
	}

	@Override
	public MailMetaData metaDataFor(NewInterestRegisteredNotification notification)
	{
		SiteId siteId = SiteId.valueOf(notification.getSiteId());

		switch (siteId)
		{
			case RJJ:
				return RJJ_MAIL_META_DATA;
			case HLJ:
				return HLJ_MAIL_META_DATA;
			case CLJ:
				return CLJ_MAIL_META_DATA;
		}

		throw new IllegalStateException("Unknown site Id: " + notification.getSiteId());
	}
}
