package ${package}.pages.edit;


import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ContextValueEncoder;
import org.apache.tapestry5.services.PageRenderLinkSource;

import org.tynamo.services.PersistenceService;
import org.tynamo.util.TynamoMessages;
import org.tynamo.util.Utils;

import ${package}.pages.Show;

public abstract class CustomEdit<T>
{
	public abstract Class<T> getType();

	@Inject
	protected PersistenceService persistenceService;

	@Inject
	private ContextValueEncoder contextValueEncoder;

	@Inject
	protected Messages messages;

	@Inject
	protected PageRenderLinkSource pageRenderLinkSource;

	@Property
	protected Object bean;

	@Property(write = false)
	protected Class beanType;

	@OnEvent(EventConstants.ACTIVATE)
	protected Object onActivate(Object object) throws Exception
	{
		this.bean = contextValueEncoder.toValue(getType(), object.toString());
		this.beanType = getType();

		if (bean == null) return Utils.new404(messages);

		return null;
	}

	@CleanupRender
	void cleanup()
	{
		bean = null;
		beanType = null;
	}

	/**
	 * This tells Tapestry to put type & id into the URL, making it bookmarkable.
	 */
	@OnEvent(EventConstants.PASSIVATE)
	Object onPassivate()
	{
		return bean;
	}

	@Log
	@CommitAfter
	@OnEvent(EventConstants.SUCCESS)
	Link onSuccess()
	{
		persistenceService.save(bean);
		return back();
	}

	@OnEvent("cancel")
	Link back()
	{
		return pageRenderLinkSource.createPageRenderLinkWithContext(Show.class, beanType, bean);
	}

	public String getListAllLinkMessage()
	{
		return TynamoMessages.listAll(messages, beanType);
	}

	public String getTitle()
	{
		return TynamoMessages.edit(messages, beanType);
	}
}
