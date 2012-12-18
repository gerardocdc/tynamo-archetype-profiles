package ${package}.pages.show;


import ${package}.pages.Show;

public abstract class CustomShow<T> extends Show
{
	public abstract Class<T> getType();

	protected void onActivate(String id) throws Exception
	{
		super.activate(getType(), id);
	}
}
