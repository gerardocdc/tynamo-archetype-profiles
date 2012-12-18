package ${package}.pages.list;


import ${package}.pages.List;

public abstract class CustomList<T> extends List
{
	public abstract Class<T> getType();

	protected void onActivate() throws Exception
	{
		super.onActivate(getType());
	}
}
